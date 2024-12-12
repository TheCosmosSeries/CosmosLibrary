package com.tcn.cosmoslibrary.common.enums;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.network.chat.MutableComponent;

public enum EnumChannelSideState {
	
	AIR(0, "air", "cosmoslibrary.channel_state.air.name", ComponentColour.RED),
	CABLE_NO_CONN(1, "no_conn", "cosmoslibrary.channel_state.no_conn.name", ComponentColour.LIGHT_GRAY),
	CABLE(2, "cable", "cosmoslibrary.channel_state.cable.name", ComponentColour.LIGHT_GRAY),
	CABLE_OTHER(3, "cable", "cosmoslibrary.channel_state.cable_other.name", ComponentColour.LIGHT_GRAY),
	INTERFACE_NO_CONN(4, "interface_no_conn", "cosmoslibrary.channel_state.interface_no_conn.name", ComponentColour.WHITE),
	INTERFACE_INPUT(5, "interface_input", "cosmoslibrary.channel_state.interface_input.name", ComponentColour.BLUE),
	INTERFACE_OUTPUT(6, "interface_output", "cosmoslibrary.channel_state.interface_output.name", ComponentColour.GREEN),
	DISABLED(7, "disabled", "cosmoslibrary.channel_state.disabled.name", ComponentColour.GRAY);
	
	public final int index;
	public final String basic_name;
	public final String localizedName;
	public final ComponentColour colour;
	
	private EnumChannelSideState (int indexIn, String basic_nameIn, String localizedNameIn, ComponentColour colourIn) {
		index = indexIn;
		basic_name = basic_nameIn;
		localizedName = localizedNameIn;
		colour = colourIn;
	}
	
	/**
	 * Get the basic_name of this ChannelState.
	 */
	public String getName() {
		return this.basic_name;
	}

	/**
	 * Get the localized name for display.
	 */
	public MutableComponent getColouredComp() {
		return ComponentHelper.style(this.colour, "bold", this.localizedName);
	}


	/**
	 * Get the display_name.
	 */
	public String getlocalizedName() {
		return this.localizedName;
	}
	
	@Override
	public String toString() {
        return this.basic_name;
    }
	
	/**
	 * Get the index of this EnumSideState. Order is [NO_CONN-CABLE-NO_CONN-INTERFACE_INPUT-INTERFACE_OUTPUT-DISABLED].
	 */
	public int getIndex() {
		return this.index;
	}
	
	/**
	 * Returns the standard array [NO_CONN[5]].
	 */
	public static EnumChannelSideState[] getStandardArray() {
		return new EnumChannelSideState[] { AIR, AIR, AIR, AIR, AIR, AIR };
	}
	
	public EnumChannelSideState getNextState() {
		switch(this) {
			case AIR:
				return CABLE_NO_CONN;
			case CABLE_NO_CONN:
				return CABLE;
			case CABLE:
				return CABLE_OTHER;
			case CABLE_OTHER:
				return INTERFACE_NO_CONN;
			case INTERFACE_NO_CONN:
				return INTERFACE_INPUT;
			case INTERFACE_INPUT:
				return INTERFACE_OUTPUT;
			case INTERFACE_OUTPUT:
				return DISABLED;
			case DISABLED:
				return AIR;
			default:
				throw new IllegalStateException("Unable to obtain next state of [" + this + "]");
		}
	}

	public EnumChannelSideState getNextStateUser() {
		switch(this) {
			case AIR:
				return INTERFACE_INPUT;
			case INTERFACE_NO_CONN:
				return INTERFACE_INPUT;
			case INTERFACE_INPUT:
				return INTERFACE_OUTPUT;
			case INTERFACE_OUTPUT:
				return DISABLED;
			case DISABLED:
				return AIR;
			default:
				return AIR;
		}
	}
	
	/**
	 * Returns the state based on the index.
	 * @param index_in [the index of the state required]
	 */
	public static EnumChannelSideState getStateFromIndex(int index_in) {
		switch (index_in) {
			case 0 :
				return AIR;
			case 1:
				return CABLE_NO_CONN;
			case 2:
				return CABLE;
			case 3:
				return CABLE_OTHER;
			case 4: 
				return INTERFACE_NO_CONN;
			case 5:
				return INTERFACE_INPUT;
			case 6:
				return INTERFACE_OUTPUT;
			case 7:
				return DISABLED;
			default:
				throw new IllegalStateException("No Enum exists with that index: " + "[ " + index_in + " ]");
		}
	}
	
	public boolean isInterface() {
		return this.equals(INTERFACE_NO_CONN) || this.equals(INTERFACE_INPUT) || this.equals(INTERFACE_OUTPUT);
	}
}