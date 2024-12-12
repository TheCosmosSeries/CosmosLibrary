package com.tcn.cosmoslibrary.common.block;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings({"unchecked"})
public class CosmosEntityBlock extends Block implements EntityBlock {

	protected CosmosEntityBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos state, BlockState p_153216_) {
		return null;
	}

	@Override
	public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int p_49229_, int p_49230_) {
		super.triggerEvent(state, level, pos, p_49229_, p_49230_);
		BlockEntity blockentity = level.getBlockEntity(pos);
		return blockentity == null ? false : blockentity.triggerEvent(p_49229_, p_49230_);
	}

	@Nullable
	@Override
	public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		BlockEntity blockentity = level.getBlockEntity(pos);
		return blockentity instanceof MenuProvider ? (MenuProvider) blockentity : null;
	}

	@Nullable
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> typeA, BlockEntityType<E> typeE, BlockEntityTicker<? super E> ticker) {
		return typeE == typeA ? (BlockEntityTicker<A>) ticker : null;
	}
}