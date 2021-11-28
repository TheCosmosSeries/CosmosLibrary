package com.tcn.cosmoslibrary.client.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.tcn.cosmoslibrary.CosmosLibrary;
import com.tcn.cosmoslibrary.client.enums.EnumTESRColour;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TERUtil {
	public static final ResourceLocation LASER_TEXTURE = new ResourceLocation(CosmosLibrary.MOD_ID, "textures/entity/laser_beam.png");

	public static final int MAX_LIGHT_X = 0xF000F0;
	public static final int MAX_LIGHT_Y = 0xF000F0;
	
	public static void renderLaser(IRenderTypeBuffer bufferIn, MatrixStack matrixStack, double firstX, double firstY, double firstZ, double secondX, double secondY, double secondZ, double rotationTime, float alpha, double beamWidth, EnumTESRColour enum_colour) {
		Minecraft mc = Minecraft.getInstance();
		World world = mc.level;
		IVertexBuilder vertexBuilder = bufferIn.getBuffer(RenderType.entityTranslucent(LASER_TEXTURE));

		float[] colour = enum_colour.getColour();
		
		float r = colour[0];
		float g = colour[1];
		float b = colour[2];
		
		Vector3d vector_one = new Vector3d(firstX, firstY, firstZ);
		Vector3d vector_two = new Vector3d(secondX, secondY, secondZ);
		Vector3d vector_combined = vector_two.subtract(vector_one);
		
		double rotation = rotationTime > 0 ? (360D * ((world.getGameTime() % rotationTime) / rotationTime)) : 0;
		double pitch = Math.atan2(vector_combined.y, Math.sqrt(vector_combined.x * vector_combined.x + vector_combined.z * vector_combined.z));
		double yaw = Math.atan2(-vector_combined.z, vector_combined.x);
		
		double length = vector_combined.length();
		
		matrixStack.pushPose();
		matrixStack.translate(0.5, 0.5, 0.5);
		
		matrixStack.mulPose(Vector3f.YP.rotationDegrees((float) (180 * yaw / Math.PI)));
		matrixStack.mulPose(Vector3f.ZP.rotationDegrees((float) (180 * pitch / Math.PI)));
		matrixStack.mulPose(Vector3f.XP.rotationDegrees((float) rotation));
		
		MatrixStack.Entry matrixstack$entry = matrixStack.last();
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
	
	private static void addVertex(Matrix4f matrixPos, Matrix3f matrixNormal, IVertexBuilder bufferIn, float red, float green, float blue, float alpha, float x, float y, float z, float texU, float texV) {
		bufferIn.vertex(matrixPos, x, y, z).color(red, green, blue, alpha).uv(texU, texV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(1.0F, 1.0F, 1.0F).endVertex();
	}

	public static void addF(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v, float r, float g, float b, float a) {
        renderer.vertex(stack.last().pose(), x, y, z).color(r, g, b, a).uv(u, v).uv2(0, 240).normal(1, 0, 0).endVertex();
    }

	public static void addF(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v, int u2, int v2, float r, float g, float b, float a) {
        renderer.vertex(stack.last().pose(), x, y, z).color(r, g, b, a).uv(u, v).uv2(u2, v2).normal(1, 0, 0).endVertex();
    }

	public static void addD(IVertexBuilder renderer, MatrixStack stack, double x, double y, double z, double u, double v, double r, double g, double b, double a) {
        renderer.vertex(stack.last().pose(), (float) x, (float) y, (float) z).color((float) r, (float) g, (float) b, (float) a).uv((float) u, (float) v).uv2(0, 240).normal(1, 0, 0).endVertex();
    }
	
	public static void addD(IVertexBuilder renderer, MatrixStack stack, double x, double y, double z, double u, double v, int u2, int v2, double r, double g, double b, double a) {
        renderer.vertex(stack.last().pose(), (float) x, (float) y, (float) z).color((float) r, (float) g, (float) b, (float) a).uv((float) u, (float) v).uv2(u2, v2).normal(1, 0, 0).endVertex();
    }
	
	@SuppressWarnings("resource")
	public static void renderLabelInWorld(FontRenderer fontRendererIn, MatrixStack matrixStackIn, IFormattableTextComponent textIn, IRenderTypeBuffer bufferIn, int combinedLightIn) {
		matrixStackIn.pushPose();
		matrixStackIn.scale(-0.025F, -0.025F, -0.025F);
		
		Matrix4f matrix4f = matrixStackIn.last().pose();
		
		float opacity = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
		int alpha = (int)(opacity * 255.0F) << 24;
		float width = (-fontRendererIn.width(textIn) / 2);
		
		fontRendererIn.drawInBatch(textIn, width, 0.0F, 553648127, false, matrix4f, bufferIn, false, -alpha, combinedLightIn);
		fontRendererIn.drawInBatch(textIn, width, 0.0F, -1, false, matrix4f, bufferIn, true, 0, combinedLightIn);
		matrixStackIn.popPose();
	}
}