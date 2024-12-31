package com.tcn.cosmoslibrary.energy;

import net.neoforged.neoforge.energy.IEnergyStorage;

public class CosmosEnergyUtil {
	public static boolean hasEnergy(IEnergyStorage storage) {
		return storage.getEnergyStored() > 0;
	}
	
	public static boolean isEnergyFull(IEnergyStorage storage) {
		return storage.getEnergyStored() == storage.getMaxEnergyStored();
	}

	public static boolean isEnergyPartial(IEnergyStorage storage) {
		return storage.getEnergyStored() < storage.getMaxEnergyStored();
	}
}
