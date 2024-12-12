package com.tcn.cosmoslibrary.common.interfaces.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * An interface to hand-over functions from a {@link Block} to a
 * {@link BlockEntity}.
 */
public interface IBlockInteract {

	public InteractionResult useWithoutItem(BlockState state, Level levelIn, BlockPos posIn, Player playerIn, BlockHitResult hit);
	
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level levelIn, BlockPos posIn, Player playerIn, InteractionHand hand, BlockHitResult hit);

	public void attack(BlockState state, Level worldIn, BlockPos pos, Player player);
	
}