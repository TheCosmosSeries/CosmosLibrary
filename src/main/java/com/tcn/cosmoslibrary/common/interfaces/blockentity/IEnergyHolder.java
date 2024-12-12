package com.tcn.cosmoslibrary.common.interfaces.blockentity;

public interface IEnergyHolder {
	
	public void setMaxIO(int valueIn);
	public void setMaxReceive(int valueIn);
	public void setMaxExtract(int valueIn);
	
	public int getMaxReceive();
	public int getMaxExtract();
	
	public void setEnergyStored(int valueIn);
	public void modifyEnergyStored(int valueIn);
	
	public int receiveEnergy(int valueIn, boolean simulateIn);
	public int extractEnergy(int valueIn, boolean simulateIn);

	public int getEnergyStored();
	public int getMaxEnergyStored();
	
	public boolean hasEnergyStored();
	
	public int getEnergyStoredScaled(int scaleIn);
}
