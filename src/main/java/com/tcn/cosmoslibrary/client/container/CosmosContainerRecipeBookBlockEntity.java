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
	protected final Player player;

	private final Level world;
	private final BlockPos pos;

	protected CosmosContainerRecipeBookBlockEntity(MenuType<?> menuTypeIn, int indexIn, Inventory playerInventoryIn, @Nullable ContainerLevelAccess accessIn, BlockPos posIn) {
		super(menuTypeIn, indexIn);
		
		this.pos = posIn;
		this.world = playerInventoryIn.player.level();
		
		this.access = accessIn;
		this.player = playerInventoryIn.player;
	}

	@Override
	public boolean stillValid(Player playerIn) {
		return false;
	}

	public Level getLevel() {
		return world;
	}

	public BlockPos getBlockPos() {
		return pos;
	}

	public Player getPlayer() {
		return this.player;
	}
	
	@Override
	public void fillCraftSlotsStackedContents(StackedContents p_40117_) { }

	@Override
	public void clearCraftingContent() { }

	@Override
	public boolean recipeMatches(RecipeHolder<R> p_40118_) {
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
	public boolean shouldMoveToInventory(int p_150635_) {
		return false;
	}

}
