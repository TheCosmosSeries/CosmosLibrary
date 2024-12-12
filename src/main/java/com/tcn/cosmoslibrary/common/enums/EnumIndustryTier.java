package com.tcn.cosmoslibrary.common.enums;

public enum EnumIndustryTier {
	NORMAL,
	SURGE,
	CREATIVE;
	
	public boolean notCreative() {
		return this != CREATIVE;
	}

	public boolean creative() {
		return this == CREATIVE;
	}
}
