package com.tcn.cosmoslibrary.client.container;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class CosmosContainerMenuItemStack extends AbstractContainerMenu {

	private ItemStack stack;
	private final Level level;
	private final Player player;
	
	protected CosmosContainerMenuItemStack(MenuType<?> menuType, int containerId, Inventory inventoryIn, ItemStack stackIn) {
		super(menuType, containerId);
		
		this.stack = stackIn;
		this.level = inventoryIn.player.level();
		this.player = inventoryIn.player;
	}

	public ItemStack getStack() {
		return this.stack;
	}

	public Level getLevel() {
		return this.level;
	}
	
	public Player getPlayer() {
		return this.player;
	}
}