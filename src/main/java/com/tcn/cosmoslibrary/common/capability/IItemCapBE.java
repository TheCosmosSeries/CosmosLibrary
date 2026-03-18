package com.tcn.cosmoslibrary.common.capability;

import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.items.IItemHandler;

public interface IItemCapBE {

	public IItemHandler getItemCapability(@Nullable Direction directionIn);
}