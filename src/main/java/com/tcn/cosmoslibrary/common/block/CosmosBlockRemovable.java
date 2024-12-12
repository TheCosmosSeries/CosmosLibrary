package com.tcn.cosmoslibrary.common.block;

import javax.annotation.Nullable;

import com.tcn.cosmoslibrary.common.lib.CompatHelper;
import com.tcn.cosmoslibrary.common.util.CosmosUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CosmosBlockRemovable extends CosmosBlock {

	public CosmosBlockRemovable(Block.Properties properties) {
		super(properties);
	}

	@Override
	public ItemInteractionResult useItemOn(ItemStack stackIn, BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		player.swing(InteractionHand.MAIN_HAND);
		
		worldIn.sendBlockUpdated(pos, state, state, 3);
		//TileEntity tile = worldIn.getTileEntity(pos);
		
		if (CosmosUtil.holdingWrench(player) && player.isShiftKeyDown() && !worldIn.isClientSide) {
			if (!worldIn.isClientSide) {
				CompatHelper.spawnStack(CompatHelper.generateItemStackFromTile(this), worldIn, pos.getX(), pos.getY(), pos.getZ(), 0);
				worldIn.removeBlock(pos, false);
				return ItemInteractionResult.SUCCESS;
			}
			
			return ItemInteractionResult.SUCCESS;
		}	
		return ItemInteractionResult.FAIL;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
		return p_152134_ == p_152133_ ? (BlockEntityTicker<A>) p_152135_ : null;
	}
}