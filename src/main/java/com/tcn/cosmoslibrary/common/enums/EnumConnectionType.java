package com.tcn.cosmoslibrary.common.enums;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.network.chat.MutableComponent;

public enum EnumConnectionType {
	
	SCREEN(0, "screen", "cosmoslibrary.enum.connection_type.screen", ComponentColour.CYAN),
	ENERGY(1, "energy", "cosmoslibrary.enum.connection_type.energy", ComponentColour.RED),
	FLUID(2, "fluid", "cosmoslibrary.enum.connection_type.fluid", ComponentColour.TURQUOISE),
	ITEM(3, "item", "cosmoslibrary.enum.connection_type.item", ComponentColour.YELLOW);

	private final int index;
	private final String name;
	private final String localized_name;
	private final ComponentColour colour;
	
	/** All states in [SCREEN-ENERGY-FLUID-ITEM] order. */
	public static final EnumConnectionType[] VALUES = new EnumConnectionType[4];
	
	private EnumConnectionType(int indexIn, String nameIn, String localizedNameIn, ComponentColour colourIn) {
		this.index = indexIn;
		this.name = nameIn;
		this.localized_name = localizedNameIn;
		this.colour = colourIn;
	}
	
	/**
	 * Get the index of this EnumConnectionType. Order is [NO_CONN-SCREEN-ENERGY-FLUID-ITEM].
	 */
	public int getIndex() {
		return this.index;
	}
	
	@Override
	public String toString() {
		return this.name();
	}
	
	/**
	 * Get the localised name for display.
	 */
	public String getName() {
		return name;
	}
	
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
	 * Get the gui_colour.
	 */
	public ComponentColour getColour() {
		return this.colour;
	}
	
	/**
	 * Returns the default value. (Index: 0)
	 */
	public static EnumConnectionType getStandardValue() {
		return SCREEN;
	}
	
	/**
	 * Returns the next state of the current state.
	 */
	public EnumConnectionType getNextState() {
		switch(this) {
			case SCREEN:
				return ENERGY;
			case ENERGY:
				return FLUID;
			case FLUID:
				return ITEM;
			case ITEM:
				return SCREEN;
			default:
				throw new IllegalStateException("Unable to obtain next state of [" + this + "]");
		}
	}
	
	/**
	 * Returns the next state from a given state.
	 * @param previous [state from]
	 */
	public static EnumConnectionType getNextStateFromState(EnumConnectionType previous) {
		switch (previous) {
			case SCREEN:
				return ENERGY;
			case ENERGY:
				return FLUID;
			case FLUID:
				return ITEM;
			case ITEM:
				return SCREEN;
			default:
				throw new IllegalStateException("Unable to obtain next state of [" + previous + "]");
		}
	}
	
	/**
	 * Returns the state from the given index.
	 * @param index
	 */
	public static EnumConnectionType getStateFromIndex(int index) {
		switch (index) {
			case 0:
				return SCREEN;
			case 1:
				return ENERGY;
			case 2:
				return FLUID;
			case 3: 
				return ITEM;
			default:
				throw new IllegalStateException("No EnumConnectionType exists with index: [" + index + "]");
		}
	}
	
	/**
	 * Returns the state from the given simple_name.
	 * @param name
	 */
	public static EnumConnectionType getStateFromName(String name) {
		switch (name) {
			case "screen":
				return SCREEN;
			case "energy":
				return ENERGY;
			case "fluid":
				return FLUID;
			case "item": 
				return ITEM;
			default:
				throw new IllegalStateException("No EnumConnectionType exists with name: [" + name + "]");
		}
	}
	
}