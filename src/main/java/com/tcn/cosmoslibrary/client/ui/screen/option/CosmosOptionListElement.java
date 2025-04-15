package com.tcn.cosmoslibrary.client.ui.screen.option;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class CosmosOptionListElement extends CosmosOptionInstance<String> {
	private Button.OnPress onPressFunction;
	private MutableComponent buttonText;
	private MutableComponent extraButtonText;
	private Button.CreateNarration narration;
	
	public CosmosOptionListElement(MutableComponent captionIn, boolean hasExtraButton, MutableComponent buttonTextIn, MutableComponent extraButtonTextIn, Button.OnPress onPressFunctionIn, Button.CreateNarration narrationIn) {
		super(captionIn, CosmosOptionInstance.noTooltip(), (component, value) -> { return ComponentHelper.empty(); }, new CosmosOptionInstance.Enum<String>(ImmutableList.of(""), Codec.STRING), "", "", (help) -> {  }, hasExtraButton, "");
		
		this.onPressFunction = onPressFunctionIn;
		this.buttonText = buttonTextIn;
		this.extraButtonText = extraButtonTextIn;
		this.narration = narrationIn;
	}

	@Override
	public AbstractWidget createButton(int xPosIn, int yPosIn, int widthIn, int heightIn) {
		return new BlankTileButton(xPosIn, yPosIn, this.hasResetButton() ? widthIn - (heightIn / 2) - heightIn : widthIn, heightIn, this.caption, true, CosmosOptionInstance.noTooltip(), (button) -> {  }, this.narration);
	}
	
	@Override
	public AbstractWidget createResetButton(int xPosIn, int yPosIn, int widthIn, int heightIn) {
		return new BlankTileButton(xPosIn + widthIn - heightIn, yPosIn, heightIn, heightIn, this.buttonText, true, CosmosOptionInstance.cachedConstantTooltip(200, this.extraButtonText), this.onPressFunction, this.narration);
	}
	
	@OnlyIn(Dist.CLIENT)
	public class BlankTileButton extends Button {

		protected final CosmosOptionInstance.TooltipSupplier<Boolean> tooltip;
		public boolean doRenderBackground;
		
		public BlankTileButton(int xPosIn, int yPosIn, int widthIn, int heightIn, Component titleMessageIn, boolean doRenderBackgoundIn, CosmosOptionInstance.TooltipSupplierFactory<Boolean> tooltipIn, Button.OnPress function, Button.CreateNarration narration) {
			super(xPosIn, yPosIn, widthIn, heightIn, titleMessageIn, function, narration);
			
			this.doRenderBackground = doRenderBackgoundIn;
			this.tooltip = tooltipIn.apply(Minecraft.getInstance());
		}
		
		@Override
		public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
			super.renderWidget(graphics, mouseX, mouseY, partialTicks);
		}
	}
}