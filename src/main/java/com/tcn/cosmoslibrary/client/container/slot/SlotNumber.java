package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class SlotNumber extends Slot {

	private int stackSize;
	
	public SlotNumber(Container containerIn, int indexIn, int posX, int posY, int stackSizeIn) {
		super(containerIn, indexIn, posX, posY);
		
		this.stackSize = stackSizeIn;
	}

	public int getMaxStackSize() {
		return this.stackSize;
	}
}