package com.tcn.cosmoslibrary.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@Deprecated
public class ExampleSideItemRenderer implements BlockEntityRenderer<BlockEntity> {

	public ExampleSideItemRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
		
	}

	@Override
	public void render(BlockEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		/**
		int slot = 0;
		World world = tileEntityIn.getLevel();
		BlockPos pos = tileEntityIn.getBlockPos();
		ItemStack stack = tileEntityIn.getItem(slot);
		*//*
		if (!(stack.isEmpty())) {
			matrixStackIn.pushPose();
			GL11.glPushMatrix();

			matrixStackIn.translate(0, 0.5F, 0);
			
			if (world.getBlockState(pos.offset(Direction.EAST.getNormal())).isAir()) {
				matrixStackIn.pushPose();
				matrixStackIn.translate(0.975F, 0, 0.5F);
				Quaternion east = new Quaternion(Vector3f.YN, 90, true);
				matrixStackIn.mulPose(east);
				Minecraft.getInstance().getItemRenderer().renderStatic(stack, TransformType.FIXED, 0, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
				matrixStackIn.popPose();
			}
			
			if (world.getBlockState(pos.offset(Direction.WEST.getNormal())).isAir()) {
				matrixStackIn.pushPose();
				matrixStackIn.translate(0.025F, 0, 0.5F);
				Quaternion west = new Quaternion(Vector3f.YN, 90, true);
				matrixStackIn.mulPose(west);
				Minecraft.getInstance().getItemRenderer().renderStatic(stack, TransformType.FIXED, 0, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
				matrixStackIn.popPose();
			}
		
			if (world.getBlockState(pos.offset(Direction.SOUTH.getNormal())).isAir()) {
				matrixStackIn.pushPose();
				matrixStackIn.translate(0.5F, 0, 0.975F);
				Minecraft.getInstance().getItemRenderer().renderStatic(stack, TransformType.FIXED, 0, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
				matrixStackIn.popPose();
			}
			
			if (world.getBlockState(pos.offset(Direction.NORTH.getNormal())).isAir()) {
				matrixStackIn.pushPose();
				matrixStackIn.translate(0.5F, 0, 0.025F);
				Minecraft.getInstance().getItemRenderer().renderStatic(stack, TransformType.FIXED, 0, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
				matrixStackIn.popPose();
			}
			
			for (Direction c : Direction.values()) {
				if (c != Direction.UP && c != Direction.DOWN) {
					BlockPos offset = pos.offset(c.getNormal());
					Block block = world.getBlockState(offset).getBlock();
					
					if (block.equals(Blocks.AIR)) {
						
						RenderHelper.turnOff();//.disableStandardItemLighting();
						if (c.equals(Direction.EAST)) {
							//matrixStackIn.translate(0, 0, 1.525F);
						//	Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.FIXED, 0, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
							break;
						} else if (c.equals(Direction.WEST)) {
							//matrixStackIn.translate(0, 0, -0.525F);
							//Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.FIXED, 0, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
						} else if (c.equals(Direction.NORTH)) {
							//matrixStackIn.translate(0, 1, 0);
							//Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.FIXED, 0, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
						} else if (c.equals(Direction.SOUTH)) {
							//matrixStackIn.translate(0, 0, -0.525F);
							//Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.FIXED, 0, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
						}
						RenderHelper.turnBackOn();//.enableStandardItemLighting();
					}
					
					
					Vector3i vec = c.getNormal();
					matrixStackIn.translate(-vec.getX() - 0.5F, -vec.getY(), -vec.getZ() - 0.5F);
				}
			}
			
			GL11.glPopMatrix();
			matrixStackIn.popPose();
		}*/
	}
}