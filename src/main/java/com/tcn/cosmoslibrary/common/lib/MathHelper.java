package com.tcn.cosmoslibrary.common.lib;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class MathHelper {

	public static BlockPos addBlockPos(BlockPos pos1, BlockPos pos2) {
		return new BlockPos(pos1.getX() + pos2.getX(), pos1.getY() + pos2.getY(), pos1.getZ() + pos2.getZ());
	}
	
	public static BlockPos addBlockPos(BlockPos pos1, BlockPos pos2, BlockPos pos3) {
		return new BlockPos(pos1.getX() + pos2.getX() + pos3.getX(), pos1.getY() + pos2.getY() + pos3.getY(), pos1.getZ() + pos2.getZ() + pos3.getZ());
	}

	public static boolean isPlayerLookingAt(Player player, BlockPos posIn, boolean isFluid) {
		HitResult block = player.pick(20.0D, 0.0F, isFluid);
		
		if (block instanceof BlockHitResult hit) {
			BlockPos hitPos = hit.getBlockPos();
			
			if (blockPosEqual(posIn, hitPos)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean blockPosEqual(BlockPos posInOne, BlockPos posInTwo) {
		if (posInOne.getX() == posInTwo.getX() && posInOne.getY() == posInTwo.getY() && posInOne.getZ() == posInTwo.getZ()) {
			return true;
		}
		
		return false;
	}
	
	public static BlockPos offsetBlockPos(BlockPos posInOne, BlockPos posInTwo) {
		return new BlockPos(posInOne.getX() - posInTwo.getX(), posInOne.getY() - posInTwo.getY(), posInOne.getZ() - posInTwo.getZ());
	}
}