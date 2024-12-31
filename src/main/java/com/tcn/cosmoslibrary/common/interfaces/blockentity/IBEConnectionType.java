package com.tcn.cosmoslibrary.common.interfaces.blockentity;

import com.tcn.cosmoslibrary.common.enums.EnumConnectionType;

public interface IBEConnectionType {

	public EnumConnectionType CONNECTION_TYPE = EnumConnectionType.getStandardValue();

	public EnumConnectionType getConnectionType();
	
	public void setConnectionType(EnumConnectionType type, boolean update);
	
	public void cycleConnectionType(boolean update);

}