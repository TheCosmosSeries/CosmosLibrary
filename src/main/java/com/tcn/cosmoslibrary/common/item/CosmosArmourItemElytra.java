package com.tcn.cosmoslibrary.common.item;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CosmosArmourItemElytra extends CosmosArmourItemColourable {
	
	private boolean damageable;

	public CosmosArmourItemElytra(Holder<ArmorMaterial> materialIn, Type typeIn, Item.Properties builderIn, boolean damageableIn) {
		super(materialIn, typeIn, builderIn);
	}

	public boolean isFlyEnabled(ItemStack stackIn) {
		if (this.damageable) {
			return stackIn.getDamageValue() < stackIn.getMaxDamage() - 1;
		}

		return true;
	}
	
	@Override
	public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
		return isFlyEnabled(stack);
	}

	@Override
	public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
		if (damageable) {
			if (!entity.level().isClientSide() && (flightTicks + 1) % 20 == 0) {
				stack.hurtAndBreak(1, entity, EquipmentSlot.CHEST);
			}
			return true;
		}
		return true;
	}
	
}