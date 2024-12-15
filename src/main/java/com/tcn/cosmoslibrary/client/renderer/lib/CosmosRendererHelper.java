package com.tcn.cosmoslibrary.client.renderer.lib;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mojang.math.MatrixUtil;
import com.tcn.cosmoslibrary.CosmosLibrary;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.ClientHooks;

@OnlyIn(Dist.CLIENT)
public class CosmosRendererHelper {
	public static final ResourceLocation LASER_TEXTURE = ResourceLocation.fromNamespaceAndPath(CosmosLibrary.MOD_ID, "textures/entity/laser_beam.png");

	public static final int MAX_LIGHT_X = 0xF000F0;
	public static final int MAX_LIGHT_Y = 0xF000F0;
	
	public static void renderLaser(MultiBufferSource bufferIn, PoseStack matrixStack, double firstX, double firstY, double firstZ, double secondX, double secondY, double secondZ, double rotationTime, float alpha, double beamWidth, ComponentColour enum_colour) {
		Minecraft mc = Minecraft.getInstance();
		Level world = mc.level;
		VertexConsumer vertexBuilder = bufferIn.getBuffer(RenderType.entityTranslucent(LASER_TEXTURE));

		float[] colour = enum_colour.getFloatRGB();
		
		float r = colour[0];
		float g = colour[1];
		float b = colour[2];
		
		Vec3 vector_one = new Vec3(firstX, firstY, firstZ);
		Vec3 vector_two = new Vec3(secondX, secondY, secondZ);
		Vec3 vector_combined = vector_two.subtract(vector_one);
		
		double rotation = rotationTime > 0 ? (360D * ((world.getGameTime() % rotationTime) / rotationTime)) : 0;
		double pitch = Math.atan2(vector_combined.y, Math.sqrt(vector_combined.x * vector_combined.x + vector_combined.z * vector_combined.z));
		double yaw = Math.atan2(-vector_combined.z, vector_combined.x);
		
		double length = vector_combined.length();
		
		matrixStack.pushPose();
		matrixStack.translate(0.5, 0.5, 0.5);
		
		matrixStack.mulPose(Axis.YP.rotationDegrees((float) (180 * yaw / Math.PI)));
		matrixStack.mulPose(Axis.ZP.rotationDegrees((float) (180 * pitch / Math.PI)));
		matrixStack.mulPose(Axis.XP.rotationDegrees((float) rotation));
		
		Pose matrixstack$entry = matrixStack.last();
		Matrix4f matrix4f = matrixstack$entry.pose();
		Matrix3f matrix3f = matrixstack$entry.normal();
		
		for (double i = 0; i < 4; i++) {
			double width = beamWidth * (i / 4.0);
			
			addVertex(matrix4f, matrix3f, vertexBuilder, r, g, b, alpha, (float) length, (float) width, (float) width, 0.0F, 0.0F);
			addVertex(matrix4f, matrix3f, vertexBuilder, r, g, b, alpha, (float) 0, (float) width, (float) width, 0.0F, 0.0F);
			addVertex(matrix4f, matrix3f, vertexBuilder, r, g, b, alpha, (float) 0, (float) -width, (float) width, 0.0F, 0.0F);
			addVertex(matrix4f, matrix3f, vertexBuilder, r, g, b, alpha, (float) length, (float) -width, (float) width, 0.0F, 0.0F);
			
			addVertex(matrix4f, matrix3f, vertexBuilder, r, g, b, alpha, (float) length, (float) -width, (float) -width, 0.0F, 0.0F);
			addVertex(matrix4f, matrix3f, vertexBuilder, r, g, b, alpha, (float) 0, (float) -width, (float) -width, 0.0F, 0.0F);
			addVertex(matrix4f, matrix3f, vertexBuilder, r, g, b, alpha, (float) 0, (float) width, (float) -width, 0.0F, 0.0F);
			addVertex(matrix4f, matrix3f, vertexBuilder, r, g, b, alpha, (float) length, (float) width, (float) -width, 0.0F, 0.0F);

			addVertex(matrix4f, matrix3f, vertexBuilder, r, g, b, alpha, (float) length, (float) width, (float) -width, 0.0F, 0.0F);
			addVertex(matrix4f, matrix3f, vertexBuilder, r, g, b, alpha, (float) 0, (float) width, (float) -width, 0.0F, 0.0F);
			addVertex(matrix4f, matrix3f, vertexBuilder, r, g, b, alpha, (float) 0, (float) width, (float) width, 0.0F, 0.0F);
			addVertex(matrix4f, matrix3f, vertexBuilder, r, g, b, alpha, (float) length, (float) width, (float) width, 0.0F, 0.0F);
			
			addVertex(matrix4f, matrix3f, vertexBuilder, r, g, b, alpha, (float) length, (float) -width, (float) width, 0.0F, 0.0F);
			addVertex(matrix4f, matrix3f, vertexBuilder, r, g, b, alpha, (float) 0, (float) -width, (float) width, 0.0F, 0.0F);
			addVertex(matrix4f, matrix3f, vertexBuilder, r, g, b, alpha, (float) 0, (float) -width, (float) -width, 0.0F, 0.0F);
			addVertex(matrix4f, matrix3f, vertexBuilder, r, g, b, alpha, (float) length, (float) -width, (float) -width, 0.0F, 0.0F);
		}
		
		matrixStack.popPose();
	}
	
