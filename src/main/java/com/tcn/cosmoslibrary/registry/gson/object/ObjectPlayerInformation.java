package com.tcn.cosmoslibrary.registry.gson.object;

import java.util.UUID;

import com.google.gson.annotations.SerializedName;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class ObjectPlayerInformation {
	
	private static final String NBT_PLAYER_NAME_KEY = "player_name";
	private static final String NBT_PLAYER_UUID_KEY = "player_uuid";
	
	@SerializedName(NBT_PLAYER_NAME_KEY)
	private String player_name;
	
	@SerializedName(NBT_PLAYER_UUID_KEY)
	private UUID player_uuid;
	
	public ObjectPlayerInformation(String player_name, UUID player_uuid) {
		this.player_name = player_name;
		this.player_uuid = player_uuid;
	}
	
	public ObjectPlayerInformation(Player player) {
		this.player_name = player.getDisplayName().getString();
		this.player_uuid = player.getUUID();
	}
	
	public String getPlayerName() {
		return this.player_name;
	}
	
	public void setPlayerName(String player_name) {
		this.player_name = player_name;
	}
	
	public UUID getPlayerUUID() {
		return this.player_uuid;
	}
	
	public void setPlayerUUID(UUID player_uuid) {
		this.player_uuid = player_uuid;
	}
	
	public static ObjectPlayerInformation readFromNBT(CompoundTag compound, String key) {
		if (compound.contains(key)) {
			CompoundTag nbt = compound.getCompound(key);
			
			String name = nbt.getString(NBT_PLAYER_NAME_KEY);
			UUID id = nbt.getUUID(NBT_PLAYER_UUID_KEY);
			
			return new ObjectPlayerInformation(name, id);
		}
		
		return null;
	}
	
	public void writeToNBT(CompoundTag compound, String key) {
		CompoundTag tag = new CompoundTag();
		
		tag.putString(NBT_PLAYER_NAME_KEY, this.getPlayerName());
		tag.putUUID(NBT_PLAYER_UUID_KEY, this.getPlayerUUID());
		
		compound.put(key, tag);
	}
	
	public static ObjectPlayerInformation readFromNBT(CompoundTag compound) {
		String name = compound.getString(NBT_PLAYER_NAME_KEY);
		UUID id = compound.getUUID(NBT_PLAYER_UUID_KEY);
		
		return new ObjectPlayerInformation(name, id);
	}
	
	public void writeToNBT(CompoundTag compound) {
		compound.putString(NBT_PLAYER_NAME_KEY, this.getPlayerName());
		compound.putUUID(NBT_PLAYER_UUID_KEY, this.getPlayerUUID());
	}
}
