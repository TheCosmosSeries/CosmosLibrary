package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SlotColourItem extends Slot {

	public Item overrideItem;
	private int stackSize;

	public SlotColourItem(Container containerIn, int indexIn, int xPos, int yPos, Item overrideItemIn, int stackSizeIn) {
		super(containerIn, indexIn, xPos, yPos);
		this.overrideItem = overrideItemIn;
		this.stackSize = stackSizeIn;
	}

	@Override
	public boolean mayPlace(ItemStack stackIn) {
		if (stackIn != null) {
			if (DyeColor.getColor(stackIn) != null) {
				return true;
			} else if (stackIn.getItem().equals(this.overrideItem)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public int getMaxStackSize() {
		return this.stackSize;
	}
}