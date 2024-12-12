package com.tcn.cosmoslibrary.registry.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.tcn.cosmoslibrary.common.nbt.CosmosNBTHelper.Const;
import com.tcn.cosmoslibrary.registry.gson.object.ObjectBlockPosDimension;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class GsonAdapterBlockPosDimension implements JsonSerializer<ObjectBlockPosDimension>, JsonDeserializer<ObjectBlockPosDimension> {

	public GsonAdapterBlockPosDimension() { }

	@Override
	public ObjectBlockPosDimension deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject object = json.getAsJsonObject();
		
		BlockPos pos = BlockPos.ZERO;
		ResourceLocation location = ResourceLocation.parse("");
		
		JsonObject dimension = object.getAsJsonObject(Const.NBT_DIMENSION_KEY);
	
		int x = object.get(Const.NBT_POS_X_KEY).getAsInt();
		int y = object.get(Const.NBT_POS_Y_KEY).getAsInt();
		int z = object.get(Const.NBT_POS_Z_KEY).getAsInt();
		
		pos = new BlockPos(x, y, z);
		
		if (dimension != null) {
			String namespace = dimension.get(Const.NBT_NAMESPACE_KEY).getAsString();
			String path = dimension.get(Const.NBT_PATH_KEY).getAsString();
			
			if (namespace != "" && path != "") {
				location = ResourceLocation.fromNamespaceAndPath(namespace, path);
			}
		}
		
		return new ObjectBlockPosDimension(pos, location);
	}

	@Override
	public JsonElement serialize(ObjectBlockPosDimension src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject object = new JsonObject();
		JsonObject dimension = new JsonObject();
		
		BlockPos pos = src.getPos();
		ResourceLocation location = src.getDimension();
		
		object.addProperty(Const.NBT_POS_X_KEY, pos.getX());
		object.addProperty(Const.NBT_POS_Y_KEY, pos.getY());
		object.addProperty(Const.NBT_POS_Z_KEY, pos.getZ());

		dimension.addProperty(Const.NBT_NAMESPACE_KEY, location.getNamespace());
		dimension.addProperty(Const.NBT_PATH_KEY, location.getPath());
		object.add(Const.NBT_DIMENSION_KEY, dimension);
		
		return object;
	}
}