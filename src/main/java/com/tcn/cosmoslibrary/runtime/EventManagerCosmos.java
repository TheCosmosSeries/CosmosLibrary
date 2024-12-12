package com.tcn.cosmoslibrary.runtime;

import com.tcn.cosmoslibrary.CosmosLibrary;
import com.tcn.cosmoslibrary.energy.item.CosmosEnergyShieldItem;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = CosmosLibrary.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class EventManagerCosmos {
	
	@SubscribeEvent
	public static void onLivingAttackEvent(LivingDamageEvent.Pre event) {
		float damage = event.getOriginalDamage();
		
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		
		Player player = (Player) event.getEntity();
		ItemStack stack = player.getUseItem();
		
		if (damage > 0.0F && !stack.isEmpty() && stack.getItem() instanceof CosmosEnergyShieldItem && player.isUsingItem()) {
			CosmosEnergyShieldItem shieldItem = (CosmosEnergyShieldItem) stack.getItem();
			
			shieldItem.damageItem(stack, 0, player, (playerX) -> {  });
		}
	}
}