package com.tcn.cosmoslibrary.common.enums;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.network.chat.MutableComponent;

public enum EnumTrapState {
	
	FREE(0, "free", "cosmoslibrary.enum.trap_players.free", false, ComponentColour.GREEN), 
	TRAPPED(1, "trapped", "cosmoslibrary.enum.trap_players.trapped", true, ComponentColour.RED);
	
	private int index;
	private String name;
	private String localized_name;
	private boolean value;
	private ComponentColour colour;
	
	EnumTrapState(int indexIn, String nameIn, String localizedNameIn, boolean valueIn, ComponentColour colourIn) {
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
	
	public static EnumTrapState getOpposite(EnumTrapState state) {
		if (state.equals(FREE)) {
			return TRAPPED;
		} else {
			return FREE;
		}
	}
	
	public static EnumTrapState getStateFromIndex(int index) {
		switch(index) {
			case 0:
				return FREE;
			case 1:
				return TRAPPED;
			default:
				return FREE;
		}
	}
	
	public static EnumTrapState getStateFromValue(boolean value) {
		if (!value) {
			return FREE;
		} else {
			return TRAPPED;
		}
	}
}