package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SlotColourItem extends Slot {

	public Item item;
	public int limit;

	public SlotColourItem(Container containerIn, int indexIn, int xPos, int yPos, Item itemIn, int stackLimitIn) {
		super(containerIn, indexIn, xPos, yPos);
		this.item = itemIn;
		this.limit = stackLimitIn;
	}

	@Override
	public boolean mayPlace(ItemStack stackIn) {
		if (stackIn != null) {
			Item item = stackIn.getItem();
			
			if (item != null) {
				DyeColor dyeColour = DyeColor.getColor(stackIn);
				
				if (dyeColour != null) {
					return true;
				} else if (item == this.item) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	@Override
	public int getMaxStackSize() {
		return this.limit;
	}
}