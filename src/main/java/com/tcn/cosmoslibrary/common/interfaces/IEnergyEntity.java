package com.tcn.cosmoslibrary.common.interfaces;

import net.minecraft.core.Direction;

public interface IEnergyEntity {
	int receiveEnergy(Direction directionIn, int maxReceive, boolean simulate);
	int extractEnergy(Direction directionIn, int maxExtract, boolean simulate);
	int getEnergyStored();
	int getMaxEnergyStored();
	boolean canExtract(Direction directionIn);
	boolean canReceive(Direction directionIn);
	void setMaxTransfer(int maxTransfer);
	void setMaxReceive(int maxReceive);
	void setMaxExtract(int maxExtract);
	int getMaxExtract();
	int getMaxReceive();
	void setEnergyStored(int stored);
	void modifyEnergyStored(int stored);
	boolean hasEnergy();
	int getEnergyScaled(int scale);
}
