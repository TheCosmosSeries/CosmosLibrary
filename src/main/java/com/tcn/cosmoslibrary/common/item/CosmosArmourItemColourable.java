package com.tcn.cosmoslibrary.common.item;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class CosmosArmourItemColourable extends ArmorItem {
	
	public CosmosArmourItemColourable(Holder<ArmorMaterial> materialIn, Type typeIn, Item.Properties builderIn) {
		super(materialIn, typeIn, builderIn);
	}
}