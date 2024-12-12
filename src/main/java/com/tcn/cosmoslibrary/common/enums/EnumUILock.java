package com.tcn.cosmoslibrary.common.enums;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.network.chat.MutableComponent;

public enum EnumUILock {
	
	PRIVATE(0, "private", "cosmoslibrary.enum.ui_lock.private", false, ComponentColour.RED), 
	PUBLIC(1, "public", "cosmoslibrary.enum.ui_lock.public", true, ComponentColour.GREEN);
	
	private int index;
	private String name;
	private String localized_name;
	private boolean value;
	private ComponentColour colour;
	
	EnumUILock(int indexIn, String nameIn, String localizedNameIn, boolean valueIn, ComponentColour colourIn) {
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
	
	public static EnumUILock getOpposite(EnumUILock state) {
		if (state.equals(PRIVATE)) {
			return PUBLIC;
		} else {
			return PRIVATE;
		}
	}
	
	public static EnumUILock getStateFromIndex(int index) {
		switch(index) {
			case 0:
				return PRIVATE;
			case 1:
				return PUBLIC;
			default:
				return PRIVATE;
		}
	}
	
	public static EnumUILock getStateFromValue(boolean value) {
		if (value) {
			return PUBLIC;
		} else {
			return PRIVATE;
		}
	}
	
	public EnumUILock getNextState() {
		switch(this) {
			case PRIVATE:
				return PUBLIC;
			case PUBLIC:
				return PRIVATE;
			default:
				throw new IllegalStateException("Unable to obtain next state of [" + this + "]");
		}
	}
	
	public static EnumUILock getNextStateFromState(EnumUILock previous) {
		switch (previous) {
		case PRIVATE:
			return PUBLIC;
		case PUBLIC:
			return PRIVATE;
		default:
			throw new IllegalStateException("Unable to obtain next state of [" + previous + "]");
		}
	}
}