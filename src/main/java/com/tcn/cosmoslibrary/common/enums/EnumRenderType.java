package com.tcn.cosmoslibrary.common.enums;

public enum EnumRenderType {
	TRANSPARENT,
	OPAQUE;
	
	public boolean transparent() {
		return this.equals(TRANSPARENT);
	}
}
