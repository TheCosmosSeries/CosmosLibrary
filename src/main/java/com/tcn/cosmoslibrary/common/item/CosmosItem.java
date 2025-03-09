package com.tcn.cosmoslibrary.common.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CosmosItem extends Item {
	
	public boolean isFoil;
	
	public CosmosItem(Item.Properties properties){
		this(properties, false);
	}
	
	public CosmosItem(Item.Properties properties, boolean isFoilIn){
		super(properties);
		
		this.isFoil = isFoilIn;
	}
	
	@Override
	public boolean isFoil(ItemStack stack) {
		return this.isFoil;
	}
}