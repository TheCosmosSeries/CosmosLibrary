package com.tcn.cosmoslibrary.registry.gson.object;

import com.tcn.cosmoslibrary.common.nbt.CosmosNBTHelper.Const;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class ObjectBlockPosDimension {

	private BlockPos pos;
	private ResourceLocation dimension;
	
	public ObjectBlockPosDimension(BlockPos posIn, ResourceLocation dimensionIn) {
		this.pos = posIn;
		this.dimension = dimensionIn;
	}

	public BlockPos getPos() {
		return this.pos;
	}

	public void setPos(BlockPos pos) {
		this.pos = pos;
	}

	public ResourceLocation getDimension() {
		return this.dimension;
	}

	public void setDimension(ResourceLocation dimensionIn) {
		this.dimension = dimensionIn;
	}

	public static ObjectBlockPosDimension load(CompoundTag compound) {
		int x = compound.getInt(Const.NBT_POS_X_KEY);
		int y = compound.getInt(Const.NBT_POS_Y_KEY);
		int z = compound.getInt(Const.NBT_POS_Z_KEY);
		
		BlockPos posOut = new BlockPos(x, y, z);
		
		ResourceLocation dimensionOut = ResourceLocation.parse("");
		
		if (compound.contains(Const.NBT_DIMENSION_KEY)) {
			CompoundTag dimensionTag = compound.getCompound(Const.NBT_DIMENSION_KEY);
	
			String namespace = dimensionTag.getString(Const.NBT_NAMESPACE_KEY);
			String path = dimensionTag.getString(Const.NBT_PATH_KEY);
	
			dimensionOut = ResourceLocation.fromNamespaceAndPath(namespace, path);
		}
		
		return new ObjectBlockPosDimension(posOut, dimensionOut);
	}
	
	public void save(CompoundTag compound) {
		int x = this.getPos().getX();
		int y = this.getPos().getY();
		int z = this.getPos().getZ();
		
		compound.putInt(Const.NBT_POS_X_KEY, x);
		compound.putInt(Const.NBT_POS_Y_KEY, y);
		compound.putInt(Const.NBT_POS_Z_KEY, z);
		
		CompoundTag dimensionTag = new CompoundTag();
		
		dimensionTag.putString(Const.NBT_NAMESPACE_KEY, this.dimension.getNamespace());
		dimensionTag.putString(Const.NBT_PATH_KEY, this.dimension.getPath());
		
		compound.put(Const.NBT_DIMENSION_KEY, dimensionTag);
	}
}
