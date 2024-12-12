package com.tcn.cosmoslibrary.common.enums;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.network.chat.MutableComponent;

public enum EnumGeneralEnableState {

	ENABLED(0, "enabled", "cosmoslibrary.enum.enable_state.enabled", true, ComponentColour.GREEN),
	DISABLED(1, "disabled", "cosmoslibrary.enum.enable_state.disabled", false, ComponentColour.RED);
	
	private int index;
	private String name;
	private String localized_name;
	private boolean value;
	private ComponentColour colour;
	
	EnumGeneralEnableState(int indexIn, String nameIn, String localizedNameIn, boolean valueIn, ComponentColour colourIn) {
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
	
	public static EnumGeneralEnableState getOpposite(EnumGeneralEnableState state) {
		if (state.equals(DISABLED)) {
			return ENABLED;
		} else {
			return DISABLED;
		}
	}
	
	public static EnumGeneralEnableState getStateFromIndex(int index) {
		switch(index) {
			case 0:
				return ENABLED;
			case 1:
				return DISABLED;
			default:
				return ENABLED;
		}
	}
	
	public static EnumGeneralEnableState getStateFromValue(boolean value) {
		if (value) {
			return ENABLED;
		} else {
			return DISABLED;
		}
	}
}
