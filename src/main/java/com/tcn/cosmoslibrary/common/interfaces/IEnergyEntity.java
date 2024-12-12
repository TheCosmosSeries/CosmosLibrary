package com.tcn.cosmoslibrary.common.interfaces;

import net.minecraft.core.Direction;

public interface IEnergyEntity {

	/**
	 * Adds energy to the storage. Returns quantity of energy that was accepted.
	 *
	 * @param maxReceive Maximum amount of energy to be inserted.
	 * @param simulate   If TRUE, the insertion will only be simulated.
	 * @return Amount of energy that was (or would have been, if simulated) accepted
	 *         by the storage.
	 */
	int receiveEnergy(Direction directionIn, int maxReceive, boolean simulate);

	/**
	 * Removes energy from the storage. Returns quantity of energy that was removed.
	 *
	 * @param maxExtract Maximum amount of energy to be extracted.
	 * @param simulate   If TRUE, the extraction will only be simulated.
	 * @return Amount of energy that was (or would have been, if simulated)
	 *         extracted from the storage.
	 */
	int extractEnergy(Direction directionIn, int maxExtract, boolean simulate);

	/**
	 * Returns the amount of energy currently stored.
	 */
	int getEnergyStored();

	/**
	 * Returns the maximum amount of energy that can be stored.
	 */
	int getMaxEnergyStored();

	/**
	 * Returns if this storage can have energy extracted. If this is false, then any
	 * calls to extractEnergy will return 0.
	 */
	boolean canExtract(Direction directionIn);

	/**
	 * Used to determine if this storage can receive energy. If this is false, then
	 * any calls to receiveEnergy will return 0.
	 */
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
