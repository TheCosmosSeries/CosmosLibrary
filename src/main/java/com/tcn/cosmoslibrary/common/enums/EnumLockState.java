package com.tcn.cosmoslibrary.common.enums;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.network.chat.MutableComponent;

public enum EnumLockState {

	UNLOCKED(0, "unlocked", "cosmoslibrary.enum.lock_state.unlocked", false, ComponentColour.GREEN),
	LOCKED(1, "locked", "cosmoslibrary.enum.lock_state.locked", true, ComponentColour.RED),
	UNKNOWN(2, "unknown", "cosmoslibrary.enum.lock_state.unknown", false, ComponentColour.GRAY);
	
	private int index;
	private String name;
	private String localized_name;
	private boolean value;
	private ComponentColour colour;
	
	EnumLockState(int indexIn, String nameIn, String localizedNameIn, boolean valueIn, ComponentColour colourIn) {
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
	
	public static EnumLockState getOpposite(EnumLockState state) {
		if (state.equals(LOCKED)) {
			return UNLOCKED;
		} else {
			return LOCKED;
		}
	}
	
	public static EnumLockState getStateFromIndex(int index) {
		switch(index) {
			case 0:
				return UNLOCKED;
			case 1:
				return LOCKED;
			default:
				return UNKNOWN;
		}
	}
	
	public static EnumLockState getStateFromValue(boolean value) {
		if (value) {
			return LOCKED;
		} else {
			return UNLOCKED;
		}
	}
}
