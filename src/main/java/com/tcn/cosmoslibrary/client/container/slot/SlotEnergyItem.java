package com.tcn.cosmoslibrary.client.container.slot;

import com.tcn.cosmoslibrary.energy.interfaces.ICosmosEnergyItem;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotEnergyItem extends Slot {
	
	public SlotEnergyItem(Container containerIn, int indexIn, int xPos, int yPos) {
		super(containerIn, indexIn, xPos, yPos);
	}

	public boolean isItemValid(ItemStack stackIn) {
		if (stackIn.getItem() instanceof ICosmosEnergyItem) {
			return true;
		}
		
		return false;
	}
	
	
	@Override
	public boolean mayPlace(ItemStack stackIn) {
		if (stackIn.getItem() instanceof ICosmosEnergyItem) {
			return true;
		}
		
		return false;
	}
	
}
