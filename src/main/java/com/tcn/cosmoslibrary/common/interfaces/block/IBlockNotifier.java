package com.tcn.cosmoslibrary.common.interfaces.block;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * An interface to hand-over functions from a {@link Block} to a
 * {@link BlockEntity}.
 */
public interface IBlockNotifier {

	public BlockState playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player);

	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack);

	public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving);

	public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving);
}