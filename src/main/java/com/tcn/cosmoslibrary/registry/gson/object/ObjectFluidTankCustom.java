package com.tcn.cosmoslibrary.registry.gson.object;

import com.tcn.cosmoslibrary.common.nbt.CosmosNBTHelper.Const;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class ObjectFluidTankCustom {
	
	public static final String NBT_FLUID_KEY = "fluid_tank_object";
	public static final String NBT_FILL_LEVEL_KEY = "fill_level";
	public static final String NBT_FLUID_CAPACITY_KEY = "capacity";
	public static final String NBT_FLUID_VOLUME_KEY = "volume";
	
	private FluidTank fluid_tank;
	
	private int fill_level;
	
	@SuppressWarnings("unused")
	private ObjectFluidTankCustom() { }
		
	public ObjectFluidTankCustom(FluidTank fluid_tank, int fill_level) {
		this.fluid_tank = fluid_tank;
		this.fill_level = fill_level;
	}
	
	public FluidTank getFluidTank() {
		return this.fluid_tank;
	}
	
	public void setFluidTank(FluidTank player_name) {
		this.fluid_tank = player_name;
	}
	
	public int getFillLevel() {
		return this.fill_level;
	}
	
	public void setFillLevel(int fill_level) {
		this.fill_level = fill_level;
	}
	
	public static ObjectFluidTankCustom readFromNBT(CompoundTag compound) {
		int capacity = compound.getInt(NBT_FLUID_CAPACITY_KEY);
		int volume = compound.getInt(NBT_FLUID_VOLUME_KEY);
		int fill_level = compound.getInt(NBT_FILL_LEVEL_KEY);
		
		FluidTank tank = new FluidTank(capacity).setCapacity(capacity);
		
		CompoundTag fluid = compound.getCompound(NBT_FLUID_KEY);
		String namespace = fluid.getString(Const.NBT_NAMESPACE_KEY);
		String path = fluid.getString(Const.NBT_PATH_KEY);
		
		ResourceLocation fluidName = ResourceLocation.fromNamespaceAndPath(namespace, path);
		
		tank.setFluid(new FluidStack(BuiltInRegistries.FLUID.get(fluidName), volume));
		
		return new ObjectFluidTankCustom(tank, fill_level);
	}
	
	public void writeToNBT(CompoundTag compound) {
		ResourceLocation fluid_name = BuiltInRegistries.FLUID.getKey(this.fluid_tank.getFluid().getFluid());
				
		CompoundTag fluid = new CompoundTag();
		
		fluid.putString(Const.NBT_NAMESPACE_KEY, fluid_name.getNamespace());
		fluid.putString(Const.NBT_PATH_KEY, fluid_name.getPath());
		
		compound.putInt(NBT_FILL_LEVEL_KEY, this.getFillLevel());
		compound.putInt(NBT_FLUID_CAPACITY_KEY, this.getFluidTank().getCapacity());
		compound.putInt(NBT_FLUID_VOLUME_KEY, this.getFluidTank().getFluidAmount());
		compound.put(NBT_FLUID_KEY, fluid);
	}
}
