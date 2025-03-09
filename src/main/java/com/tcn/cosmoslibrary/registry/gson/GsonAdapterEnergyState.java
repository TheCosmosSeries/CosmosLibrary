package com.tcn.cosmoslibrary.registry.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.tcn.cosmoslibrary.common.enums.EnumEnergyState;

public class GsonAdapterEnergyState implements JsonSerializer<EnumEnergyState>, JsonDeserializer<EnumEnergyState> {

	public GsonAdapterEnergyState() { }

	@Override
	public EnumEnergyState deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		return EnumEnergyState.getStateFromIndex(json.getAsJsonObject().get("index").getAsInt());
	}

	@Override
	public JsonElement serialize(EnumEnergyState src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject object = new JsonObject();
		
		object.addProperty("index", src.getIndex());
		object.addProperty("name", src.getName());
		
		return object;
	}
}