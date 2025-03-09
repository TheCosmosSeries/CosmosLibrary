package com.tcn.cosmoslibrary.registry.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class GsonAdapterNonNullList implements JsonSerializer<NonNullList<ItemStack>>, JsonDeserializer<NonNullList<ItemStack>> {

	@Override
	public NonNullList<ItemStack> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		CompoundTag compoundOut = new CompoundTag();
		
		try {
			compoundOut = TagParser.parseTag(json.getAsJsonPrimitive().getAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		NonNullList<ItemStack> list = NonNullList.<ItemStack>withSize(compoundOut.getInt("size"), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(compoundOut, list, ServerLifecycleHooks.getCurrentServer().registryAccess());
		
		return list;
	}

	@Override
	public JsonElement serialize(NonNullList<ItemStack> src, Type typeOfSrc, JsonSerializationContext context) {
		CompoundTag compound = new CompoundTag();
		ContainerHelper.saveAllItems(compound, src, ServerLifecycleHooks.getCurrentServer().registryAccess());
		compound.putInt("size", src.size());
		
		return new JsonPrimitive(compound.toString());
	}

}
