package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class SlotStackSize extends Slot {

	private int stackSize;
	
	public SlotStackSize(Container containerIn, int indexIn, int xPos, int yPos, int stackSizeIn) {
		super(containerIn, indexIn, xPos, yPos);
		
		this.stackSize = stackSizeIn;
	}

	public int getMaxStackSize() {
		return this.stackSize;
	}
}