package com.tcn.cosmoslibrary.common.lib;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.text.WordUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@SuppressWarnings("deprecation")
public class CompatHelper {
	
	public static void generateStack(Level levelIn, BlockPos pos, HolderLookup.Provider provider) {
		BlockEntity tile = levelIn.getBlockEntity(pos);
		Block block = levelIn.getBlockState(pos).getBlock();
		
		ItemStack stack = new ItemStack(block);
		
		
		if (tile != null) {
			tile.saveToItem(stack, provider);
		}
		
		spawnStack(stack, levelIn, pos.getX(), pos.getY(), pos.getZ(), 0);
		levelIn.removeBlock(pos, false);
	}
	
	public static ItemStack generateItemStackOnRemoval(Level levelIn, BlockEntity blockEntityIn, BlockPos posIn) {
		if (blockEntityIn != null) {
			ItemStack returnStack = new ItemStack(blockEntityIn.getBlockState().getBlock());
	
			blockEntityIn.saveToItem(returnStack, levelIn.registryAccess());
			
			return returnStack;
		}
		return ItemStack.EMPTY;
	}
	
	public static ItemStack generateFluidItemStackOnRemoval(Level levelIn, BlockEntity blockEntityIn, FluidTank fluidTank, BlockPos posIn) {
		if (blockEntityIn != null) {
			ItemStack returnStack = new ItemStack(blockEntityIn.getBlockState().getBlock());
	
			blockEntityIn.saveToItem(returnStack, levelIn.registryAccess());
			
			if (!fluidTank.isEmpty()) {
				returnStack.remove(DataComponents.CUSTOM_NAME);
				
				FluidType type = fluidTank.getFluid().getFluid().getFluidType();
				ResourceLocation location = NeoForgeRegistries.FLUID_TYPES.getKey(type);

				String name = WordUtils.capitalize(location.getPath());
				String[] splitName = name.split("_");
				String newName = "";
				
				for (int i = 0; i < splitName.length; i++) {
					newName = newName + (i == 0 ? "": " ") + WordUtils.capitalize(splitName[i].replace("_", " "));
				}
				
				returnStack.set(DataComponents.CUSTOM_NAME, ComponentHelper.style(ComponentColour.WHITE, "", returnStack.getHoverName().getString() + ":").append(ComponentHelper.style(type.getTemperature() > 1000 ? ComponentColour.ORANGE : ComponentColour.CYAN, "bold", " [" + newName + "]")));	
			}
			
			return returnStack;
		}
		return ItemStack.EMPTY;
	}
	
	public static ItemStack generateItemStackFromBlock(Block block) {
		return new ItemStack(block);
	}

	public static ItemStack generateItemStackFromBlock(BlockState block) {
		return new ItemStack(block.getBlock());
	}
	
	public static ItemStack generateItemStackFromBlock(BlockEntity block) {
		return new ItemStack(block.getBlockState().getBlock());
	}
	
	public static void spawnStack(ItemStack itemStack, Level world, double d, double e, double f, int delayBeforePickup) {
		ItemEntity entityItem = new ItemEntity(world, d, e, f, itemStack);
		entityItem.setPickUpDelay(delayBeforePickup);

		world.addFreshEntity(entityItem);
	}

	@Nonnull
	public static <T> T _null() {
		return null;
	}
}