package com.tcn.cosmoslibrary;

import net.minecraft.resources.ResourceLocation;

/**
 * Collection of static fields for reference purposes.
 * @author TheCosmicNebula_
 */
public class CosmosReference {
	
	/**
	 * Static access to standard values required by multiple classes.
	 */
	public static class RESOURCE {
		
		/**
		 * Prefix for all ResourceLocations.
		 */
		public static final String PRE = CosmosLibrary.MOD_ID + ":";
		public static final String RESOURCE = PRE + "textures/";
		
		/**
		 * ResourceLocations / Path Strings for Base Objects
		 */
		public static class BASE {
			public static final String BASE = RESOURCE + "base/";
			public static final String BLOCKS = BASE + "block/";
			public static final String ITEMS = BASE + "item/";
			public static final String GUI = BASE + "gui/";
			
			/** Gui */
			
			/** - Button - */
			public static final ResourceLocation BUTTON_ICON_PATH = ResourceLocation.parse(GUI + "button/button_icon.png");
			public static final ResourceLocation BUTTON_ICON_PATH_ALT = ResourceLocation.parse(GUI + "button/button_icon_0.png");
			
			public static final ResourceLocation BUTTON_GENERAL_PATH = ResourceLocation.parse(GUI + "button/button_general.png");
			public static final ResourceLocation BUTTON_GENERAL_PATH_ALT = ResourceLocation.parse(GUI + "button/button_general_0.png");
			
			public static final ResourceLocation BUTTON_ENERGY_PATH = ResourceLocation.parse(GUI + "button/button_energy.png");
			public static final ResourceLocation BUTTON_ENERGY_PATH_ALT = ResourceLocation.parse(GUI + "button/button_energy_0.png");
			
			public static final ResourceLocation BUTTON_FLUID_PATH = ResourceLocation.parse(GUI + "button/button_fluid.png");
			public static final ResourceLocation BUTTON_FLUID_PATH_ALT = ResourceLocation.parse(GUI + "button/button_fluid_0.png");
			
			public static final ResourceLocation BUTTON_ITEM_PATH = ResourceLocation.parse(GUI + "button/button_item.png");
			public static final ResourceLocation BUTTON_ITEM_PATH_ALT = ResourceLocation.parse(GUI + "button/button_item_0.png");
			
			public static final ResourceLocation BUTTON_STORAGE_PATH = ResourceLocation.parse(GUI + "button/button_storage.png");
			public static final ResourceLocation BUTTON_STORAGE_PATH_ALT = ResourceLocation.parse(GUI + "button/button_storage_0.png");
			
			public static final ResourceLocation BUTTON_UI = ResourceLocation.parse(GUI + "button/button_ui_icon.png");

			public static final ResourceLocation BUTTON_COLOUR = ResourceLocation.parse(GUI + "button/button_colour.png");
			
			/** - Misc UI - */
			public static final ResourceLocation UI_DARKEN = ResourceLocation.parse(GUI + "ui_darken.png");
			public static final ResourceLocation UI_LIGHTEN = ResourceLocation.parse(GUI + "ui_lighten.png");
			public static final ResourceLocation UI_LIGHTEN_HIGHLIGHT = ResourceLocation.parse(GUI + "ui_lighten_highlight.png");
			
			public static final ResourceLocation GUI_SLOT_LOC = ResourceLocation.parse(GUI + "gui_slot.png");
			public static final ResourceLocation UI_ENERGY_VERTICAL = ResourceLocation.parse(GUI + "ui_energy_vertical.png");
			public static final ResourceLocation UI_ENERGY_HORIZONTAL = ResourceLocation.parse(GUI + "ui_energy_horizontal.png");
			public static final ResourceLocation GUI_DIRECTION_LOC = ResourceLocation.parse(GUI + "gui_direction.png");
			public static final ResourceLocation GUI_ELEMENT_MISC_LOC = ResourceLocation.parse(GUI + "gui_element_misc.png");
			
			/** ToolTip */
			public static final String TOOLTIP_HOLD = "cosmoslibrary.info.hold.name";
			
