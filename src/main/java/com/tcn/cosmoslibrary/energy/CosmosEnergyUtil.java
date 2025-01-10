package com.tcn.cosmoslibrary.energy;

import com.tcn.cosmoslibrary.common.interfaces.blockentity.IEnergyHolder;
import com.tcn.cosmoslibrary.energy.interfaces.ICosmosEnergyItem;
import com.tcn.cosmoslibrary.energy.interfaces.IEnergyStorageBulk;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class CosmosEnergyUtil {
	
	public static boolean hasEnergy(IEnergyStorage storage) {
		return storage.getEnergyStored() > 0;
	}
	
	public static boolean isEnergyFull(IEnergyStorage storage) {
		return storage.getEnergyStored() == storage.getMaxEnergyStored();
	}

	public static boolean isEnergyFull(IEnergyHolder storage) {
		return storage.getEnergyStored() == storage.getMaxEnergyStored();
	}

	public static boolean isEnergyPartial(IEnergyStorage storage) {
		return storage.getEnergyStored() < storage.getMaxEnergyStored();
	}
	
	public static IEnergyStorage getDefault(ItemStack stackIn, ICosmosEnergyItem item) {
		return new IEnergyStorage() {
			
			@Override
			public int getEnergyStored() {
				return item.getEnergy(stackIn);
			}
	
			@Override
			public int getMaxEnergyStored() {
				return item.getMaxEnergyStored(stackIn);
			}
	
			@Override
			public int receiveEnergy(int maxReceive, boolean simulate) {
				return item.receiveEnergy(stackIn, maxReceive, simulate);
			}

			@Override
			public int extractEnergy(int maxExtract, boolean simulate) {
				return item.extractEnergy(stackIn, maxExtract, simulate);
			}
			
			@Override
			public boolean canReceive() {
				return item.canReceiveEnergy(stackIn) && item.doesCharge(stackIn);
			}
	
			@Override
			public boolean canExtract() {
				return item.canExtractEnergy(stackIn) && item.doesExtract(stackIn);
			}
		};
	}

	public static IEnergyStorageBulk getDefaultBulk(ItemStack stackIn, ICosmosEnergyItem item) {
		return new IEnergyStorageBulk() {
			
			@Override
			public int getEnergyStored() {
				return item.getEnergy(stackIn);
			}
	
			@Override
			public int getMaxEnergyStored() {
				return item.getMaxEnergyStored(stackIn);
			}
	
			@Override
			public int receiveEnergy(int maxReceive, boolean simulate) {
				return item.receiveEnergy(stackIn, maxReceive, simulate);
			}

			@Override
			public int extractEnergy(int maxExtract, boolean simulate) {
				return item.extractEnergy(stackIn, maxExtract, simulate);
			}
			
			@Override
			public boolean canReceive() {
				return item.canReceiveEnergy(stackIn) && item.doesCharge(stackIn);
			}
	
			@Override
			public boolean canExtract() {
				return item.canExtractEnergy(stackIn) && item.doesExtract(stackIn);
			}
		};
	}
}
