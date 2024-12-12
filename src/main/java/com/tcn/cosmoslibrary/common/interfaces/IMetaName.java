package com.tcn.cosmoslibrary.common.interfaces;

import net.minecraft.world.item.ItemStack;

/**
 * Used for names with meta.
 * Used for blocks with variants. {@link BlockPlanks}
 */
public interface IMetaName {

	public String getSpecialName(ItemStack stack);
	
}