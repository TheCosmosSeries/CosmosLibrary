package com.tcn.cosmoslibrary.runtime;

import java.util.function.Supplier;

import com.tcn.cosmoslibrary.CosmosLibrary;
import com.tcn.cosmoslibrary.common.crafting.CosmosShieldDecorationRecipe;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBusSubscriberCosmos {

	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, CosmosLibrary.MOD_ID);

	public static final Supplier<RecipeSerializer<CosmosShieldDecorationRecipe>> SHIELD_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_special_shielddecoration", () -> new SimpleCraftingRecipeSerializer<>(CosmosShieldDecorationRecipe::new));

	public static void register(IEventBus bus) {	
		RECIPE_SERIALIZERS.register(bus);
	}
}