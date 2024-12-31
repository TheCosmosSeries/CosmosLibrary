package com.tcn.cosmoslibrary.common.item;

import java.util.Random;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CosmosCraftingItem extends Item {
	
	private int durabilityPerUse;
	private boolean rand;

	public CosmosCraftingItem(Properties properties, int stackSize, int maxDamage, int maxDurabilityPerUse, boolean rand) {
		super(properties.stacksTo(stackSize).durability(maxDamage));
		
		this.durabilityPerUse = maxDurabilityPerUse;
		this.rand = rand;
	}

	@Override
	public boolean hasCraftingRemainingItem(ItemStack stack) {
		return true;
	}
	
	@Override
	public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
		ItemStack stack = itemStack.copy();
		stack.setDamageValue(stack.getDamageValue() + (this.rand ? Math.max(1, new Random(this.durabilityPerUse).nextInt()) : this.durabilityPerUse));
		return stack.getDamageValue() < stack.getMaxDamage() ? stack : ItemStack.EMPTY;
	}
}