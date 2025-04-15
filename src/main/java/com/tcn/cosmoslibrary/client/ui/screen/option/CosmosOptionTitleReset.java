package com.tcn.cosmoslibrary.client.ui.screen.option;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class CosmosOptionTitleReset extends CosmosOptionInstance<String> {
	
	private Button.OnPress onPressFunction;
	
	public CosmosOptionTitleReset(MutableComponent captionIn, Button.OnPress onPress) {
		super(captionIn, CosmosOptionInstance.noTooltip(), (component, value) -> { return ComponentHelper.empty(); }, new CosmosOptionInstance.Enum<String>(ImmutableList.of(""), Codec.STRING), "", "", (help) -> {}, true, "");
		
		this.onPressFunction = onPress;
	}

	@Override
	public AbstractWidget createButton(int xPosIn, int yPosIn, int widthIn, int heightIn) {
		return new BlankTileButton(xPosIn, yPosIn, widthIn, 16, this.caption, false);
	}

	@Override
	public AbstractWidget createResetButton(int xPosIn, int yPosIn, int widthIn, int heightIn) {
		return new BlankTileButton(xPosIn + widthIn + 4, yPosIn, heightIn, heightIn, ComponentHelper.style(ComponentColour.TURQUOISE, "R"), true, this.onPressFunction);
	}
	
	@OnlyIn(Dist.CLIENT)
	public class BlankTileButton extends Button {
		
		public boolean doRenderBackground;
		
		public BlankTileButton(int xPosIn, int yPosIn, int widthIn, int heightIn, Component titleMessageIn, boolean doRenderBackgoundIn) {
			super(xPosIn, yPosIn, widthIn, heightIn, titleMessageIn, (button) -> { }, (button) -> { return ComponentHelper.empty(); });
			
			this.doRenderBackground = doRenderBackgoundIn;
		}

		public BlankTileButton(int xPosIn, int yPosIn, int widthIn, int heightIn, Component titleMessageIn, boolean doRenderBackgoundIn, Button.OnPress function) {
			super(xPosIn, yPosIn, widthIn, heightIn, titleMessageIn, function, (button) -> { return ComponentHelper.empty(); });
			
			this.doRenderBackground = doRenderBackgoundIn;
		}
		
		@Override
		public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
			Minecraft minecraft = Minecraft.getInstance();
			Font font = minecraft.font;
			
			if (this.doRenderBackground) {
				super.renderWidget(graphics, mouseX, mouseY, partialTicks);
			}
			
			int j = getFGColor();
			graphics.drawCenteredString(font, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
		}
	}
}