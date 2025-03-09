package com.tcn.cosmoslibrary.client.container;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;

public abstract class CosmosContainerRecipeBookBlockEntity<J extends RecipeInput, R extends Recipe<J>> extends RecipeBookMenu<J, R> {

	protected final ContainerLevelAccess access;

	private final BlockPos pos;
	private final Level level;
	protected final Player player;

	protected CosmosContainerRecipeBookBlockEntity(MenuType<?> menuTypeIn, int indexIn, Inventory playerInventoryIn, @Nullable ContainerLevelAccess accessIn, BlockPos posIn) {
		super(menuTypeIn, indexIn);

		this.access = accessIn;
		this.pos = posIn;
		this.level = playerInventoryIn.player.level();
		this.player = playerInventoryIn.player;
	}

	public BlockPos getBlockPos() {
		return this.pos;
	}

	public Level getLevel() {
		return this.level;
	}

	public Player getPlayer() {
		return this.player;
	}
	
	@Override
	public void fillCraftSlotsStackedContents(StackedContents contents) { }

	@Override
	public void clearCraftingContent() { }

	@Override
	public boolean recipeMatches(RecipeHolder<R> recipe) {
		return false;
	}

	@Override
	public int getResultSlotIndex() {
		return 0;
	}

	@Override
	public int getGridWidth() {
		return 0;
	}

	@Override
	public int getGridHeight() {
		return 0;
	}

	@Override
	public int getSize() {
		return 0;
	}

	@Override
	public RecipeBookType getRecipeBookType() {
		return null;
	}

	@Override
	public boolean shouldMoveToInventory(int index) {
		return false;
	}
}