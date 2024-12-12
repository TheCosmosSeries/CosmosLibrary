package com.tcn.cosmoslibrary.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ACosmosBlockEntity extends BlockEntity {

    public ACosmosBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos posIn, BlockState stateIn) {
		super(tileEntityTypeIn, posIn, stateIn);
	}

	@Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider provider) {
		super.onDataPacket(net, pkt, provider);
		CompoundTag tag_ = pkt.getTag();
		
		this.handleUpdateTag(tag_, provider);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		CompoundTag tag = new CompoundTag();
		this.saveAdditional(tag, registries);
		return tag;
	}

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void fireEvent(int id, int process) {
        this.getLevel().blockEvent(this.getBlockPos(), this.getBlockState().getBlock(), id, process);
    }

    public BlockPos getCoordSet() {
        return this.getBlockPos();
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + getCoordSet();
    }
}