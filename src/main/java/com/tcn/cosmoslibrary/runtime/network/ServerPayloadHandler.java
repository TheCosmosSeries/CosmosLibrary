package com.tcn.cosmoslibrary.runtime.network;

import com.tcn.cosmoslibrary.CosmosLibrary;
import com.tcn.cosmoslibrary.common.interfaces.blockentity.IBEUILockable;
import com.tcn.cosmoslibrary.common.interfaces.blockentity.IBEUIMode;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class ServerPayloadHandler {
	public static void handleDataOnNetwork(final ICosmosPacket data, final IPayloadContext context) {
		if (data instanceof PacketUIHelp packet) {
			context.enqueueWork(() -> {
				MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
				ServerLevel world = server.getLevel(packet.dimension());
				BlockEntity entity = world.getBlockEntity(packet.pos());
				
				if (entity instanceof IBEUIMode blockEntity) {
					blockEntity.cycleUIHelp();
				} else {
					CosmosLibrary.CONSOLE.debugWarn("[Packet Delivery Failure] <uihelp> Block Entity not equal to expected.");
				}
			}).exceptionally(e -> {
				context.disconnect(Component.translatable("[Packet Delivery Failure] <uihelp> Block Entity not equal to expected.", e.getMessage()));
				return null;
			});
		}
		
		if (data instanceof PacketUILock packet) {
			context.enqueueWork(() -> {
				MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
				ServerLevel world = server.getLevel(packet.dimension());
				BlockEntity entity = world.getBlockEntity(packet.pos());
				
				Player player = world.getPlayerByUUID(packet.uuid());
				
				if (entity instanceof IBEUILockable blockEntity) {
					if (blockEntity.checkIfOwner(player)) {
						blockEntity.cycleUILock();
					}
				} else {
					CosmosLibrary.CONSOLE.debugWarn("[Packet Delivery Failure] <uilock> Block Entity not equal to expected.");
				}
			}).exceptionally(e -> {
				context.disconnect(Component.translatable("[Packet Delivery Failure] <uilock> Block Entity not equal to expected.", e.getMessage()));
				return null;
			});
		}

		if (data instanceof PacketUIMode packet) {
			context.enqueueWork(() -> {
				MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
				ServerLevel world = server.getLevel(packet.dimension());
				BlockEntity entity = world.getBlockEntity(packet.pos());
				
				if (entity instanceof IBEUIMode blockEntity) {
					blockEntity.cycleUIMode();
				} else {
					CosmosLibrary.CONSOLE.debugWarn("[Packet Delivery Failure] <uimode> Block Entity not equal to expected.");
				}
			}).exceptionally(e -> {
				context.disconnect(Component.translatable("[Packet Delivery Failure] <uilock> Block Entity not equal to expected.", e.getMessage()));
				return null;
			});
		}
	}
}
