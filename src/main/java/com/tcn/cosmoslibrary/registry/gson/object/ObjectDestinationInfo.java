package com.tcn.cosmoslibrary.registry.gson.object;

import com.tcn.cosmoslibrary.common.nbt.CosmosNBTHelper.Const;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class ObjectDestinationInfo {
	
	private BlockPos pos;
	private float yaw;
	private float pitch;
	
	public static final ObjectDestinationInfo BLANK = new ObjectDestinationInfo(BlockPos.ZERO, 0, 0);
	
	public ObjectDestinationInfo(BlockPos posIn, float yawIn, float pitchIn) {
		this.pos = posIn;
		this.setYaw(yawIn);
		this.setPitch(pitchIn);
	}

	public BlockPos getPos() {
		return this.pos;
	}

	public void setPos(BlockPos pos) {
		this.pos = pos;
	}

	public float getYaw() {
		return this.yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public float getPitch() {
		return this.pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public static ObjectDestinationInfo readFromNBT(CompoundTag compound) {
		CompoundTag block_pos = compound.getCompound(Const.NBT_POS_KEY);
		
		int x = block_pos.getInt(Const.NBT_POS_X_KEY);
		int y = block_pos.getInt(Const.NBT_POS_Y_KEY);
		int z = block_pos.getInt(Const.NBT_POS_Z_KEY);

		BlockPos pos = new BlockPos(x, y, z);
		
		float yaw = compound.getFloat(Const.NBT_POS_YAW_KEY);
		float pitch = compound.getFloat(Const.NBT_POS_PITCH_KEY);
		
		return new ObjectDestinationInfo(pos, yaw, pitch);
	}
	
	public void writeToNBT(CompoundTag compound) {

		float yaw = this.getYaw();
		float pitch = this.getPitch();
		
		compound.putFloat(Const.NBT_POS_YAW_KEY, yaw);
		compound.putFloat(Const.NBT_POS_PITCH_KEY, pitch);
		
		int x = this.getPos().getX();
		int y = this.getPos().getY();
		int z = this.getPos().getZ();
		
		CompoundTag block_pos = new CompoundTag();
		
		block_pos.putInt(Const.NBT_POS_X_KEY, x);
		block_pos.putInt(Const.NBT_POS_Y_KEY, y);
		block_pos.putInt(Const.NBT_POS_Z_KEY, z);

		compound.put(Const.NBT_POS_KEY, block_pos);
	}
}