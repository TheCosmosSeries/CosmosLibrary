package com.tcn.cosmoslibrary.core.teleport;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class CosmosTeleportCore {
	
	public static void shiftPlayerToDimension(Player playerIn, CosmosTeleporter teleporterIn, @Nullable Holder<SoundEvent> soundIn, float volume) {
		if (playerIn instanceof ServerPlayer) {
			ServerPlayer server_player = (ServerPlayer) playerIn;
			ResourceKey<Level> dimension_key = teleporterIn.getDimensionKey();
			
			if (dimension_key != null) {
				MinecraftServer Mserver = ServerLifecycleHooks.getCurrentServer();
				ServerLevel server_world = Mserver.getLevel(dimension_key);
				BlockPos target_pos = teleporterIn.getTargetPos(server_world);
				
				if (server_world != null) {
					if (target_pos != null) {
						double[] position = teleporterIn.getTargetPosA();
												
						DimensionTransition trans = new DimensionTransition(server_world, target_pos.getCenter(), playerIn.getDeltaMovement(), teleporterIn.getTargetRotation()[0], teleporterIn.getTargetRotation()[1], DimensionTransition.DO_NOTHING);
						
						server_player.changeDimension(trans);

						if (!teleporterIn.playVanillaSound() && soundIn != null) {
							server_player.connection.send(new ClientboundSoundPacket(soundIn, SoundSource.AMBIENT, position[0], position[1], position[2], volume, 1, 0));
						}
					} else {
						DimensionTransition trans = new DimensionTransition(server_world, BlockPos.ZERO.getCenter(), playerIn.getDeltaMovement(), teleporterIn.getTargetRotation()[0], teleporterIn.getTargetRotation()[1], DimensionTransition.DO_NOTHING);
						
						server_player.changeDimension(trans);

						if (!teleporterIn.playVanillaSound() && soundIn != null) {
							server_player.connection.send(new ClientboundSoundPacket(soundIn, SoundSource.AMBIENT, server_player.getX(), server_player.getY(), server_player.getZ(), volume, 1, 0));
						}
					}
				}
			}
		}
	}
}