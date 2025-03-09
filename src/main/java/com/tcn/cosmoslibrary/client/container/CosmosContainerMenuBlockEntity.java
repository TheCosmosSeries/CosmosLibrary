package com.tcn.cosmoslibrary.client.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;

public abstract class CosmosContainerMenuBlockEntity extends AbstractContainerMenu {

	protected final ContainerLevelAccess access;
	
	private final BlockPos pos;
	private final Level level;
	protected final Player player;

	protected CosmosContainerMenuBlockEntity(MenuType<?> menuTypeIn, int indexIn, Inventory playerInventoryIn, ContainerLevelAccess accessIn, BlockPos posIn) {
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
}