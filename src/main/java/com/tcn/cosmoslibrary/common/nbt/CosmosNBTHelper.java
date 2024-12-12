package com.tcn.cosmoslibrary.common.nbt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class CosmosNBTHelper {

	public static void writeDimensionToNBT(ResourceKey<Level> dimension, CompoundTag compound) {
		CompoundTag dimension_tag = new CompoundTag();
		
		ResourceLocation location = dimension.location();
		
		dimension_tag.putString("namespace", location.getNamespace());
		dimension_tag.putString("path", location.getPath());
		
		compound.put("block_dimension", dimension_tag);
	}
	
	public static ResourceLocation readDimensionFromNBT(CompoundTag compound) {
		if (compound.contains("block_dimension")) {
			CompoundTag dimension = compound.getCompound("block_dimension");
			
			String namespace = dimension.getString("namespace");
			String path = dimension.getString("path");
			
			return ResourceLocation.fromNamespaceAndPath(namespace, path);
		}
		
		return null;
	}
	
	public static class Const {
		public static final String NBT_NAMESPACE_KEY = "namespace";
		public static final String NBT_PATH_KEY = "path";
		
		public static final String NBT_POS_KEY = "pos";
		
		public static final String NBT_POS_X_KEY = "x";
		public static final String NBT_POS_Y_KEY = "y";
		public static final String NBT_POS_Z_KEY = "z";
		
		public static final String NBT_POS_YAW_KEY = "yaw";
		public static final String NBT_POS_PITCH_KEY = "pitch";
		
		public static final String NBT_DIMENSION_KEY = "dimension";
	}
}
