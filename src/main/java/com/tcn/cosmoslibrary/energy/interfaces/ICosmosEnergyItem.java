package com.tcn.cosmoslibrary.energy.interfaces;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

/**
 * An interface that allows the addition of Energy to Items.
 * 
 * @author TheCosmicNebula
 *
 */
public interface ICosmosEnergyItem {

	int maxEnergyStored = 0;
	int maxExtract = 0;
	int maxReceive = 0;
	int maxUse = 0;
	boolean doesExtract = true;
	boolean doesCharge = true;
	boolean doesDisplayEnergyInTooltip = true;
	ComponentColour barColour = ComponentColour.RED;

	public int getMaxEnergyStored(ItemStack stackIn);
	public int getMaxExtract(ItemStack stackIn);
	public int getMaxReceive(ItemStack stackIn); 
	public int getMaxUse(ItemStack stackIn);
	public boolean doesExtract(ItemStack stackIn);
	public boolean doesCharge(ItemStack stackIn);
	public boolean doesDisplayEnergyInTooltip(ItemStack stackIn);
	
	default boolean hasEnergy(ItemStack stackIn) {
		if (this.getEnergy(stackIn) > this.getMaxEnergyStored(stackIn)) {
			this.setEnergy(stackIn, this.getMaxEnergyStored(stackIn));
		}
		return this.getEnergy(stackIn) > 0;
	}

	default int getEnergy(ItemStack stackIn) {
		return !stackIn.has(DataComponents.CUSTOM_DATA) ? 0 : stackIn.get(DataComponents.CUSTOM_DATA).copyTag().getInt("energy");
	}
	
	default int setEnergy(ItemStack stackIn, int energy) {
		//stackIn.setDamageValue(0);
		if (stackIn.has(DataComponents.CUSTOM_DATA)) {
			CompoundTag tag = stackIn.get(DataComponents.CUSTOM_DATA).copyTag();
			tag.putInt("energy", Math.max(0, energy));
			stackIn.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
		} else {
			CompoundTag tag = new CompoundTag();
			tag.putInt("energy", Math.max(0, energy));
			stackIn.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
		}
		return energy;
	}
	
	default boolean canReceiveEnergy(ItemStack stackIn) {
		return this.getEnergy(stackIn) < this.getMaxEnergyStored(stackIn);
	}

	default boolean canExtractEnergy(ItemStack stackIn) {
		return this.getEnergy(stackIn) > 0;
	}

	public double getScaledEnergy(ItemStack stackIn, int scaleIn);
	public double getScaledEnergy(ItemStack stackIn, float scaleIn);
	
	public int receiveEnergy(ItemStack stackIn, int energy, boolean simulate);
	public int extractEnergy(ItemStack stackIn, int energy, boolean simulate);
}