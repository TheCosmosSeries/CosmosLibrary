package com.tcn.cosmoslibrary.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CosmosBlockUnbreakable extends Block {

	public CosmosBlockUnbreakable(Block.Properties properties) {
		super(properties.strength(-1.0F, 3600000.0F));
	}

	@Override
	public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
        return false;
    }
	
	@Override
	public boolean canEntityDestroy(BlockState state, BlockGetter world, BlockPos pos, Entity entity) {
		return false;
	}
	
}