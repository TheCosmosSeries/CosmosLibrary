package com.tcn.cosmoslibrary.common.block;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CosmosBlockModelUnplaceable extends CosmosBlock {

	public CosmosBlockModelUnplaceable(Block.Properties properties) {
		super(properties);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return Blocks.AIR.defaultBlockState();
	}
}