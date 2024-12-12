package com.tcn.cosmoslibrary.client.interfaces;

import javax.annotation.Nullable;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.material.Fluid;

/**
 * Used on tiles that require updates for guis etc.
 */
public class IBlockEntityClientUpdated {
	
	public interface Processing {

		/**
		 * Returns the current process progress scaled for display.
		 * @param scale [scale inside the Gui]
		 * @return energy [scaled as required]
		 */
		public int getEnergyScaled(int scale);
		
		/**
		 * Returns whether or not the tile has energy.
		 */
		public boolean hasEnergy();
		
		/**
		 * Returns the processing speed of this tile.
		 */
		public int getProcessSpeed();
		
		/**
		 * Returns the time it takes to process.
		 * @param i [number of upgrades]
		 */
		public int getProcessTime(int i);
		
		/**
		 * Returns the current process progress scaled for display.
		 * @param scale [scale inside the Gui]
		 */
		public int getProcessProgressScaled(int scale);
		
		/**
		 * Returns whether the tile can process or not.
		 */
		public boolean canProcess();
	
		/**
		 * Actually process the Item;
		 */
		public void processItem();
		
		/**
		 * Returns whether the machine is processing or not.
		 */
		public boolean isProcessing();
		
		/**
		 * Returns how much FE the machine is currently using.
		 */
		public int getRFTickRate();
	}

	public interface ProcessingRecipe {

		/**
		 * Returns the current process progress scaled for display.
		 * @param scale [scale inside the Gui]
		 * @return energy [scaled as required]
		 */
		public int getEnergyScaled(int scale);
		
		/**
		 * Returns whether or not the tile has energy.
		 */
		public boolean hasEnergy();
		
		/**
		 * Returns the processing speed of this tile.
		 */
		public int getProcessSpeed();
		
		/**
		 * Returns the time it takes to process.
		 * @param i [number of upgrades]
		 */
		public int getProcessTime(int i);
		
		/**
		 * Returns the current process progress scaled for display.
		 * @param scale [scale inside the Gui]
		 */
		public int getProcessProgressScaled(int scale);
		
		/**
		 * Returns whether the tile can process or not.
		 */
		public boolean canProcess(@Nullable Recipe<?> recipeIn);
	
		/**
		 * Actually process the Item;
		 */
		public void processItem(@Nullable Recipe<?> recipeIn);
		
		/**
		 * Returns whether the machine is processing or not.
		 */
		public boolean isProcessing();
		
		/**
		 * Returns how much FE the machine is currently using.
		 */
		public int getRFTickRate();
	}
	
	public interface Furnace {

		/**
		 * Returns the current process progress scaled for display.
		 * @param scale [scale inside the Gui]
		 * @return energy [scaled as required]
		 */
		public int getEnergyScaled(int scale);
		
		/**
		 * Returns whether or not the tile has energy.
		 */
		public boolean hasEnergy();
		
		/**
		 * Returns the processing speed of this tile.
		 */
		public int getProcessSpeed();
		
		/**
		 * Returns the time it takes to process.
		 * @param i [number of upgrades]
		 */
		public int getProcessTime(int i);
		
		/**
		 * Returns the current process progress scaled for display.
		 * @param scale [scale inside the Gui]
		 */
		public int getProcessProgressScaled(int scale);
		
		/**
		 * Returns whether the tile can process or not.
		 */
		public boolean canProcess(@Nullable Recipe<?> recipeIn);
	
		/**
		 * Actually process the Item;
		 */
		public void processItem(@Nullable Recipe<?> recipeIn);
		
		/**
		 * Returns whether the machine is processing or not.
		 */
		public boolean isProcessing();
	}
	
	public interface Charge {

		/**
		 * Returns the current process progress scaled for display.
		 * @param scale [scale inside the Gui]
		 * @return energy [scaled as required]
		 */
		public int getEnergyScaled(int scale);
		
		/**
		 * Returns whether or not the tile has energy.
		 */
		public boolean hasEnergy();
		
		public int getChargeRate();
		
		public void setChargeRate(int chargeRate);
		
		public void increaseChargeRate();
		
		public void decreaseChargeRate();
		
		public boolean canIncrease();
		
		public boolean canDecrease();
	}
	
	public interface Storage {
	
		/**
		 * Returns the current process progress scaled for display.
		 * @param scale [scale inside the Gui]
		 * @return energy [scaled as required]
		 */
		public int getEnergyScaled(int scale);
		
		/**
		 * Returns whether or not the tile has energy.
		 */
		public boolean hasEnergy();
		
	}
	
	public interface FluidTile {
		public int getFluidLevelScaled(int one);
		
		public Fluid getCurrentStoredFluid();

		public boolean isFluidEmpty();

		public int getCurrentFluidAmount();
	}
}