package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotCopiedItem extends Slot {

	public SlotCopiedItem(Container containerIn, int indexIn, int xPos, int yPos) {
		super(containerIn, indexIn, xPos, yPos);
	}
	
	@Override
	public boolean mayPickup(Player playerIn) {
		return false;
	}
	
	@Override
	public boolean mayPlace(ItemStack stackIn) {
		return false;
	}
}