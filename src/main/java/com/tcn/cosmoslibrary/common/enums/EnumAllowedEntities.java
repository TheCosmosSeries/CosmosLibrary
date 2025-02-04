package com.tcn.cosmoslibrary.common.enums;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.network.chat.MutableComponent;

public enum EnumAllowedEntities {
	
	NONE(0, "none", "cosmoslibrary.enum.allowed_entities.none", ComponentColour.RED),
	NON_PLAYERS_ONLY(1, "entities_only", "cosmoslibrary.enum.allowed_entities.entities_only", ComponentColour.YELLOW),
	PLAYERS_ONLY(2, "players_only", "cosmoslibrary.enum.allowed_entities.players_only", ComponentColour.CYAN),
	ITEMS_ONLY(3, "items_only", "cosmoslibrary.enum.allowed_entities.items_only", ComponentColour.ORANGE),
	ALL(4, "all", "cosmoslibrary.enum.allowed_entities.all", ComponentColour.GREEN);
	
	private final int index;
	private final String name;
	private final String localizedName;
	private final ComponentColour displayColour;
	
	private EnumAllowedEntities(int index, String name, String localizedName, ComponentColour displayColour) {
		this.index = index;
		this.name = name;
		this.localizedName = localizedName;
		this.displayColour = displayColour;
	}

	public int getIndex() {
		return this.index;
	}
	
	public String getName() {
		return this.name;
	}
	
	public MutableComponent getColouredComp() {
		return ComponentHelper.style(this.displayColour, "bold", this.localizedName);
	}
	
	public String getUnlocalizedName() {
		return this.localizedName;
	}
	
	public ComponentColour getTextColour() {
		return this.displayColour;
	}

	public EnumAllowedEntities getNextState() {
		switch(this) {
			case NONE:
				return NON_PLAYERS_ONLY;
			case NON_PLAYERS_ONLY:
				return PLAYERS_ONLY;
			case PLAYERS_ONLY:
				return ITEMS_ONLY;
			case ITEMS_ONLY:
				return ALL;
			case ALL:
				return NONE;
			default:
				throw new IllegalStateException("Unable to obtain state of [" + this + "]");
		}
	}

	public static EnumAllowedEntities getNextState(EnumAllowedEntities previous) {
		switch(previous) {
			case NONE:
				return NON_PLAYERS_ONLY;
			case NON_PLAYERS_ONLY:
				return PLAYERS_ONLY;
			case PLAYERS_ONLY:
				return ITEMS_ONLY;
			case ITEMS_ONLY:
				return ALL;
			case ALL:
				return NONE;
			default:
				throw new IllegalStateException("Unable to obtain state of [" + previous + "]");
		}
	}

	public static EnumAllowedEntities getNextStateReverse(EnumAllowedEntities previous) {
		switch(previous) {
			case NONE:
				return ALL;
			case ALL:
				return ITEMS_ONLY;
			case ITEMS_ONLY:
				return PLAYERS_ONLY;
			case PLAYERS_ONLY:
				return NON_PLAYERS_ONLY;
			case NON_PLAYERS_ONLY:
				return NONE;
			default:
				throw new IllegalStateException("Unable to obtain state of [" + previous + "]");
			
		}
	}

	public static EnumAllowedEntities getStateFromIndex(int index) {
		switch(index) {
			case 0:
				return NONE;
			case 1:
				return NON_PLAYERS_ONLY;
			case 2:
				return PLAYERS_ONLY;
			case 3:
				return ITEMS_ONLY;
			case 4:
				return ALL;
			default:
				throw new IllegalStateException("No state exists with index: [" + index + "]");
		}
	}
}