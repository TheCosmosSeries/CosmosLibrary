package com.tcn.cosmoslibrary.common.lib;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;
import com.tcn.cosmoslibrary.CosmosReference.RESOURCE.BASE;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.fluids.FluidStack;

public final class ComponentHelper {
	
	public final class Value {
		public static final String BLACK = (char) 167 + "0";
		public static final String BLUE = (char) 167 + "1";
		public static final String GREEN = (char) 167 + "2";
		public static final String CYAN = (char) 167 + "3";
		public static final String RED = (char) 167 + "4";
		public static final String PURPLE = (char) 167 + "5";
		public static final String ORANGE = (char) 167 + "6";
		public static final String LIGHT_GRAY = (char) 167 + "7";
		public static final String GRAY = (char) 167 + "8";
		public static final String LIGHT_BLUE = (char) 167 + "9";
		public static final String BRIGHT_GREEN = (char) 167 + "a";
		public static final String BRIGHT_BLUE = (char) 167 + "b";
		public static final String LIGHT_RED = (char) 167 + "c";
		public static final String MAGENTA = (char) 167 + "d";
		public static final String YELLOW = (char) 167 + "e";
		public static final String WHITE = (char) 167 + "f";
	
		public static final String OBFUSCATED = (char) 167 + "k";
		public static final String BOLD = (char) 167 + "l";
		public static final String STRIKETHROUGH = (char) 167 + "m";
		public static final String UNDERLINE = (char) 167 + "n";
		public static final String ITALIC = (char) 167 + "o";
		public static final String END = (char) 167 + "r";
	}
	
	public static boolean isAltKeyDown(Minecraft mc) {
		return (InputConstants.isKeyDown(mc.getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_ALT) || InputConstants.isKeyDown(mc.getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_ALT));
	}

	public static boolean isControlKeyDown(Minecraft mc) {
		return (InputConstants.isKeyDown(mc.getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_CONTROL) || InputConstants.isKeyDown(mc.getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_CONTROL));
	}

	public static boolean isShiftKeyDown(Minecraft mc) {
		return (InputConstants.isKeyDown(mc.getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT) || InputConstants.isKeyDown(mc.getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_SHIFT));
	}
	
	public static int getSplitStringHeight(Font fontRenderer, FormattedText input, int width) {
		List<FormattedCharSequence> stringRows = fontRenderer.split(input, width);
		return stringRows.size() * fontRenderer.lineHeight;
	}
	
	public static String camelCase(String input) {
		return input.substring(0, 1).toLowerCase() + input.substring(1);
	}

	public static String titleCase(String input) {
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}

	@OnlyIn(Dist.CLIENT)
	public static String locString(String key) {
		return I18n.get(key);
	}

