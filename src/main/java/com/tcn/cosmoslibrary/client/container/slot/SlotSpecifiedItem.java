package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SlotSpecifiedItem extends Slot {

	public Item item;
	public int stackLimit;

	public SlotSpecifiedItem(Container containerIn, int indexIn, int xPos, int yPos, Item itemIn, int stackLimitIn) {
		super(containerIn, indexIn, xPos, yPos);
		this.item = itemIn;
		this.stackLimit = stackLimitIn;
	}

	@Override
	public boolean mayPlace(ItemStack stackIn) {
		if (stackIn != null) {
			Item item = stackIn.getItem();
			return (item != null) && (item == this.item);
		}
		return false;
	}

	@Override
	public int getMaxStackSize() {
		return this.stackLimit;
	}
}