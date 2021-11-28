package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotColourItem extends Slot {

	public Item item;
	public int limit;

	public SlotColourItem(IInventory tile, int par3, int par4, int par5, Item par6, int limit) {
		super(tile, par3, par4, par5);
		this.item = par6;
		this.limit = limit;
	}

	@Override
	public boolean mayPlace(ItemStack par1ItemStack) {
		if (par1ItemStack != null) {
			Item item = par1ItemStack.getItem();
			
			if (item != null) {
				DyeColor dyeColour = DyeColor.getColor(par1ItemStack);
				
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