package com.tcn.cosmoslibrary.common.capability;

import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.energy.IEnergyStorage;

public interface IEnergyCapBE {

	public IEnergyStorage getEnergyCapability(@Nullable Direction directionIn);
}
