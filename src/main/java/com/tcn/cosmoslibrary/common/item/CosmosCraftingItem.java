package com.tcn.cosmoslibrary.common.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CosmosCraftingItem extends Item {
	
	private int durabilityPerUse;

	public CosmosCraftingItem(Properties properties, int stackSize, int maxDamage, int durabilityPerUse) {
		super(properties.stacksTo(stackSize).durability(maxDamage));
		
		this.durabilityPerUse = durabilityPerUse;
	}

	@Override
	public boolean hasCraftingRemainingItem(ItemStack stack) {		
		return true;
	}
	
	@Override
	public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
		ItemStack stack = itemStack.copy();
		stack.setDamageValue(stack.getDamageValue() + this.durabilityPerUse);
		return stack;
	}
}