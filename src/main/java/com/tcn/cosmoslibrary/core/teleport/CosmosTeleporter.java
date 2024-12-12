package com.tcn.cosmoslibrary.core.teleport;

import java.util.function.Function;

import com.tcn.cosmoslibrary.common.lib.MathHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class CosmosTeleporter {

	private ResourceKey<Level> dimension_key;
	private BlockPos target_pos;
	private float target_yaw;
	private float target_pitch;
	private boolean playVanillaSound;
	private boolean sendMessage;
	private boolean safeSpawn;

	public CosmosTeleporter(ResourceKey<Level> dimensionKeyIn, BlockPos targetPosIn, float targetYawIn, float targetPitchIn, boolean playVanillaSoundIn, boolean sendMessageIn, boolean safeSpawnIn) {
		this.dimension_key = dimensionKeyIn;
		this.target_pos = targetPosIn;
		this.target_yaw = targetYawIn;
		this.target_pitch = targetPitchIn;
		this.playVanillaSound = playVanillaSoundIn;
		this.sendMessage = sendMessageIn;
		this.safeSpawn = safeSpawnIn;
	}
	
	public BlockPos getTargetPos(ServerLevel destWorld) {
		if (this.safeSpawn) {
    		EnumSafeTeleport spawnLocation = EnumSafeTeleport.getValidTeleportLocation(destWorld, this.target_pos);
    		
    		if (spawnLocation != EnumSafeTeleport.UNKNOWN) {
    			BlockPos resultPos = spawnLocation.toBlockPos();
    			BlockPos combinedPos = MathHelper.addBlockPos(this.target_pos, resultPos);
    			
    			return BlockPos.containing(combinedPos.getX() + 0.5F, combinedPos.getY(), combinedPos.getZ() + 0.5F);
    		}
    	}
		return BlockPos.ZERO;
	}
	
	public double[] getTargetPosA () {
		return new double[] { this.target_pos.getX(), this.target_pos.getY(), this.target_pos.getZ() };
	}

	public float getTargetYaw() {
		return this.target_yaw;
	}

	public float getTargetPitch() {
		return this.target_pitch;
	}
	
	public float[] getTargetRotation() {
		return new float[] { this.target_yaw, this.target_pitch };
	}

	public ResourceKey<Level> getDimensionKey() {
		return this.dimension_key;
	}

	public static CosmosTeleporter createTeleporter(ResourceKey<Level> dimensionKeyIn, BlockPos targetPosIn, float targetYawIn, float targetPitchIn, boolean playVanillaSoundIn, boolean sendMessageIn, boolean safeSpawnIn) {
		return new CosmosTeleporter(dimensionKeyIn, targetPosIn, targetYawIn, targetPitchIn, playVanillaSoundIn, sendMessageIn, safeSpawnIn);
	}
	
	public boolean playVanillaSound() {
		return this.playVanillaSound;
	}

	public boolean getSendMessage() {
		return this.sendMessage;
	}

	public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
		return repositionEntity.apply(false);
    }
	
    public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld) {
    	return this.playVanillaSound;
    }
}