	private static void addVertex(Matrix4f matrixPos, Matrix3f matrixNormal, VertexConsumer bufferIn, float red, float green, float blue, float alpha, float x, float y, float z, float texU, float texV) {
		bufferIn.addVertex(matrixPos, x, y, z).setColor(red, green, blue, alpha).setUv(texU, texV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(1.0F, 1.0F, 1.0F);
	}

	public static void addF(VertexConsumer renderer, PoseStack stack, float x, float y, float z, float u, float v, float r, float g, float b, float a) {
        renderer.addVertex(stack.last().pose(), x, y, z).setColor(r, g, b, a).setUv(u, v).setUv2(0, 240).setNormal(1, 0, 0);
    }

	public static void addF(VertexConsumer renderer, PoseStack stack, float x, float y, float z, float u, float v, int u2, int v2, float r, float g, float b, float a) {
        renderer.addVertex(stack.last().pose(), x, y, z).setColor(r, g, b, a).setUv(u, v).setUv2(u2, v2).setNormal(1, 0, 0);
    }

	public static void addD(VertexConsumer renderer, PoseStack stack, double x, double y, double z, double u, double v, double r, double g, double b, double a) {
        renderer.addVertex(stack.last().pose(), (float) x, (float) y, (float) z).setColor((float) r, (float) g, (float) b, (float) a).setUv((float) u, (float) v).setUv2(0, 240).setNormal(1, 0, 0);
    }
	
	public static void addD(VertexConsumer renderer, PoseStack stack, double x, double y, double z, double u, double v, int u2, int v2, double r, double g, double b, double a) {
        renderer.addVertex(stack.last().pose(), (float) x, (float) y, (float) z).setColor((float) r, (float) g, (float) b, (float) a).setUv((float) u, (float) v).setUv2(u2, v2).setNormal(1, 0, 0);
    }
	
	@SuppressWarnings("resource")
	public static void renderLabelInWorld(Font fontRendererIn, PoseStack matrixStackIn, MutableComponent textIn, MultiBufferSource bufferIn, int combinedLightIn, boolean boxOn, boolean shadowOn) {
		matrixStackIn.pushPose();
		matrixStackIn.scale(-0.025F, -0.025F, -0.025F);
		
		Matrix4f matrix4f = matrixStackIn.last().pose();
		
		float opacity = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
		int alpha = (int)(opacity * 255.0F) << 24;
		float width = (-fontRendererIn.width(textIn) / 2);
		
		if (boxOn) {
			fontRendererIn.drawInBatch(textIn, width, 0.0F, 553648127, false, matrix4f, bufferIn, Font.DisplayMode.NORMAL, -alpha, combinedLightIn);
		}
		
		fontRendererIn.drawInBatch(textIn, width, 0.0F, -1, shadowOn, matrix4f, bufferIn, Font.DisplayMode.POLYGON_OFFSET, 0, combinedLightIn);
		matrixStackIn.popPose();
	}

	@OnlyIn(Dist.CLIENT)
	public static void render(ItemRenderer renderer, ItemStack stackIn, ItemDisplayContext transformIn, boolean p_229111_3_, PoseStack poseStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, BakedModel modelIn, boolean renderFoil) {
		if (!stackIn.isEmpty()) {
			poseStackIn.pushPose();
			boolean flag = transformIn == ItemDisplayContext.GUI || transformIn == ItemDisplayContext.GROUND || transformIn == ItemDisplayContext.FIXED;
			
			modelIn = ClientHooks.handleCameraTransforms(poseStackIn, modelIn, transformIn, p_229111_3_);
			poseStackIn.translate(-0.5D, -0.5D, -0.5D);
			
			if ((stackIn.getItem() != Items.TRIDENT || flag)) {
				boolean flag1;
				if (transformIn != ItemDisplayContext.GUI && !transformIn.firstPerson() && stackIn.getItem() instanceof BlockItem) {
					Block block = ((BlockItem) stackIn.getItem()).getBlock();
					flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
				} else {
					flag1 = true;
				}

				for (var model : modelIn.getRenderPasses(stackIn, flag1)) {
					for (var rendertype : model.getRenderTypes(stackIn, flag1)) {
						poseStackIn.pushPose();
						PoseStack.Pose posestack$pose = poseStackIn.last();
						if (transformIn == ItemDisplayContext.GUI) {
							MatrixUtil.mulComponentWise(posestack$pose.pose(), 0.5F);
						} else if (transformIn.firstPerson()) {
							MatrixUtil.mulComponentWise(posestack$pose.pose(), 0.75F);
						}
			               
						//if (modelIn.isLayered()) {
						//	ForgeHooksClient.drawItemLayered(renderer, modelIn, stackIn, poseStackIn, bufferIn, combinedLightIn, combinedOverlayIn, flag1);
						//} else {
						VertexConsumer ivertexbuilder;
							
						poseStackIn.popPose();
						if (flag1) {
							if (renderFoil) {
								ivertexbuilder = ItemRenderer.getFoilBufferDirect(bufferIn, rendertype, true, stackIn.hasFoil());
							} else {
								ivertexbuilder = ItemRenderer.getFoilBufferDirect(bufferIn, rendertype, true, false);
							}
						} else {
							if (renderFoil) {
								ivertexbuilder = ItemRenderer.getFoilBuffer(bufferIn, rendertype, true, stackIn.hasFoil());
							} else {
								ivertexbuilder = ItemRenderer.getFoilBuffer(bufferIn, rendertype, true, false);
							}
						}
						renderer.renderModelLists(modelIn, stackIn, combinedLightIn, combinedOverlayIn, poseStackIn,
								ivertexbuilder);
					}
					// }
				}
			}
			poseStackIn.popPose();
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static float getMappedTextureHeight(TextureAtlasSprite spriteIn, float inputHeightIn, float inputMinIn, float inputMaxIn) {
		return ((0.0625F / (512.0F / spriteIn.contents().height())) * (Mth.map(inputHeightIn, inputMinIn, inputMaxIn, 0, 8)));
	}

	@OnlyIn(Dist.CLIENT)
	public static float getMappedTextureHeight(TextureAtlasSprite spriteIn, float inputHeightIn) {
		return getMappedTextureHeight(spriteIn, inputHeightIn, 16, 0);
	}
}