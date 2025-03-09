package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class SlotEnergyItem extends Slot {
	
	public SlotEnergyItem(Container containerIn, int indexIn, int xPos, int yPos) {
		super(containerIn, indexIn, xPos, yPos);
	}

	@Override
	public boolean mayPlace(ItemStack stackIn) {
		return stackIn.getCapability(Capabilities.EnergyStorage.ITEM) instanceof IEnergyStorage;
	}
}