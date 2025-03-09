package com.tcn.cosmoslibrary.integration.jei;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

public class CosmosJEIHelper {
	private final RecipeManager recipeManager;

	private static final CosmosJEIHelper INSTANCE = new CosmosJEIHelper();
	
	public CosmosJEIHelper() {
		Minecraft minecraft = Minecraft.getInstance();
		this.recipeManager = minecraft.level.getRecipeManager();
	}
	
	public static CosmosJEIHelper getInstance() {
		return INSTANCE;
	}
	
	public <C extends RecipeInput, T extends Recipe<C>> List<T> getRecipes(IRecipeCategory<T> stationCategory, RecipeType<T> recipeType) {
		List<T> list = new ArrayList<T>();
		
		getRecipes(recipeManager, recipeType).forEach((holder) -> {
			list.add(holder.value());
		});
		
		return list;
	}
	
	private static <C extends RecipeInput, T extends Recipe<C>> Collection<RecipeHolder<T>> getRecipes(RecipeManager recipeManager, RecipeType<T> recipeType) {
		return recipeManager.getAllRecipesFor(recipeType);
	}
}
