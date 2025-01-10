package com.tcn.cosmoslibrary.common.chat;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class CosmosChatUtil {
	
	public static void sendClientPlayerMessage(Player playerIn, Component comp) {
		if (playerIn.level().isClientSide()) {
			playerIn.sendSystemMessage(comp);
		}
	}

	public static void sendServerPlayerMessage(Player playerIn, Component comp) {
		if (playerIn instanceof ServerPlayer) {
			playerIn.sendSystemMessage(comp);
		}
		
		if (!playerIn.level().isClientSide()) {
			//playerIn.sendMessage(comp, UUID.randomUUID());
		}
	}
	
	public static void sendPlayerMessageServer(ServerPlayer playerIn, Component comp) {
		playerIn.sendSystemMessage(comp);
	}
}
