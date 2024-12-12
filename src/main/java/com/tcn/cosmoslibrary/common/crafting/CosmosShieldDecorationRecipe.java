package com.tcn.cosmoslibrary.common.crafting;

import com.tcn.cosmoslibrary.energy.item.CosmosEnergyShieldItem;
import com.tcn.cosmoslibrary.runtime.ModBusSubscriberCosmos;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;

@SuppressWarnings("deprecation")
public class CosmosShieldDecorationRecipe extends CustomRecipe {

	public CosmosShieldDecorationRecipe(CraftingBookCategory categoryIn) {
		super(categoryIn);
	}

	@Override
	public boolean matches(CraftingInput input, Level level) {
		ItemStack itemstack = ItemStack.EMPTY;
		ItemStack itemstack1 = ItemStack.EMPTY;

		for (int i = 0; i < input.size(); ++i) {
			ItemStack itemstack2 = input.getItem(i);

			if (!itemstack2.isEmpty()) {
				if (itemstack2.getItem() instanceof BannerItem) {
					if (!itemstack1.isEmpty()) {
						return false;
					}

					itemstack1 = itemstack2;
				} else {
					if (!(itemstack2.getItem() instanceof CosmosEnergyShieldItem)) {
						return false;
					}

					if (!itemstack.isEmpty()) {
						return false;
					}

					if (itemstack2.get(DataComponents.BLOCK_ENTITY_DATA).getUnsafe() != null) {
						return false;
					}

					itemstack = itemstack2;
				}
			}
		}

		return !itemstack.isEmpty() && !itemstack1.isEmpty();
	}

	@Override
	public ItemStack assemble(CraftingInput input, Provider registries_) {
		ItemStack itemstack = ItemStack.EMPTY;
		ItemStack itemstack1 = ItemStack.EMPTY;

		for (int i = 0; i < input.size(); ++i) {
			ItemStack itemstack2 = input.getItem(i);

			if (!itemstack2.isEmpty()) {
				if (itemstack2.getItem() instanceof BannerItem) {
					itemstack = itemstack2;
				} else if (itemstack2.getItem() instanceof CosmosEnergyShieldItem) {
					itemstack1 = itemstack2.copy();
				}
			}
		}

		if (itemstack1.isEmpty()) {
			return itemstack1;
		} else {
			CompoundTag compoundtag = itemstack.get(DataComponents.BLOCK_ENTITY_DATA).getUnsafe();
			CompoundTag compoundtag1 = compoundtag == null ? new CompoundTag() : compoundtag.copy();
			compoundtag1.putInt("Base", ((BannerItem) itemstack.getItem()).getColor().getId());
			BlockItem.setBlockEntityData(itemstack1, BlockEntityType.BANNER, compoundtag1);
			return itemstack1;
		}
	}

	@Override
	public boolean canCraftInDimensions(int xIn, int yIn) {
		return xIn * yIn >= 2;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModBusSubscriberCosmos.SHIELD_SERIALIZER.get();
	}

}