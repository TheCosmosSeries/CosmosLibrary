package com.tcn.cosmoslibrary.common.enums;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;

public enum EnumUIMode {
	
	DARK(0, "dark", "cosmoslibrary.enum.ui_mode.dark", false, ComponentColour.LIGHT_GRAY), 
	LIGHT(1, "light", "cosmoslibrary.enum.ui_mode.light", true, ComponentColour.YELLOW);
	
	private int index;
	private String name;
	private String localized_name;
	private boolean value;
	private ComponentColour colour;

	public static final StreamCodec<ByteBuf, EnumUIMode> STREAM_CODEC = new StreamCodec<ByteBuf, EnumUIMode>() {
		@Override
        public EnumUIMode decode(ByteBuf bufIn) {
            return EnumUIMode.getStateFromIndex(bufIn.readInt());
        }

		@Override
        public void encode(ByteBuf bufIn, EnumUIMode modeIn) {
        	bufIn.writeInt(modeIn != null ? modeIn.getIndex() : 0);
        }
    };
    
	EnumUIMode(int indexIn, String nameIn, String localizedNameIn, boolean valueIn, ComponentColour colourIn) {
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
	
	public static EnumUIMode getOpposite(EnumUIMode state) {
		if (state.equals(DARK)) {
			return LIGHT;
		} else {
			return DARK;
		}
	}
	
	public static EnumUIMode getStateFromIndex(int index) {
		switch(index) {
			case 0:
				return DARK;
			case 1:
				return LIGHT;
			default:
				return DARK;
		}
	}
	
	public static EnumUIMode getStateFromValue(boolean value) {
		if (value) {
			return LIGHT;
		} else {
			return DARK;
		}
	}
	
	public EnumUIMode getNextState() {
		switch(this) {
			case DARK:
				return LIGHT;
			case LIGHT:
				return DARK;
			default:
				throw new IllegalStateException("Unable to obtain next state of [" + this + "]");
		}
	}
	
	public static EnumUIMode getNextStateFromState(EnumUIMode previous) {
		switch (previous) {
		case DARK:
			return LIGHT;
		case LIGHT:
			return DARK;
		default:
			throw new IllegalStateException("Unable to obtain next state of [" + previous + "]");
		}
	}
}