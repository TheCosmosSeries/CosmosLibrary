package com.tcn.cosmoslibrary.client.container.slot;

import com.tcn.cosmoslibrary.common.item.CosmosArmourItemColourable;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SlotColourableArmourItem extends Slot {

	public int limit;

	public SlotColourableArmourItem(Container containerIn, int indexIn, int xPos, int yPos, int stackLimitIn) {
		super(containerIn, indexIn, xPos, yPos);
		
		this.limit = stackLimitIn;
	}

	@Override
	public boolean mayPlace(ItemStack stackIn) {
		if (stackIn != null) {
			Item item = stackIn.getItem();
			
			if (item != null) {
				if (item instanceof CosmosArmourItemColourable) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		
		return false;
	}

	@Override
	public int getMaxStackSize() {
		return this.limit;
	}
}