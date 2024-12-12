package com.tcn.cosmoslibrary.common.enums;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.network.chat.MutableComponent;

public enum EnumGeneralAllowState {

	ALLOWED(0, "allowed", "cosmoslibrary.enum.allow_state.allowed", true, ComponentColour.GREEN),
	BLOCKED(1, "blocked", "cosmoslibrary.enum.allow_state.blocked", false, ComponentColour.RED);
	
	private int index;
	private String name;
	private String localized_name;
	private boolean value;
	private ComponentColour colour;
	
	EnumGeneralAllowState(int indexIn, String nameIn, String localizedNameIn, boolean valueIn, ComponentColour colourIn) {
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
	
	public static EnumGeneralAllowState getOpposite(EnumGeneralAllowState state) {
		if (state.equals(BLOCKED)) {
			return ALLOWED;
		} else {
			return BLOCKED;
		}
	}
	
	public static EnumGeneralAllowState getStateFromIndex(int index) {
		switch(index) {
			case 0:
				return ALLOWED;
			case 1:
				return BLOCKED;
			default:
				return ALLOWED;
		}
	}
	
	public static EnumGeneralAllowState getStateFromValue(boolean value) {
		if (value) {
			return ALLOWED;
		} else {
			return BLOCKED;
		}
	}
}
