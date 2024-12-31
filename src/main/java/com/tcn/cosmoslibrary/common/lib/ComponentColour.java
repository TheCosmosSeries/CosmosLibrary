package com.tcn.cosmoslibrary.common.lib;

import java.util.Arrays;
import java.util.Comparator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper.Value;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.FastColor;

public enum ComponentColour {

	WHITE(0, "White", 16777215, Value.WHITE, 255, 255, 255, false),
	ORANGE(1, "Orange", 16755200, Value.ORANGE, 255, 170, 0, false),
	MAGENTA(2, "Magenta", 16733695, Value.MAGENTA, 255, 85, 255, false),
	LIGHT_BLUE(3, "Light Blue", 5592575, Value.LIGHT_BLUE, 85, 85, 255, false),
	YELLOW(4, "Yellow", 16777045, Value.YELLOW, 255, 255, 85, false),
	LIME(5, "Lime", 5635925, Value.BRIGHT_GREEN, 85, 255, 85, false),
	PINK(6, "Pink", 15961002, "", 243, 139, 170, false),
	GRAY(7, "Gray", 5592405, Value.GRAY, 85, 85, 85, true),
	LIGHT_GRAY(8, "Light Gray", 11184810, Value.LIGHT_GRAY, 170, 170, 170, false),
	CYAN(9, "Cyan", 43690, Value.CYAN, 0, 170, 170, false),
	PURPLE(10, "Purple", 11141290, Value.PURPLE, 170, 0, 170, true),
	BLUE(11, "Blue", 170, Value.BLUE, 0, 0, 170, true),
	BROWN(12, "Brown", 8606770, "", 131, 84, 50, true),
	GREEN(13, "Green", 43520, Value.GREEN, 0, 170, 0, false),
	RED(14, "Red", 11141120, Value.RED, 170, 0, 0, true),
	BLACK(15, "Black", 1579032, Value.BLACK, 24, 24, 24, true),
	POCKET_PURPLE(16, "Pocket Purple", 4134239, Value.PURPLE, 35, 12, 53, true),
	POCKET_PURPLE_LIGHT (17, "Pocket Purple Light", 6627993, Value.PURPLE, 101, 34, 154, true),
	POCKET_PURPLE_GUI (18, "Pocket Purple GUI", 10748079, Value.PURPLE, 164, 0, 175, false),
	LIGHT_RED(19, "Light Red", 16733525, Value.LIGHT_RED, 255, 85, 85, false),
	SCREEN_DARK(20, "Gui Background", 4210752, Value.GRAY, 85, 85, 85, true),
	SCREEN_LIGHT(21, "Gui Font List", 16777215, Value.LIGHT_GRAY, 170, 170, 170, false),
	DARK_GREEN(22, "Dark Green", 25600, Value.GREEN, 0, 100, 0, true),
	DARK_RED(23, "Dark Red", 6553600, Value.RED, 100, 0, 0, true),
	DARK_YELLOW(24, "Dark Yellow", 8355584, Value.YELLOW, 127, 127, 0, true),
	TURQUOISE(25, "Turquoise", 50115, Value.CYAN, 0, 225, 225, false),
	DARK_CYAN(26, "Dark Cyan", 26214, Value.CYAN, 0, 102, 102, true),
	BLURPLE(27, "Blurple", 7506394, Value.CYAN, 114, 137, 218, true),
	BLURPLE_LIGHT(28, "Blurple Light", 3692707, Value.CYAN, 56, 88, 163, false),
	EMPTY(29, "Default", 0, Value.LIGHT_GRAY, 0, 0, 0, true),
	END(30, "End", 2458740, Value.GREEN, 37, 132, 116, true),
	ELYTRA(31, "Elytra", 7113119, Value.CYAN, 108, 137, 159, false);

	private final int index;
	private final String name;
	private final int decimal;
	private final String chat_colour;
	private final int[] RGB;
	private final float[] RGBF;
	private final boolean dark;

	private static final ComponentColour[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(ComponentColour::getIndex)).toArray((index) -> { return new ComponentColour[index]; });

	ComponentColour(int indexIn, String nameIn, int decimalIn, String chatColourIn, int rIn, int gIn, int bIn, boolean isDarkIn) {
		this.index = indexIn;
		this.name = nameIn;
		this.decimal = decimalIn;
		this.chat_colour = chatColourIn;
		this.RGB = new int[] { rIn, gIn, bIn};
		this.RGBF = new float[] { rIn / 255.0F, gIn / 255.0F, bIn / 255.0F };
		this.dark = isDarkIn;
	}

	public static final Codec<ComponentColour> CODEC = RecordCodecBuilder.create(instance ->
		instance.group(Codec.INT.fieldOf("index").forGetter(ComponentColour::getIndex)).apply(instance, ComponentColour::fromIndex)
	);
	
