package com.tcn.cosmoslibrary.common.enums;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;

public enum EnumUIHelp {
	
	HIDDEN(0, "hidden", "cosmoslibrary.enum.ui_help.hidden", false, ComponentColour.RED),
	SHOWN(1, "shown", "cosmoslibrary.enum.ui_help.shown", true, ComponentColour.GREEN);
	
	private int index;
	private String name;
	private String localized_name;
	private boolean value;
	private ComponentColour colour;

	public static final StreamCodec<ByteBuf, EnumUIHelp> STREAM_CODEC = new StreamCodec<ByteBuf, EnumUIHelp>() {
		@Override
        public EnumUIHelp decode(ByteBuf bufIn) {
            return EnumUIHelp.getStateFromIndex(bufIn.readInt());
        }

		@Override
        public void encode(ByteBuf bufIn, EnumUIHelp modeIn) {
        	bufIn.writeInt(modeIn.getIndex());
        }
    };
    
	EnumUIHelp(int indexIn, String nameIn, String localizedNameIn, boolean valueIn, ComponentColour colourIn) {
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
	
	public static EnumUIHelp getOpposite(EnumUIHelp state) {
		if (state.equals(HIDDEN)) {
			return SHOWN;
		} else {
			return HIDDEN;
		}
	}
	
	public static EnumUIHelp getStateFromIndex(int index) {
		switch(index) {
			case 0:
				return HIDDEN;
			case 1:
				return SHOWN;
			default:
				return HIDDEN;
		}
	}
	
	public static EnumUIHelp getStateFromValue(boolean value) {
		if (value) {
			return SHOWN;
		} else {
			return HIDDEN;
		}
	}
	
	public EnumUIHelp getNextState() {
		switch(this) {
			case HIDDEN:
				return SHOWN;
			case SHOWN:
				return HIDDEN;
			default:
				throw new IllegalStateException("Unable to obtain next state of [" + this + "]");
		}
	}
	
	public static EnumUIHelp getNextStateFromState(EnumUIHelp previous) {
		switch (previous) {
		case HIDDEN:
			return SHOWN;
		case SHOWN:
			return HIDDEN;
		default:
			throw new IllegalStateException("Unable to obtain next state of [" + previous + "]");
		}
	}
}