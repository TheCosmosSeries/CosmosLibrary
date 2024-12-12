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
	protected final Player player;

	private final Level world;
	private final BlockPos pos;

	protected CosmosContainerMenuBlockEntity(MenuType<?> menuTypeIn, int indexIn, Inventory playerInventoryIn, ContainerLevelAccess accessIn, BlockPos posIn) {
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
	
}