	public static final StreamCodec<ByteBuf, ComponentColour> STREAM_CODEC = new StreamCodec<ByteBuf, ComponentColour>() {
		@Override
		public ComponentColour decode(ByteBuf bufIn) {
            return ComponentColour.fromIndex(bufIn.readInt());
        }

		@Override
        public void encode(ByteBuf bufIn, ComponentColour modeIn) {
        	bufIn.writeInt(modeIn.getIndex());
        }
    };
    
    @Override
	public String toString() {
		return this.name;
	}

	public int getIndex() {
		return this.index;
	}

	public String getName() {
		return this.name;
	}

	public int dec() {
		return this.decimal;
	}

	public int decOpaque() {
		return FastColor.ARGB32.opaque(this.decimal);
	}

	public int alpha() {
		return FastColor.ARGB32.alpha(this.decimal);
	}
	
	public int withAlpha(float alpha) {
		return FastColor.ARGB32.colorFromFloat(alpha, this.RGBF[0], this.RGBF[1], this.RGBF[2]);
	}

	public static int opaque(int colour) {
		return FastColor.ARGB32.opaque(colour);
	}

	public static int alpha(int colour) {
		return FastColor.ARGB32.alpha(colour);
	}

	public String getChatColour() {
		if (this.chat_colour != null) {
			return this.chat_colour;
		} else {
			return "";
		}
	}
	
	public MutableComponent getColouredName() {
		return ComponentHelper.style(this == EMPTY ? LIGHT_GRAY : this, "bold", this.name);
	}

	public static ComponentColour fromIndex(int colorId) {
		if (colorId < 0 || colorId >= VALUES.length) {
			colorId = 0;
		}
		
		return VALUES[colorId];
	}
	
	public int[] getRGB() {
		return this.RGB;
	}
	
	public float[] getFloatRGB() {
		return this.RGBF;
	}

	public boolean isDark() {
		return this.dark;
	}
	
	public static ComponentColour col(int decimal) {
		for (ComponentColour colour : values()) {
			if (colour.dec() == decimal) {
				return colour;
			}
		}
		return WHITE;
	}
	
	public static int[] rgbIntArray(ComponentColour colour) {
		return rgbIntArray(colour.decimal);
	}
	
	public static int[] rgbIntArray(int decimal) {
		for (ComponentColour colour : values()) {
			if (colour.dec() == decimal) {
				return colour.getRGB();
			}
		}
		return new int[] { 255, 255, 255 };
	}

	public static float[] rgbFloatArray(ComponentColour colour) {
		return rgbFloatArray(colour.decimal);
	}
	
	public static float[] rgbFloatArray(int decimal) {
		for (ComponentColour colour : values()) {
			if (colour.dec() == decimal) {
				int[] RGB_ = colour.getRGB();
				
				return new float[] {RGB_[0] / 255.0F, RGB_[1] / 255.0F, RGB_[2] / 255.0F };	
			}
		}
		return new float[] { 1.0F, 1.0F, 1.0F };
	}
	
	public static int decFromCol(ComponentColour colour) {
		return colour.decimal;
	}
	
	public static ComponentColour getCompColourForScreen(ComponentColour colour) {
		return !colour.isDark() ? SCREEN_DARK : SCREEN_LIGHT;
	}
	
	public ComponentColour getNextVanillaColour(boolean includeNoColour) {
		switch (this) {
			case EMPTY:
				return WHITE;
			case WHITE:
				return ORANGE;
			case ORANGE:
				return MAGENTA;
			case MAGENTA:
				return LIGHT_BLUE;
			case LIGHT_BLUE:
				return YELLOW;
			case YELLOW:
				return LIME;
			case LIME:
				return PINK;
			case PINK:
				return GRAY;
			case GRAY:
				return LIGHT_GRAY;
			case LIGHT_GRAY:
				return CYAN;
			case CYAN:
				return PURPLE;
			case PURPLE:
				return BLUE;
			case BLUE:
				return BROWN;
			case BROWN:
				return GREEN;
			case GREEN:
				return RED;
			case RED:
				return BLACK;
			case BLACK:
				return includeNoColour ? EMPTY : WHITE;
			default:
				return WHITE;
		}
	}

	public ComponentColour getNextVanillaColourReverse(boolean includeNoColour) {
		switch (this) {
			case EMPTY:
				return BLACK;
			case BLACK:
				return RED;
			case RED:
				return GREEN;
			case GREEN:
				return BROWN;
			case BROWN:
				return BLUE;
			case BLUE:
				return PURPLE;
			case PURPLE:
				return CYAN;
			case CYAN:
				return LIGHT_GRAY;
			case LIGHT_GRAY:
				return GRAY;
			case GRAY:
				return PINK;
			case PINK:
				return LIME;
			case LIME:
				return YELLOW;
			case YELLOW:
				return LIGHT_BLUE;
			case LIGHT_BLUE:
				return MAGENTA;
			case MAGENTA:
				return ORANGE;
			case ORANGE:
				return WHITE;
			case WHITE:
				return includeNoColour ? EMPTY : BLACK;
			default:
				return BLACK;
		}
	}

