package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class SlotArmourItem extends Slot {

	final EquipmentSlot[] SLOT_IDS = new EquipmentSlot[] { EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET };

	private Player player;
	private int slotIndex;

	public SlotArmourItem(Inventory containerIn, int indexIn, int posX, int posY, Player playerIn, int slotIndexIn) {
		super(containerIn, indexIn, posX, posY);
		
		this.player = playerIn;
		this.slotIndex = slotIndexIn;
	}

	public int getMaxStackSize() {
		return 1;
	}

	public boolean mayPlace(ItemStack stackIn) {
		return stackIn.canEquip(this.SLOT_IDS[this.slotIndex], this.player);
	}

	public boolean mayPickup(Player playerIn) {
		ItemStack itemstack = this.getItem();
		
		
		return !itemstack.isEmpty() && !playerIn.isCreative() && EnchantmentHelper.has(itemstack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE) ? false : super.mayPickup(playerIn);
	}
}
