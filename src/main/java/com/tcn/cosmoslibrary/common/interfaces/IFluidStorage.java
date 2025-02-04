package com.tcn.cosmoslibrary.common.interfaces;

import net.minecraft.core.Direction;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public interface IFluidStorage {
	public int capacity = 0;
	
	public int getFluidCapacity();
	
	public int fill(FluidStack resource, FluidAction doDrain);
	public FluidStack drain(FluidStack resource, FluidAction doDrain);
	public FluidStack drain(int maxDrain, FluidAction doDrain);
	
	public boolean canFill(Direction from, Fluid fluid);
	public boolean canDrain(Direction from, Fluid fluid);
	
	public FluidTank getTank();
}
