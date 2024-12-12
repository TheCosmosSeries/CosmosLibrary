package com.tcn.cosmoslibrary.common.enums;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.network.chat.MutableComponent;

public enum EnumGeneratedState {
	
	UNGENERATED(0, "ungenerated", "cosmoslibrary.enum.generated.false", false, ComponentColour.RED), 
	GENERATED(1, "generated", "cosmoslibrary.enum.generated.true", true, ComponentColour.GREEN);
	
	private int index;
	private String name;
	private String localized_name;
	private boolean value;
	private ComponentColour colour;
	
	EnumGeneratedState(int indexIn, String nameIn, String localizedNameIn, boolean valueIn, ComponentColour colourIn) {
		this.index = indexIn;
		this.name = nameIn;
		this.localized_name = localizedNameIn;
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
		return this.localized_name;
	}
	
	public MutableComponent getColouredComp() {
		return ComponentHelper.style(this.colour, "bold", this.localized_name);
	}

	public boolean getValue() {
		return this.value;
	}

	public ComponentColour getColour() {
		return this.colour;
	}
	
	public static EnumGeneratedState getOpposite(EnumGeneratedState state) {
		if (state.equals(UNGENERATED)) {
			return GENERATED;
		} else {
			return UNGENERATED;
		}
	}
	
	public static EnumGeneratedState getStateFromIndex(int index) {
		switch(index) {
			case 0:
				return UNGENERATED;
			case 1:
				return GENERATED;
			default:
				return UNGENERATED;
		}
	}
	
	public static EnumGeneratedState getStateFromValue(boolean value) {
		if (value) {
			return GENERATED;
		} else {
			return UNGENERATED;
		}
	}
}