package com.tcn.cosmoslibrary.common.interfaces.item;

import net.minecraft.world.item.ItemStack;

/**
 * Used to aid in providing advanced wrench functionality.
 */
public abstract interface ICosmosToolAdvanced {
	
	/**
	 * Used to determine if the wrench is currently active.
	 * @param paramstack
	 * @return
	 */
	public abstract boolean isActive(ItemStack paramstack);
}
