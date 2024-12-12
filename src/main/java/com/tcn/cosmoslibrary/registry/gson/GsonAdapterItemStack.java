package com.tcn.cosmoslibrary.registry.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class GsonAdapterItemStack implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
	
	public GsonAdapterItemStack() { }

	@Override
	public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonPrimitive object = json.getAsJsonPrimitive();
		
		String string = object.getAsString();
		
		CompoundTag nbt = new CompoundTag();
		
		try {
			nbt = TagParser.parseTag(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ItemStack stack = ItemStack.parseOptional(ServerLifecycleHooks.getCurrentServer().registryAccess(), nbt);
		
		return stack;
	}

	@Override
	public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
		CompoundTag nbt = new CompoundTag();
		src.save(ServerLifecycleHooks.getCurrentServer().registryAccess(), nbt);
		
		String nbt_string = nbt.toString();
		
		return new JsonPrimitive(nbt_string);
	}
}