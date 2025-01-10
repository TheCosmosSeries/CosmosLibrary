package com.tcn.cosmoslibrary.client.interfaces;

import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.crafting.RecipeHolder;

public class IBEUpdated {
	
	public interface Processing extends Minimal {
		public int getProcessSpeed();
		public int getProcessTime(int i);
		public int getProcessProgressScaled(int scale);
		public boolean canProcess();
		public void processItem();
		public boolean isProcessing();
		public int getRFTickRate();
	}

	public interface ProcessingRecipe extends Minimal {
		public int getProcessSpeed();
		public int getProcessTime(int i);
		public int getProcessProgressScaled(int scale);
		public boolean canProcess(@Nullable RecipeHolder<?> recipeIn, HolderLookup.Provider provider);
		public void processItem(@Nullable RecipeHolder<?> recipeIn, HolderLookup.Provider provider);
		public boolean isProcessing();
		public int getRFTickRate();
	}

	public interface Production extends Minimal {
		public int getProduceTimeMax();
		public int getProduceProgressScaled(int scale);
		public boolean canProduce();
		public void produceEnergy();
		public boolean isProducing();
		public int getRFTickRate();
		public void pushEnergy(Direction directionIn);
	}

	public interface Furnace extends Minimal {
		public int getProcessSpeed();
		public int getProcessTime(int i);
		public int getProcessProgressScaled(int scale);
		public boolean canProcess(@Nullable RecipeHolder<?> recipeIn, HolderLookup.Provider provider);
		public void processItem(@Nullable RecipeHolder<?> recipeIn, HolderLookup.Provider provider);
		public boolean isProcessing();
	}
	
	public interface Charge extends Minimal {
		public int getChargeRate();
		public void setChargeRate(int chargeRate);
		public void increaseChargeRate();
		public void decreaseChargeRate();
		public boolean canIncrease();
		public boolean canDecrease();
	}

	public interface Storage extends Minimal {
		public void pushEnergy(Direction directionIn);
	}

	public interface Energy extends Minimal {
		public boolean hasEnergy();
		public int getEnergyScaled(int scale);
	}
	
	public interface Minimal {
		public void sendUpdates();
	}
	
	public interface Fluid extends Minimal {
		public boolean isFluidEmpty();
		public net.minecraft.world.level.material.Fluid getCurrentStoredFluid();
		public int getCurrentFluidAmount();
		public int getFluidLevelScaled(int one);
		public int getFluidFillLevel();
		public void emptyFluidTank();
	}
	
	public interface FluidDual {
		public boolean isFluidEmpty(int tank);
		public net.minecraft.world.level.material.Fluid getCurrentStoredFluid(int tank);
		public int getCurrentFluidAmount(int tank);
		public int getFluidLevelScaled(int tank, int one);
		public int getFluidFillLevel(int tank);
	}
}