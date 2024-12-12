package com.tcn.cosmoslibrary.common.enums;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.network.chat.MutableComponent;

public enum EnumGenerationMode {
	BURNABLE_ITEM(0, "burnable_item", "cosmoslibrary.enum.generation_mode.item", false, ComponentColour.LIGHT_GRAY),
	BURNABLE_FLUID(1, "high_temperature_fluid", "cosmoslibrary.enum.generation_mode.fluid", true, ComponentColour.ORANGE);
	
	private int index;
	private String name;
	private String localizedName;
	private boolean value;
	private ComponentColour colour;
	
	EnumGenerationMode(int indexIn, String nameIn, String localizedNameIn, boolean valueIn, ComponentColour colourIn) {	
		this.index = indexIn;
		this.name = nameIn;
		this.localizedName = localizedNameIn;
		this.value = valueIn;
		this.colour = colourIn;
	}
	
	public int getIndex() {
		return this.index;
	}

	public String getName() {
		return this.name;
	}
	
	public String getUnlocalizedName() {
		return this.localizedName;
	}
	
	public MutableComponent getColouredComp() {
		return ComponentHelper.style(this.colour, "bold", this.localizedName);
	}

	public boolean getValue() {
		return this.value;
	}

	public ComponentColour getColour() {
		return this.colour;
	}

	public static EnumGenerationMode getOpposite(EnumGenerationMode state) {
		if (state.equals(BURNABLE_ITEM)) {
			return BURNABLE_FLUID;
		} else {
			return BURNABLE_ITEM;
		}
	}
	
	public static EnumGenerationMode getStateFromIndex(int index) {
		switch(index) {
			case 0:
				return BURNABLE_ITEM;
			case 1:
				return BURNABLE_FLUID;
			default:
				return BURNABLE_ITEM;
		}
	}
	
	public static EnumGenerationMode getStateFromValue(boolean value) {
		if (value) {
			return BURNABLE_FLUID;
		} else {
			return BURNABLE_ITEM;
		}
	}
}
