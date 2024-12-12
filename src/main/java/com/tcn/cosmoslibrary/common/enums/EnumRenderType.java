package com.tcn.cosmoslibrary.common.enums;

public enum EnumRenderType {
	TRANSPARENT,
	OPAQUE;
	
	
	public boolean isTransparent() {
		return this.equals(TRANSPARENT);
	}
}
