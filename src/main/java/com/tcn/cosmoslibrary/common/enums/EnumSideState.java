package com.tcn.cosmoslibrary.common.enums;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.network.chat.MutableComponent;

public enum EnumSideState {

	INTERFACE_NORMAL(0, "interface_normal", "cosmoslibrary.enum.side_state.interface_normal", ComponentColour.LIGHT_GRAY),
	INTERFACE_OUTPUT(1, "interface_output", "cosmoslibrary.enum.side_state.interface_output", ComponentColour.GREEN),
	INTERFACE_INPUT(2, "interface_input", "cosmoslibrary.enum.side_state.interface_input", ComponentColour.LIGHT_BLUE),
	DISABLED(3, "disabled", "cosmoslibrary.enum.side_state.disabled", ComponentColour.GRAY);

	private final int index;
	private final String name;
	private final String localized_name;
	private final ComponentColour colour;
	
	/** All states in [ INTERFACE_NORMAL, INTERFACE_OUTPUT, INTERFACE_INPUT, DISABLED ] order. */
	public static EnumSideState[] VALUES = new EnumSideState[4];
	
	/** You must CLONE (.clone()) this array or Bugs WILL happen */
	private static EnumSideState[] INTERNAL = new EnumSideState[] { INTERFACE_NORMAL, INTERFACE_NORMAL, INTERFACE_NORMAL, INTERFACE_NORMAL, INTERFACE_NORMAL, INTERFACE_NORMAL };
	
	public static EnumSideState[] STANDARD = INTERNAL.clone();
	
	private EnumSideState(int indexIn, String nameIn, String localizedNameIn, ComponentColour colourIn) {
		this.index = indexIn;
		this.name = nameIn;
		this.localized_name = localizedNameIn;
		this.colour = colourIn;
	}
	
	/**
	 * Get the index of this EnumSideState. Order is [NO_CONN-INTERFACE_NORMAL-INTERFACE_OUTPUT-INTERFACE_INPUT-DISABLED].
	 */
	public int getIndex() {
		return this.index;
	}
	
	/**
	 * Get the name of this EnumSideState.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Get the localized name for display.
	 */
	public MutableComponent getColouredComp() {
		return ComponentHelper.style(this.colour, "bold", this.localized_name);
	}
	
	/**
	 * Get the localized_name without localization.
	 */
	public String getUnlocalizedName() {
		return this.localized_name;
	}
	
	/**
	 * Get the colour for displayed text.
	 */
	public ComponentColour getTextColour() {
		return this.colour;
	}
	
	/**
	 * Returns the standard array [NO_CONN[5]].
	 */
	public static EnumSideState[] getStandardArray() {
		return new EnumSideState[] { INTERFACE_NORMAL, INTERFACE_NORMAL, INTERFACE_NORMAL, INTERFACE_NORMAL, INTERFACE_NORMAL, INTERFACE_NORMAL };
	}
	
	/**
	 * Returns the next state.
	 */
	public EnumSideState getNextState() {
		switch(this) {
			case INTERFACE_NORMAL:
				return INTERFACE_OUTPUT;
			case INTERFACE_OUTPUT:
				return INTERFACE_INPUT;
			case INTERFACE_INPUT:
				return DISABLED;
			case DISABLED:
				return INTERFACE_NORMAL;
			default:
				throw new IllegalStateException("Unable to obtain next state of [" + this + "]");
		}
	}
	
	/**
	 * Returns the next state from a given state.
	 * @param previous [state from]
	 */
	public static EnumSideState getNextStateFromState(EnumSideState previous) {
		switch (previous) {
			case INTERFACE_NORMAL:
				return INTERFACE_OUTPUT;
			case INTERFACE_OUTPUT:
				return INTERFACE_INPUT;
			case INTERFACE_INPUT:
				return DISABLED;
			case DISABLED:
				return INTERFACE_NORMAL;
			default:
				throw new IllegalStateException("Unable to obtain next state of [" + previous + "]");
		}
	}
	
	/**
	 * Returns the state from the given index.
	 * @param index
	 */
	public static EnumSideState getStateFromIndex(int index) {
		switch (index) {
			case 0:
				return INTERFACE_NORMAL;
			case 1:
				return INTERFACE_OUTPUT;
			case 2:
				return INTERFACE_INPUT;
			case 3: 
				return DISABLED;
			default:
				throw new IllegalStateException("No EnumSideState exists with index: [" + index + "]");
		}
	}

}