package com.tcn.cosmoslibrary.common.interfaces.blockentity;

import com.tcn.cosmoslibrary.common.enums.EnumUILock;

import net.minecraft.world.entity.player.Player;

public interface IBEUILockable {
	public EnumUILock getUILock();
	public void setUILock(EnumUILock modeIn);
	public void cycleUILock();
	public void setOwner(Player playerIn);
	public boolean checkIfOwner(Player playerIn);
	public boolean canPlayerAccess(Player playerIn);
}