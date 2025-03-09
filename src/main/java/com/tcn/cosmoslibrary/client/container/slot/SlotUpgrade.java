package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SlotUpgrade extends Slot {

	public Item specifiedItem;

	public SlotUpgrade(Container containerIn, int indexIn, int xPos, int yPos, Item specifiedItemIn) {
		super(containerIn, indexIn, xPos, yPos);

		this.specifiedItem = specifiedItemIn;
	}

	@Override
	public boolean mayPlace(ItemStack stackIn) {
		return stackIn != null ? stackIn.getItem().equals(this.specifiedItem) : false;
	}

	@Override
	public int getMaxStackSize() {
		return 4;
	}
}