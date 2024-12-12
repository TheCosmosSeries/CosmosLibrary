package com.tcn.cosmoslibrary.registry.gson.object;

import com.tcn.cosmoslibrary.common.enums.EnumConnectionType;
import com.tcn.cosmoslibrary.common.nbt.CosmosNBTHelper;
import com.tcn.cosmoslibrary.common.nbt.CosmosNBTHelper.Const;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class ObjectConnectionType {

	private static final String NBT_TYPE_KEY = "type";
	
	private BlockPos pos;
	private EnumConnectionType type;
	
	public ObjectConnectionType(BlockPos posIn, EnumConnectionType typeIn) {
		this.setPos(posIn);
		this.setType(typeIn);
	}

	public BlockPos getPos() {
		return this.pos;
	}

	public void setPos(BlockPos pos) {
		this.pos = pos;
	}

	public EnumConnectionType getType() {
		return this.type;
	}

	public void setType(EnumConnectionType type) {
		this.type = type;
	}
	
	public static ObjectConnectionType readFromNBT(CompoundTag compound) {
		CompoundTag block_pos = compound.getCompound(Const.NBT_POS_KEY);
		
		int x = block_pos.getInt(CosmosNBTHelper.Const.NBT_POS_X_KEY);
		int y = block_pos.getInt(CosmosNBTHelper.Const.NBT_POS_Y_KEY);
		int z = block_pos.getInt(CosmosNBTHelper.Const.NBT_POS_Z_KEY);

		BlockPos pos = new BlockPos(x, y, z);
		
		String s = compound.getString(NBT_TYPE_KEY);
		
		EnumConnectionType type = EnumConnectionType.getStateFromName(s);
		
		return new ObjectConnectionType(pos, type);
	}
	
	public void writeToNBT(CompoundTag compound) {
		String name = this.getType().getName();
		
		compound.putString(NBT_TYPE_KEY, name);
		
		int x = this.getPos().getX();
		int y = this.getPos().getY();
		int z = this.getPos().getZ();
		
		CompoundTag block_pos = new CompoundTag();
		
		block_pos.putInt(CosmosNBTHelper.Const.NBT_POS_X_KEY, x);
		block_pos.putInt(CosmosNBTHelper.Const.NBT_POS_Y_KEY, y);
		block_pos.putInt(CosmosNBTHelper.Const.NBT_POS_Z_KEY, z);
		
		compound.put(Const.NBT_POS_KEY, block_pos);
	}
}
