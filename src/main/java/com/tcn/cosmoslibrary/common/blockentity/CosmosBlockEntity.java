package com.tcn.cosmoslibrary.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Base TileEntity class.
 * Rarely used.
 */
public class CosmosBlockEntity extends BlockEntity {

	public CosmosBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos posIn, BlockState stateIn) {
		super(tileEntityTypeIn, posIn, stateIn);
	} 
}