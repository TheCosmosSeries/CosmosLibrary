package com.tcn.cosmoslibrary.common.lib;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class FluidHelper {
	
	public static ItemStack useItemSafely(ItemStack stack) {
		if (stack.getCount() == 1) {
			if (stack.getItem().hasCraftingRemainingItem(stack))
				return stack.getItem().getCraftingRemainingItem(stack);
			else
				return null;
		} else {
			stack.split(1);
			return stack;
		}
	}

	public static void dropStackInWorld(Level world, BlockPos pos, ItemStack stack) {
		if (!world.isClientSide && world.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
			float f = 0.7F;
			double d0 = (double) (world.random.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			double d1 = (double) (world.random.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			double d2 = (double) (world.random.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			ItemEntity entityitem = new ItemEntity(world, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, stack);
			entityitem.setPickUpDelay(10); 
			world.addFreshEntity(entityitem);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getTankStackFromData(Block block) {
		ItemStack stack = new ItemStack(Item.byBlock(block));
		CompoundTag tag = new CompoundTag();
		stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
		
		//stack.setTag(tag);
		
		stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, data1 -> CustomData.of(tag));
		return stack;
	}
}