			public static final String TOOLTIP_SHIFT = "cosmoslibrary.info.shift.name";
			public static final String TOOLTIP_CTRL = "cosmoslibrary.info.ctrl.name";
			public static final String TOOLTIP_ALT = "cosmoslibrary.info.alt.name";

			public static final String TOOLTIP_NBT = "cosmoslibrary.info.nbt.name";
			public static final String TOOLTIP_NBT_LESS = "cosmoslibrary.info.nbt.less.name";

			public static final String TOOLTIP_ENERGY = "cosmoslibrary.info.energy.name";
			public static final String TOOLTIP_ENERGY_LESS = "cosmoslibrary.info.energy.less.name";
			
			public static final String TOOLTIP_MORE = "cosmoslibrary.info.fordetails.name";
			public static final String TOOLTIP_RELEASE = "cosmoslibrary.info.release.name";
			public static final String TOOLTIP_LESS = "cosmoslibrary.info.less.name";
			
			public static final String TOOLTIP_ENERGYITEM = "cosmoslibrary.info.tooltip.energy.name";
			public static final String TOOLTIP_FLUIDITEM = "cosmoslibrary.info.tooltip.fluid.name";
			
			public static final String TOOLTIP_CURRENT_POWER = "cosmoslibrary.info.currentpower.name";
			public static final String TOOLTIP_MAX_POWER = "cosmoslibrary.info.maxpower.name";
		}
		
		public static class INFO {
			/** Button Spacing [-X-] */
			public static final int BUTTON_X_SPACING = 44;
			public static final int BUTTON_X_SPACING_SMALL = 67;
			
			/** Locations [-X-] for each button in the button array. */
			public static final int[] BUTTON_STATE_X = { 0, 38, 76, 114, 152, 190 };
			public static final int[] BUTTON_STATE_X_SMALL = { 20, 58, 96, 134, 172, 210 };
			
			/** Button Spacing [-Y-] */
			public static final int BUTTON_Y_SPACING = 23;
			/** Locations [-Y-] for each button in the button array. */
			public static final int[] BUTTON_STATE_Y = { 0, 20, 40, 60, 80, 100, 120, 140, 160, 180, 200, 220 };
			public static final int[] BUTTON_STATE_Y_SMALL = { 0, 20, 40, 60, 80, 100, 120, 140, 160, 180, 200, 220 };
			
			/** Slots in the format: [ texture-x, texture-y, width, height ] */
			public static final int[] SLOT_REGULAR_SMALL = new int[] { 0, 0, 18, 18 };
			public static final int[] SLOT_OUTPUT_SMALL = new int[] { 18, 0, 18, 18 };
			public static final int[] SLOT_INPUT_SMALL = new int[] { 36, 0, 18, 18 };
			
			/** Energy-bar in the format: [ texture-x, texture-y, texture-height, texture-width ]*/
			public static final int[] ENERGY_BAR = new int[] { 72, 61, 62, 18 };
			public static final int[] ENERGY_BAR_SMALL = new int[] { 72, 102, 40, 18 };
			
			@Deprecated
			public static final int[] POWERED_BAR = new int[] { 18, 61, 62, 18 };
			@Deprecated
			public static final int[] POWERED_BAR_SMALL = new int[] { 18, 102, 40, 18 };
			
			@Deprecated
			public static final int[] ENERGIZED_BAR = new int[] { 36, 61, 62, 18 };
			@Deprecated
			public static final int[] ENERGIZED_BAR_SMALL = new int[] { 36, 102, 40, 18 };
			
			@Deprecated
			public static final int[] CREATIVE_BAR = new int[] { 54, 61, 62, 18	};
			@Deprecated
			public static final int[] CREATIVE_BAR_SMALL = new int[] { 54, 102, 40, 18 };
		}
	}
	
	/**
	 * JEI recipes.
	 */
	public static class JEI {
		public static final String GRINDER_UID = CosmosLibrary.MOD_ID + ":grinder";
		
		public static final String COMPACTOR_UID = CosmosLibrary.MOD_ID + ":compactor";
		
		public static final String SEPARATOR_UID = CosmosLibrary.MOD_ID + ":separator";
		
		public static final String SYNTHESISER_UID = CosmosLibrary.MOD_ID + ":synthesiser";
		
		public static final String EXPERIENCE = "jei.experience";
	}
}