	@OnlyIn(Dist.CLIENT)
	public static String locString(String colour, String key) {
		return colour + I18n.get(key) + Value.END;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static String locString(String pre, String key, String suff) {
		return pre + I18n.get(key) + suff + Value.END;
	}

	public static MutableComponent empty() {
		return Component.translatable("");
	}
	
	public static MutableComponent title(String key) {
		return Component.translatable(key);
	}

	public static MutableComponent comp(String key) {
		return Component.translatable(key);
	}
	
	/**
	 * 
	 * @param colourIn {@link Integer} - Text Colour
	 * @param flags {@link String} - Style. In the format: "bold underline italic strikethrough obfuscated"
	 * @param keyIn {@link String} - Text you actually want. Can be localized or unlocalized.
	 * @return {@link MutableComponent} - Styled Component.
	 */
	public static MutableComponent style(int colourIn, String flags, String keyIn) {
		MutableComponent comp = Component.translatable(keyIn);
		
		comp.setStyle(Style.EMPTY
			.withBold(flags.contains("bold"))
			.withUnderlined(flags.contains("underline"))
			.withItalic(flags.contains("italic"))
			.withStrikethrough(flags.contains("strikethrough"))
			.withObfuscated(flags.contains("obfuscated"))
			.withColor(TextColor.fromRgb(colourIn))
		);
		
		return comp;
	}

	public static MutableComponent style(int colourIn, String keyIn) {
		return style(colourIn, "", keyIn);
	}

	public static MutableComponent style(ComponentColour colourIn, String keyIn) {
		return style(colourIn.dec(), "", keyIn);
	}
	
	public static MutableComponent style(ComponentColour colourIn, String flags, String keyIn) {
		return style(colourIn.dec(), flags, keyIn);
	}

	public static MutableComponent style3(int colourIn, String flags, String keyInA, String keyInB, String keyInC) {
		return (MutableComponent) style(colourIn, flags, keyInA).append(style(colourIn, flags, keyInB)).append(style(colourIn, flags, keyInC));
	}

	public static MutableComponent style3(int colourIn, String keyInA, String keyInB, String keyInC) {
		return (MutableComponent) style(colourIn, "", keyInA).append(style(colourIn, "", keyInB)).append(style(colourIn, "", keyInC));
	}

	public static MutableComponent style3(ComponentColour colourIn, String keyInA, String keyInB, String keyInC) {
		return (MutableComponent) style(colourIn, "", keyInA).append(style(colourIn, "", keyInB)).append(style(colourIn, "", keyInC));
	}

	public static MutableComponent style3(ComponentColour colourIn, String flags, String keyInA, String keyInB, String keyInC) {
		return (MutableComponent) style(colourIn, flags, keyInA).append(style(colourIn, flags, keyInB)).append(style(colourIn, flags, keyInC));
	}

	public static MutableComponent style2(ComponentColour colourIn, String flags, String keyInA, String keyInB) {
		return (MutableComponent) style(colourIn, flags, keyInA).append(style(colourIn, flags, keyInB));
	}
	
	public static MutableComponent style2(ComponentColour colourIn, String... keys) {
		return (MutableComponent) style(colourIn, "", keys[0]).append(style(colourIn, "", keys[1]));
	}
	
	public static String getFluidName(FluidStack fluid) {
		return getFluidName(fluid.getFluid());
	}

	public static String getFluidName(Fluid fluid) {
		String fluidName = "";
		if (fluid.getFluidType().getTemperature() > 1000) {
			fluidName = fluidName + Value.RED;
		} else {
			fluidName = fluidName + Value.BLUE;
		}
		fluidName = fluidName + locString(fluid.getFluidType().getDescription().toString()) + Value.END;

		return fluidName;
	}

	public static String getScaledNumber(int number) {
		return getScaledNumber(number, 2);
	}

	public static String getScaledNumber(int number, int minDigits) {
		String numString = "";

		int numMod = 10 * minDigits;
		if (number > 100000 * numMod) {
			numString = numString + number / 1000000 + "M";
		} else if (number > 100 * numMod) {
			numString = numString + number / 1000 + "k";
		} else {
			numString = numString + number;
		}
		
		return numString;
	}

	public static MutableComponent shiftForMoreDetails() {
		return (MutableComponent) style(ComponentColour.WHITE, BASE.TOOLTIP_HOLD).append(style(ComponentColour.BLACK,  " ")).append(style(ComponentColour.ORANGE, "bold", BASE.TOOLTIP_SHIFT)
				.append(style(ComponentColour.BLACK, " ")).append(style(ComponentColour.WHITE, BASE.TOOLTIP_MORE)));
	}
	
	public static MutableComponent shiftForLessDetails() {
		return (MutableComponent) style(ComponentColour.WHITE, BASE.TOOLTIP_RELEASE).append(style(ComponentColour.BLACK,  " ")).append(style(ComponentColour.ORANGE, "bold", BASE.TOOLTIP_SHIFT)
				.append(style(ComponentColour.BLACK, " ")).append(style(ComponentColour.WHITE, BASE.TOOLTIP_LESS)));
	}
	
	public static MutableComponent ctrlForMoreDetails() {
		return (MutableComponent) style(ComponentColour.WHITE, BASE.TOOLTIP_HOLD).append(style(ComponentColour.BLACK,  " ")).append(style(ComponentColour.LIGHT_GRAY, "bold", BASE.TOOLTIP_CTRL)
				.append(style(ComponentColour.BLACK, " ")).append(style(ComponentColour.WHITE,  BASE.TOOLTIP_NBT)));
	}
	
	public static MutableComponent ctrlForLessDetails() {
		return (MutableComponent) style(ComponentColour.WHITE, BASE.TOOLTIP_RELEASE).append(style(ComponentColour.BLACK, " ")).append(style(ComponentColour.LIGHT_GRAY, "bold", BASE.TOOLTIP_CTRL)
				.append(style(ComponentColour.BLACK," ")).append(style(ComponentColour.WHITE, BASE.TOOLTIP_NBT_LESS)));
	}

	public static MutableComponent altForMoreDetails(ComponentColour colourIn) {
		return (MutableComponent) style(ComponentColour.WHITE, BASE.TOOLTIP_HOLD).append(style(ComponentColour.BLACK, " ")).append(style(colourIn, "bold", BASE.TOOLTIP_ALT)
				.append(style(ComponentColour.BLACK, " ")).append(style(ComponentColour.WHITE, BASE.TOOLTIP_ENERGY)));
	}
	
	public static MutableComponent altForLessDetails(ComponentColour colourIn) {
		return (MutableComponent) style(ComponentColour.WHITE,  BASE.TOOLTIP_RELEASE).append(style(ComponentColour.BLACK, " ")).append(style(colourIn, "bold", BASE.TOOLTIP_ALT)
				.append(style(ComponentColour.BLACK, " ")).append(style(ComponentColour.WHITE, BASE.TOOLTIP_ENERGY_LESS)));
	}

	public static MutableComponent getTooltipInfo(String key) {
		return style(ComponentColour.LIGHT_GRAY, key);
	}

	public static MutableComponent getTooltipOne(String key) {
		return style(ComponentColour.CYAN, key);
	}

	public static MutableComponent getTooltipTwo(String key) {
		return style(ComponentColour.GREEN, key);
	}
	
	public static MutableComponent getTooltipThree(String key) {
		return style(ComponentColour.LIGHT_BLUE, key);
	}

	public static MutableComponent getTooltipFour(String key) {
		return style(ComponentColour.LIME, key);
	}
	
	public static MutableComponent getTooltipLimit(String key) {
		return style(ComponentColour.LIGHT_RED, key);
	}
	
	public static MutableComponent getErrorText(String key) {
		return style(ComponentColour.RED, key);
	}

	public static boolean displayShiftForDetail = true;
	public static boolean displayCtrlForDetail = true;
	public static boolean displayAltForDetail = true;
	
}