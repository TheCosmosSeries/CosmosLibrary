package com.tcn.cosmoslibrary.energy.item;

import java.util.List;

import com.tcn.cosmoslibrary.common.item.CosmosItem;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper.Value;
import com.tcn.cosmoslibrary.common.util.CosmosUtil;
import com.tcn.cosmoslibrary.energy.interfaces.ICosmosEnergyItem;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class CosmosEnergyItem extends CosmosItem implements ICosmosEnergyItem {
	private int maxEnergyStored;
	private int maxExtract;
	private int maxReceive;
	private int maxUse;
	private boolean doesExtract;
	private boolean doesCharge;
	private boolean doesDisplayEnergyInTooltip;
	private ComponentColour barColour;
	
	public CosmosEnergyItem(Item.Properties properties, CosmosEnergyItem.Properties energyProperties) {
		super(properties);

		this.maxEnergyStored = energyProperties.maxEnergyStored;
		this.maxExtract = energyProperties.maxExtract;
		this.maxReceive = energyProperties.maxReceive;
		this.maxUse = energyProperties.maxUse;
		this.doesExtract = energyProperties.doesExtract;
		this.doesCharge = energyProperties.doesCharge;
		this.doesDisplayEnergyInTooltip = energyProperties.doesDisplayEnergyInTooltip;
		this.barColour = energyProperties.barColour;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, context, tooltip, flagIn);
		
		if (stack.has(DataComponents.CUSTOM_DATA)) {
			CompoundTag stackTag = stack.get(DataComponents.CUSTOM_DATA).copyTag();
			tooltip.add(ComponentHelper.style(ComponentColour.GRAY, "cosmoslibrary.tooltip.energy_item.stored").append(ComponentHelper.comp(Value.LIGHT_GRAY + "[ " + Value.RED + CosmosUtil.formatIntegerMillion(stackTag.getInt("energy")) + Value.LIGHT_GRAY + " / " + Value.RED + CosmosUtil.formatIntegerMillion(this.getMaxEnergyStored(stack)) + Value.LIGHT_GRAY + " ]")));
		}
	}

	@Override
	public int getMaxEnergyStored(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyItem) ? 0 : ((CosmosEnergyItem)item).maxEnergyStored;
	}
	
	@Override
	public int getMaxExtract(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyItem) ? 0 : ((CosmosEnergyItem)item).maxExtract;
	}
	
	@Override
	public int getMaxReceive(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyItem) ? 0 : ((CosmosEnergyItem)item).maxReceive;
	}

	@Override
	public int getMaxUse(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyItem) ? 0 : ((CosmosEnergyItem)item).maxUse;
	}

	@Override
	public boolean doesExtract(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyItem) ? false : ((CosmosEnergyItem)item).doesExtract;
	}

	@Override
	public boolean doesCharge(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyItem) ? false : ((CosmosEnergyItem)item).doesCharge;
	}

	@Override
	public boolean doesDisplayEnergyInTooltip(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyItem) ? false : ((CosmosEnergyItem)item).doesDisplayEnergyInTooltip;
	}
	
	@Override
	public boolean canReceiveEnergy(ItemStack stackIn) {
		return this.getEnergy(stackIn) < this.getMaxEnergyStored(stackIn);
	}

	@Override
	public double getScaledEnergy(ItemStack stackIn, int scaleIn) {
		Item item = stackIn.getItem();
		
		if (item instanceof ICosmosEnergyItem energyItem) {
			return (double) this.getEnergy(stackIn) * scaleIn / (double) this.getMaxEnergyStored(stackIn);
		}
		
		return 0;
	}

	@Override
	public double getScaledEnergy(ItemStack stackIn, float scaleIn) {
		Item item = stackIn.getItem();
		
		if (item instanceof ICosmosEnergyItem energyItem) {
			return (double) this.getEnergy(stackIn) * scaleIn / (double) this.getMaxEnergyStored(stackIn);
		}
		
		return 0;
	}

	@Override
	public int receiveEnergy(ItemStack stackIn, int energy, boolean simulate) {
		if (this.canReceiveEnergy(stackIn)) {
			if(this.doesCharge(stackIn)) {
				int storedReceived = Math.min(this.getMaxEnergyStored(stackIn) - this.getEnergy(stackIn), Math.min(this.getMaxReceive(stackIn), energy));
				
				if (!simulate) {
					this.setEnergy(stackIn, this.getEnergy(stackIn) + storedReceived);
				}
				
				return storedReceived;
			} 
		}
		
		return 0;
	}

	@Override
	public int extractEnergy(ItemStack stackIn, int energy, boolean simulate) {
		if (this.canExtractEnergy(stackIn)) {
			if (this.doesExtract(stackIn)) {
				int storedExtracted = Math.min(this.getEnergy(stackIn), Math.min(this.getMaxExtract(stackIn), energy));
				
				if (!simulate) {
					this.setEnergy(stackIn, this.getEnergy(stackIn) - storedExtracted);
				}
				
				return storedExtracted;
			}
		}
		
		return 0;
	}

	@Override
	public boolean isBarVisible(ItemStack stackIn) {
		return stackIn.has(DataComponents.CUSTOM_DATA) ? stackIn.get(DataComponents.CUSTOM_DATA).copyTag().contains("energy") : false;
	}
	
	@Override
	public int getBarColor(ItemStack stackIn) {
		return this.barColour.dec();
	}
	
	@Override
	public int getBarWidth(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof ICosmosEnergyItem energyItem) ? 0 : Mth.clamp(Math.round((float) energyItem.getScaledEnergy(stackIn, 13)), 0, 13);
	}
	
	public static class Properties {
		public int maxEnergyStored = 0;
		public int maxExtract = 0;
		public int maxReceive = 0;
		public int maxUse = 0;
		public boolean doesExtract = true;
		public boolean doesCharge = true;
		public boolean doesDisplayEnergyInTooltip = true;
		public ComponentColour barColour = ComponentColour.RED;
		
		public CosmosEnergyItem.Properties maxEnergyStored(int valueIn) {
			this.maxEnergyStored = valueIn;
			return this;
		}
		
		public CosmosEnergyItem.Properties maxExtract(int valueIn) {
			this.maxExtract = valueIn;
			return this;
		}
		
		public CosmosEnergyItem.Properties maxReceive(int valueIn) {
			this.maxReceive = valueIn;
			return this;
		}
		
		public CosmosEnergyItem.Properties maxUse(int value) {
			this.maxUse = value;
			return this;
		}

		public CosmosEnergyItem.Properties maxIO(int valueIn) {
			this.maxExtract = valueIn;
			this.maxReceive = valueIn;
			return this;
		}
		
		public CosmosEnergyItem.Properties doesExtract(boolean valueIn) {
			this.doesExtract = valueIn;
			return this;
		}
		
		public CosmosEnergyItem.Properties doesCharge(boolean valueIn) {
			this.doesCharge = valueIn;
			return this;
		}

		public CosmosEnergyItem.Properties doesDisplayEnergyInTooltip(boolean valueIn) {
			this.doesDisplayEnergyInTooltip = valueIn;
			return this;
		}
		
		public CosmosEnergyItem.Properties barColour(ComponentColour colourIn) {
			this.barColour = colourIn;
			return this;
		}
		
		public CosmosEnergyItem.Properties setStatsFromArray(int[] array) {
			this.maxEnergyStored = array[0];
			this.maxExtract = array[1];
			this.maxReceive = array[1];
			this.maxUse = array[2];
			return this;
		}
	}
}