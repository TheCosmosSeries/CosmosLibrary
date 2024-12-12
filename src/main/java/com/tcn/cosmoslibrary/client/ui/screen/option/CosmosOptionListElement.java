package com.tcn.cosmoslibrary.client.ui.screen.option;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.serialization.Codec;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class CosmosOptionListElement extends CosmosOptionInstance<String> {
	
	public Button.OnPress onPressFunction;
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
	public AbstractWidget createButton(CosmosOptions options, int xPosIn, int yPosIn, int widthIn, int heightIn) {
		return new BlankTileButton(xPosIn, yPosIn, this.hasResetButton() ? widthIn - (heightIn / 2) - heightIn : widthIn, heightIn, this.caption, true, CosmosOptionInstance.noTooltip(), (button) -> {  }, this.narration);
	}
	
	@Override
	public AbstractWidget createResetButton(CosmosOptions options, int xPosIn, int yPosIn, int widthIn, int heightIn) {
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
			Minecraft minecraft = Minecraft.getInstance();
			Font fontrenderer = minecraft.font;
			
			if (this.doRenderBackground) {
				RenderSystem.setShader(GameRenderer::getPositionTexShader);
				RenderSystem.setShaderTexture(0, SPRITES.enabled());
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
				int i = this.getTextureY();
				
				RenderSystem.enableBlend();
				RenderSystem.defaultBlendFunc();
				RenderSystem.enableDepthTest();
				
				graphics.blit(SPRITES.enabled(), this.getX(), this.getY(), 0, 46 + i * 20, this.width / 2, this.height);
				graphics.blit(SPRITES.enabled(), this.getX() + this.width / 2, this.getY(), 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
				//this.renderBg(graphics, minecraft, mouseX, mouseY);
			}
			
			int j = getFGColor();
			graphics.drawCenteredString(fontrenderer, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
		}

		private int getTextureY() {
			int i = 1;
			if (!this.active) {
				i = 0;
			} else if (this.isHoveredOrFocused()) {
				i = 2;
			}

			return 46 + i * 20;
		}
	}
}