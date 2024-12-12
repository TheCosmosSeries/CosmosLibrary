package com.tcn.cosmoslibrary.common.blockentity;

import com.tcn.cosmoslibrary.common.interfaces.blockentity.IBlockEntityUpdateable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CosmosBlockEntityUpdateable extends BlockEntity implements IBlockEntityUpdateable {

	public CosmosBlockEntityUpdateable(BlockEntityType<?> typeIn, BlockPos posIn, BlockState stateIn) {
		super(typeIn, posIn, stateIn);
	}

	@Override
	public void sendUpdates(boolean forceUpdate) {
		this.setChanged();
		
		if (this.level != null) {
			BlockState state = this.getBlockState();
			
			this.level.sendBlockUpdated(this.getBlockPos(), state, state, 3);
			this.level.markAndNotifyBlock(this.getBlockPos(), this.level.getChunkAt(this.getBlockPos()), state, state, 3, 0);
			
			if (forceUpdate) {
				if (!this.level.isClientSide) {
					level.setBlockAndUpdate(this.getBlockPos(), state.updateShape(Direction.DOWN, state, this.level, this.getBlockPos(), this.getBlockPos().below()));
				}
			}
		}
	}
}