	public ComponentColour getNextVanillaColourPocket() {
		switch (this) {
			case WHITE:
				return ORANGE;
			case ORANGE:
				return MAGENTA;
			case MAGENTA:
				return LIGHT_BLUE;
			case LIGHT_BLUE:
				return YELLOW;
			case YELLOW:
				return LIME;
			case LIME:
				return PINK;
			case PINK:
				return GRAY;
			case GRAY:
				return LIGHT_GRAY;
			case LIGHT_GRAY:
				return CYAN;
			case CYAN:
				return PURPLE;
			case PURPLE:
				return BLUE;
			case BLUE:
				return BROWN;
			case BROWN:
				return GREEN;
			case GREEN:
				return RED;
			case RED:
				return BLACK;
			case BLACK:
				return POCKET_PURPLE;
			case POCKET_PURPLE:
				return WHITE;
			default:
				return WHITE;
		}
	}

	public ComponentColour getNextVanillaColourReversePocket() {
		switch (this) {
			case POCKET_PURPLE:
				return BLACK;
			case BLACK:
				return RED;
			case RED:
				return GREEN;
			case GREEN:
				return BROWN;
			case BROWN:
				return BLUE;
			case BLUE:
				return PURPLE;
			case PURPLE:
				return CYAN;
			case CYAN:
				return LIGHT_GRAY;
			case LIGHT_GRAY:
				return GRAY;
			case GRAY:
				return PINK;
			case PINK:
				return LIME;
			case LIME:
				return YELLOW;
			case YELLOW:
				return LIGHT_BLUE;
			case LIGHT_BLUE:
				return MAGENTA;
			case MAGENTA:
				return ORANGE;
			case ORANGE:
				return WHITE;
			case WHITE:
				return POCKET_PURPLE;
			default:
				return BLACK;
		}
	}

	public ComponentColour getNextVanillaColourElytra() {
		switch (this) {
			case WHITE:
				return ORANGE;
			case ORANGE:
				return MAGENTA;
			case MAGENTA:
				return LIGHT_BLUE;
			case LIGHT_BLUE:
				return YELLOW;
			case YELLOW:
				return LIME;
			case LIME:
				return PINK;
			case PINK:
				return GRAY;
			case GRAY:
				return LIGHT_GRAY;
			case LIGHT_GRAY:
				return CYAN;
			case CYAN:
				return PURPLE;
			case PURPLE:
				return BLUE;
			case BLUE:
				return BROWN;
			case BROWN:
				return GREEN;
			case GREEN:
				return RED;
			case RED:
				return BLACK;
			case BLACK:
				return POCKET_PURPLE;
			case POCKET_PURPLE:
				return ELYTRA;
			case ELYTRA:
				return WHITE;
			default:
				return WHITE;
		}
	}

	public ComponentColour getNextVanillaColourReverseElytra() {
		switch (this) {
			case POCKET_PURPLE:
				return BLACK;
			case BLACK:
				return RED;
			case RED:
				return GREEN;
			case GREEN:
				return BROWN;
			case BROWN:
				return BLUE;
			case BLUE:
				return PURPLE;
			case PURPLE:
				return CYAN;
			case CYAN:
				return LIGHT_GRAY;
			case LIGHT_GRAY:
				return GRAY;
			case GRAY:
				return PINK;
			case PINK:
				return LIME;
			case LIME:
				return YELLOW;
			case YELLOW:
				return LIGHT_BLUE;
			case LIGHT_BLUE:
				return MAGENTA;
			case MAGENTA:
				return ORANGE;
			case ORANGE:
				return WHITE;
			case WHITE:
				return ELYTRA;
			case ELYTRA:
				return POCKET_PURPLE;
			default:
				return BLACK;
		}
	}

	public static ComponentColour getNextVanillaColour(boolean includeNoColour, ComponentColour colourIn) {
		switch (colourIn) {
			case EMPTY:
				return WHITE;
			case WHITE:
				return ORANGE;
			case ORANGE:
				return MAGENTA;
			case MAGENTA:
				return LIGHT_BLUE;
			case LIGHT_BLUE:
				return YELLOW;
			case YELLOW:
				return LIME;
			case LIME:
				return PINK;
			case PINK:
				return GRAY;
			case GRAY:
				return LIGHT_GRAY;
			case LIGHT_GRAY:
				return CYAN;
			case CYAN:
				return PURPLE;
			case PURPLE:
				return BLUE;
			case BLUE:
				return BROWN;
			case BROWN:
				return GREEN;
			case GREEN:
				return RED;
			case RED:
				return BLACK;
			case BLACK:
				return includeNoColour ? EMPTY : WHITE;
			default:
				return WHITE;
		}
	}
	
	public boolean isEmpty() {
		return this == EMPTY;
	}
}