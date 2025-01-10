package com.tcn.cosmoslibrary.energy.item;

import com.tcn.cosmoslibrary.energy.CosmosEnergyUtil;
import com.tcn.cosmoslibrary.energy.interfaces.IEnergyStorageBulk;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class CosmosEnergyStorageItem extends CosmosEnergyItem {

	public CosmosEnergyStorageItem(Item.Properties properties, Properties energyProperties) {
		super(properties, energyProperties);
	}
	
	@Override
	public boolean isDamageable(ItemStack stack) {
		return false;
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack stackIn = playerIn.getItemInHand(handIn);

		if (playerIn.isShiftKeyDown()) {
			if (this.hasEnergy(stackIn)) {
				this.setActive(!this.isActive(stackIn), stackIn);
			}
			
			playerIn.swing(handIn);
			return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
		}
		
		return InteractionResultHolder.fail(playerIn.getItemInHand(handIn));
	}
	
	@Override
	public boolean isFoil(ItemStack stackIn) {
		return this.isActive(stackIn);
	}

	@Override
	public void inventoryTick(ItemStack stackIn, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!worldIn.isClientSide()) {
			if (this.isActive(stackIn)) {
				if (this.hasEnergy(stackIn)) {
					if (itemSlot >= 0 && itemSlot <= 8) {
						if (entityIn instanceof ServerPlayer serverPlayer) {
							Inventory inv = serverPlayer.getInventory();
							
							for (int i = 0; i < inv.getContainerSize(); i++) {
								ItemStack testStack = inv.getItem(i);
								
								Object object = testStack.getCapability(Capabilities.EnergyStorage.ITEM);
								
								if (!(object instanceof IEnergyStorageBulk)) {
									if (object instanceof IEnergyStorage energyItem) {
										if (energyItem.canReceive()) {
											this.extractEnergy(stackIn, energyItem.receiveEnergy(this.getMaxExtract(stackIn), false), false);
										}
									}
								}
							}
						}
					} else {
						this.setActive(false, stackIn);
					}
				} else {
					this.setActive(false, stackIn);
				}
			}
		}
	}

	public boolean isActive(ItemStack stackIn) {
		if (stackIn.has(DataComponents.CUSTOM_DATA)) {
			CompoundTag stack_tag = stackIn.get(DataComponents.CUSTOM_DATA).copyTag();
			
			if (stack_tag.contains("nbt_data")) {
				CompoundTag nbt_data = stack_tag.getCompound("nbt_data");
				
				if (nbt_data.contains("active")) {
					return nbt_data.getBoolean("active");
				}
			}
		}
		
		return false;
	}
	
	public void setActive(boolean active, ItemStack stackIn) {
		if (stackIn.has(DataComponents.CUSTOM_DATA)) {
			CompoundTag stack_tag = stackIn.get(DataComponents.CUSTOM_DATA).copyTag();
			
			if (stack_tag.contains("nbt_data")) {
				CompoundTag nbt_data = stack_tag.getCompound("nbt_data");
				nbt_data.putBoolean("active", active);
				stackIn.set(DataComponents.CUSTOM_DATA, CustomData.of(stack_tag));
			} else {
				CompoundTag nbt_data = new CompoundTag();
				
				nbt_data.putBoolean("active", active);
				stack_tag.put("nbt_data", nbt_data);
				stackIn.set(DataComponents.CUSTOM_DATA, CustomData.of(stack_tag));

			}
		} else {
			CompoundTag stack_tag = new CompoundTag();
			CompoundTag nbt_data = new CompoundTag();
			
			nbt_data.putBoolean("active", active);
			
			stack_tag.put("nbt_data", nbt_data);
			stackIn.set(DataComponents.CUSTOM_DATA, CustomData.of(stack_tag));
		}
	}

	@Override
	public IEnergyStorageBulk getEnergyCapability(ItemStack stackIn) {
		return CosmosEnergyUtil.getDefaultBulk(stackIn, this);
	}
}