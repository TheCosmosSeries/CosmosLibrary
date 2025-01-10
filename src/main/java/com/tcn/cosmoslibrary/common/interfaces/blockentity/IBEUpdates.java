package com.tcn.cosmoslibrary.common.interfaces.blockentity;

public class IBEUpdates {
	
	public interface Base {
		public void sendUpdates(boolean forceUpdate);
	}
	
	public interface FluidBE extends Base {
		public boolean isFluidEmpty();
		public net.minecraft.world.level.material.Fluid getCurrentStoredFluid();
		public int getCurrentFluidAmount();
		public int getFluidLevelScaled(int one);
		public int getFluidFillLevel();
	}
}
