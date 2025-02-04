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
import com.tcn.cosmoslibrary.registry.gson.object.ObjectFluidTankCustom;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class GsonAdapterFluidTankCustom implements JsonSerializer<ObjectFluidTankCustom>, JsonDeserializer<ObjectFluidTankCustom> {

	private static final String NBT_FLUID_KEY = "fluid";
	private static final String NBT_FILL_LEVEL_KEY = "fill_level";
	private static final String NBT_FLUID_CAPACITY_KEY = "capacity";
	private static final String NBT_FLUID_VOLUME_KEY = "volume";
	
	public GsonAdapterFluidTankCustom() { }

	@Override
	public ObjectFluidTankCustom deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject object = json.getAsJsonObject();
		
		JsonObject fluid = object.getAsJsonObject(NBT_FLUID_KEY);
		
		int capacity = object.get(NBT_FLUID_CAPACITY_KEY).getAsInt();
		int volume = object.get(NBT_FLUID_VOLUME_KEY).getAsInt();
		int fill_level = object.get(NBT_FILL_LEVEL_KEY).getAsInt();
		
		FluidTank tank = new FluidTank(capacity);
		
		String namespace = fluid.get(Const.NBT_NAMESPACE_KEY).getAsString();
		String path = fluid.get(Const.NBT_PATH_KEY).getAsString();
		
		ResourceLocation fluidName = ResourceLocation.fromNamespaceAndPath(namespace, path);
		tank.setFluid(new FluidStack(BuiltInRegistries.FLUID.get(fluidName), volume));
		
		return new ObjectFluidTankCustom(tank, fill_level);
	}

	@Override
	public JsonElement serialize(ObjectFluidTankCustom src, Type typeOfSrc, JsonSerializationContext context) {
		ResourceLocation fluid_name = ResourceLocation.parse(src.getFluidTank().getFluid().getFluid().getFluidType().getDescriptionId());
		
		JsonObject object = new JsonObject();
		JsonObject fluid = new JsonObject();
		
		fluid.addProperty(Const.NBT_NAMESPACE_KEY, fluid_name.getNamespace());
		fluid.addProperty(Const.NBT_PATH_KEY, fluid_name.getPath());
		
		object.addProperty(NBT_FILL_LEVEL_KEY, src.getFillLevel());
		object.addProperty(NBT_FLUID_CAPACITY_KEY, src.getFluidTank().getCapacity());
		object.addProperty(NBT_FLUID_VOLUME_KEY, src.getFluidTank().getFluidAmount());
		object.add(NBT_FLUID_KEY, fluid);
		
		return object;
	}
}