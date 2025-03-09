package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SlotSpecifiedItem extends Slot {

	public Item specifiedItem;
	public int stackSize;

	public SlotSpecifiedItem(Container containerIn, int indexIn, int xPos, int yPos, Item specifiedItemIn, int stackSizeIn) {
		super(containerIn, indexIn, xPos, yPos);
		this.specifiedItem = specifiedItemIn;
		this.stackSize = stackSizeIn;
	}

	@Override
	public boolean mayPlace(ItemStack stackIn) {
		return stackIn != null ? stackIn.getItem().equals(this.specifiedItem) : false;
	}

	@Override
	public int getMaxStackSize() {
		return this.stackSize;
	}
}