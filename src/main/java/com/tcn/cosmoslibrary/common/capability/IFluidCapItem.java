package com.tcn.cosmoslibrary.common.capability;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

public interface IFluidCapItem extends ItemLike {

	public IFluidHandlerItem getFluidCapability(ItemStack stackIn);
}