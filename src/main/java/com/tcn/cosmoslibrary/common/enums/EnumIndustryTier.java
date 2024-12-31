package com.tcn.cosmoslibrary.common.enums;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;

public enum EnumIndustryTier {
	NORMAL (0, ComponentColour.WHITE, false),
	SURGE (1, ComponentColour.ORANGE, false),
	CREATIVE (2, ComponentColour.MAGENTA, true);
	
	private int index;
	private ComponentColour colour;
	private boolean creative;
	
	EnumIndustryTier(int indexIn, ComponentColour colourIn, boolean creativeIn) {
		this.index = indexIn;
		this.colour = colourIn;
		this.creative = creativeIn;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public ComponentColour getColour() {
		return this.colour;
	}
	
	public boolean normal() {
		return this == NORMAL;
	}
	
	public boolean surge() {
		return this == SURGE;
	}
	
	public boolean creative() {
		return this.creative;
	}

	public boolean notCreative() {
		return !this.creative;
	}
}