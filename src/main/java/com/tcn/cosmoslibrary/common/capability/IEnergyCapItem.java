package com.tcn.cosmoslibrary.common.capability;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.energy.IEnergyStorage;

public interface IEnergyCapItem extends ItemLike {

	public IEnergyStorage getEnergyCapability(ItemStack stackIn);
}
