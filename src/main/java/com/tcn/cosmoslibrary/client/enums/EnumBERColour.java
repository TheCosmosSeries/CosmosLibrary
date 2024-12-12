package com.tcn.cosmoslibrary.client.enums;

import java.util.Arrays;
import java.util.Comparator;

import com.tcn.cosmoslibrary.common.lib.ComponentHelper.Value;

/**
 * 
 * Used for custom colour objects for {@link TileEntitySpecialRenderer}.
 * @param name [basic name of the colour]
 * @param colour [R-G-B] {% colour of each channel]
 * 
 * @author TheCosmicNebula_
 */
public enum EnumBERColour {

    WHITE(0, "white", "White", Value.WHITE, 0.9F, 0.9F, 0.9F),
    GRAY(1, "gray", "Gray", Value.GRAY, 0.5F, 0.5F, 0.5F),
	BLACK(2, "black", "Black", Value.BLACK, 0.1F, 0.1F, 0.1F),
	
	RED(3, "red", "Red", Value.RED, 255F/255F, 40F/255F, 40F/255F),
	GREEN(4, "green", "Green", Value.GREEN, 40F/255F, 255/255F, 40F/255F),
	BLUE(5, "blue", "Blue", Value.BLUE, 40F/255F, 40F/255F, 255F/255F),
	
	LIGHT_RED(6, "light_red", "Light Red", Value.LIGHT_RED, 210F/255F, 99F/255F, 99F/255F),
	LIGHT_GREEN(7, "light_green", "Light Green", Value.BRIGHT_GREEN, 54F/255F, 255F/255F, 24F/255F),
	LIGHT_BLUE(8, "light_blue", "Light Blue", Value.BRIGHT_BLUE, 99F/255F, 135F/255F, 210F/255F),
	
	DARK_RED(9, "dark_red", "Dark Red", Value.RED, 65F/255F, 16F/255F, 16F/255F),
	DARK_GREEN(10, "dark_green", "Dark Green", Value.GREEN, 17F/255F, 65F/255F, 16F/255F),
	DARK_BLUE(11, "dark_green", "Dark Green", Value.BLUE, 16F/255F, 16F/255F, 65F/255F),
    
	ORANGE(12, "orange", "Orange", Value.ORANGE, 255F/255F, 102F/255F, 0F/255F),
	
    PURPLE(13, "purple", "Purple", Value.PURPLE, 84/255F , 26/255F, 140/255F),
    YELLOW(14, "yellow", "Yellow", Value.YELLOW, 209F/255F, 199F/255F, 0F/255F);
	
	private int index;
    private final String name;
    private final float[] colour;
    private final String text_colour;
    private final String display_name;

	private static final EnumBERColour[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(EnumBERColour::getIndex)).toArray((index) -> { return new EnumBERColour[index]; });

    EnumBERColour(int index, String name, String display_name, String text_colour, float... conversionColorParticles) {
    	this.index = index;
        this.name = name;
        this.colour = conversionColorParticles;
        this.text_colour = text_colour;
        this.display_name = display_name;
    }
    
    @Override
    public String toString() {
    	return this.getName();
    }
    
    public String getName() {
    	return this.name;
    }

	public float[] getColour() {
		return this.colour;
	}
	
	public String getTextColour() {
		return this.text_colour;
	}
	
	public String getDisplayName() {
		return this.display_name;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public static EnumBERColour fromIndex(int indexIn) {
		if (indexIn < 0 || indexIn >= VALUES.length) {
			indexIn = 0;
		}
		
		return VALUES[indexIn];
	}
	
	/*
	@Override
	public String getSerializedName() {
		return this.name;
	}**/
}