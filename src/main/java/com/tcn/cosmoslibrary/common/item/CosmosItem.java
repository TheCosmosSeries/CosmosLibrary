package com.tcn.cosmoslibrary.common.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CosmosItem extends Item {
	
	public boolean has_effect;
	
	public CosmosItem(Item.Properties properties){
		super(properties);
		
		this.has_effect = false;
	}
	
	public CosmosItem(Item.Properties properties, boolean has_effect){
		super(properties);
		
		this.has_effect = has_effect;
	}
	
	@Override
	public boolean isFoil(ItemStack stack) {
		return this.has_effect;
	}
}