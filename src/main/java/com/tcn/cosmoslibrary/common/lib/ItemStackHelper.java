package com.tcn.cosmoslibrary.common.lib;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class ItemStackHelper {

	public static void applyColourToItemStack(ItemStack stackIn, int colour) {
		CustomData data = stackIn.get(DataComponents.CUSTOM_DATA);
		CompoundTag compound = data.copyTag();
		CompoundTag colourData = new CompoundTag();
		
		colourData.putInt("intColour", colour);
		compound.put("colourData", colourData);
		
		stackIn.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, (data1) -> CustomData.of(compound));
	}
	
	public static int getColourFromStack(ItemStack stackIn) {
		CustomData data = stackIn.get(DataComponents.CUSTOM_DATA);
		CompoundTag compound = data.copyTag();
		
		if (compound.contains("colourData")) {
			CompoundTag colourData = compound.getCompound("colourData");
			
			return colourData.getInt("intColour");
		}
		
		return -1;
	}
}
