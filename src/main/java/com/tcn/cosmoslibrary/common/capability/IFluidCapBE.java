package com.tcn.cosmoslibrary.common.capability;

import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public interface IFluidCapBE {

	public IFluidHandler getFluidCapability(@Nullable Direction directionIn);
}