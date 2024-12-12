package com.tcn.cosmoslibrary.common.interfaces.blockentity;

import com.tcn.cosmoslibrary.common.enums.EnumUIHelp;
import com.tcn.cosmoslibrary.common.enums.EnumUILock;
import com.tcn.cosmoslibrary.common.enums.EnumUIMode;

import net.minecraft.world.entity.player.Player;

public interface IBlockEntityUIMode {
	
	public EnumUIMode getUIMode();
	
	public void setUIMode(EnumUIMode modeIn);
	
	public void cycleUIMode();
	
	public EnumUIHelp getUIHelp();
	
	public void setUIHelp(EnumUIHelp modeIn);
	
	public void cycleUIHelp();
	
	public EnumUILock getUILock();
	
	public void setUILock(EnumUILock modeIn);
	
	public void cycleUILock();
	
	public void setOwner(Player playerIn);
	public boolean checkIfOwner(Player playerIn);
	public boolean canPlayerAccess(Player playerIn);
	
}
