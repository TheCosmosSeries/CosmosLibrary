package com.tcn.cosmoslibrary.common.enums;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.network.chat.MutableComponent;

public enum EnumEnergyState {

	FILL(0, "fill", "cosmoslibrary.enum.energy_state.fill", true, ComponentColour.GREEN),
	DRAIN(1, "drain", "cosmoslibrary.enum.energy_state.drain", false, ComponentColour.RED);
	
	private int index;
	private String name;
	private String localized_name;
	private boolean value;
	private ComponentColour colour;
	
	EnumEnergyState(int indexIn, String nameIn, String localizedNameIn, boolean valueIn, ComponentColour colourIn) {
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
	
	public static EnumEnergyState getOpposite(EnumEnergyState state) {
		if (state.equals(FILL)) {
			return DRAIN;
		} else {
			return FILL;
		}
	}
	
	public static EnumEnergyState getStateFromIndex(int index) {
		switch(index) {
			case 0:
				return FILL;
			case 1:
				return DRAIN;
			default:
				return FILL;
		}
	}
	
	public static EnumEnergyState getStateFromValue(boolean value) {
		if (value) {
			return FILL;
		} else {
			return DRAIN;
		}
	}
}
