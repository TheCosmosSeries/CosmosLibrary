package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotRestrictedAccess extends Slot {

	public int stackSize;
	public boolean mayPlace;
	public boolean mayPickup;

	public SlotRestrictedAccess(Container containerIn, int indexIn, int xPos, int yPos, boolean mayPlace) {
		this(containerIn, indexIn, xPos, yPos, 64, mayPlace, true);
	}
	
	public SlotRestrictedAccess(Container containerIn, int indexIn, int xPos, int yPos, boolean mayPlace, boolean mayPickup) {
		this(containerIn, indexIn, xPos, yPos, 64, mayPlace, mayPickup);
	}
	
	public SlotRestrictedAccess(Container containerIn, int indexIn, int xPos, int yPos, int stackSizeIn, boolean mayPlace, boolean mayPickup) {
		super(containerIn, indexIn, xPos, yPos);
		this.stackSize = stackSizeIn;
		this.mayPlace = mayPlace;
		this.mayPickup = mayPickup;
	}

	@Override
	public boolean mayPlace(ItemStack stackIn) {
		return this.mayPlace;
	}

	@Override
	public int getMaxStackSize() {
		return this.stackSize;
	}
	
	@Override
	public boolean mayPickup(Player playerIn) {
		return this.mayPickup;
	}
}