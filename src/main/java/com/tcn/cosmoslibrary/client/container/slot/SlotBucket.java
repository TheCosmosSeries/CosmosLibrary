package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidUtil;

public class SlotBucket extends Slot {

	public SlotBucket(Container containerIn, int indexIn, int xPos, int yPos) {
		super(containerIn, indexIn, xPos, yPos);
	}

	@Override
	public boolean mayPlace(ItemStack stackIn) {
		if (stackIn != null) {
			if (FluidUtil.getFluidHandler(stackIn).isPresent()) {
				return true;
			} else if (stackIn.getItem() instanceof BucketItem) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	@Override
	public int getMaxStackSize() {
		return 16;
	}
}