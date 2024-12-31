package com.tcn.cosmoslibrary.client.ui.screen;

import com.tcn.cosmoslibrary.client.container.CosmosContainerMenuBlockEntity;
import com.tcn.cosmoslibrary.client.ui.CosmosUISystem;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosButtonBase;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CosmosScreenBlockEntity<J extends CosmosContainerMenuBlockEntity> extends AbstractContainerScreen<J> {
	
	protected ResourceLocation TEXTURE;
	protected ResourceLocation DUAL_TEXTURE;
	
	private int[] screenCoords;
	
	private boolean hasDualScreen = false;
	private int[] dualScreenIndex;
	
	private boolean renderTitleLabel = true;
	private boolean titleLabelCentered = false;
	private boolean renderInventoryLabel = true;

	public CosmosScreenBlockEntity(J containerIn, Inventory playerInventoryIn, Component titleIn) {
		super(containerIn, playerInventoryIn, titleIn);
	}
	
	@Override
	protected void init() {
		this.setScreenCoords(CosmosUISystem.Init.getScreenCoords(this, this.imageWidth, this.imageHeight));
		super.init();
		this.addButtons();
	}
	
	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBg(graphics, partialTicks, mouseX, mouseY);
		super.render(graphics, mouseX, mouseY, partialTicks);

		this.renderComponents(graphics, mouseX, mouseY, partialTicks);
		this.renderComponentHoverEffect(graphics, Style.EMPTY, mouseX, mouseY);
		this.renderTooltip(graphics, mouseX, mouseY);
	}
	
	protected void renderComponents(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) { 
		this.addButtons();
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
		CosmosUISystem.Render.renderStaticElement(graphics, this.screenCoords, 0, 0, 0, 0, Mth.clamp(this.imageWidth, 0, 256), this.imageHeight, TEXTURE);
		
		if (this.hasDualScreen) {
			if(this.dualScreenIndex != null && this.DUAL_TEXTURE != null && this.DUAL_TEXTURE != null) {
				CosmosUISystem.Render.renderStaticElement(graphics, this.screenCoords, this.dualScreenIndex[0], this.dualScreenIndex[1], 0, 0, Mth.clamp(256 + this.dualScreenIndex[2], 0, 256), this.dualScreenIndex[3], DUAL_TEXTURE);
			}
		}
	}

	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		if (this.renderTitleLabel) {
			if (this.titleLabelCentered) {				
				graphics.drawCenteredString(this.font, this.title, this.imageWidth / 2 + this.titleLabelX, this.titleLabelY, CosmosUISystem.DEFAULT_COLOUR_FONT_LIST);
			} else {
				graphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, CosmosUISystem.DEFAULT_COLOUR_FONT_LIST);
			}
		}
		
		if (this.renderInventoryLabel) {
			graphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, CosmosUISystem.DEFAULT_COLOUR_FONT_LIST);
		}
	}
	
	public void renderComponentHoverEffect(GuiGraphics graphics, Style style, int mouseX, int mouseY) { }
	
	protected void addButtons() { 
		this.clearWidgets();
	}

	protected void clickButton(Button buttonIn, boolean isLeftClick) { }
	
	protected void setImageDims(int widthIn, int heightIn) {
		this.imageWidth = widthIn;
		this.imageHeight = heightIn;
	}

	protected void setTexture(ResourceLocation textureIn) {
		this.TEXTURE = textureIn;
	}
	
	protected void setDualScreen() {
		this.hasDualScreen = true;
	}
	
	protected void setDualScreenIndex(int posX, int posY, int width, int height) {
		this.setDualScreen();
		this.dualScreenIndex = new int[] { posX, posY, width, height };
	}

	protected void setDual(ResourceLocation textureIn) {
		this.setDualScreen();
		this.DUAL_TEXTURE = textureIn;
	}
	
	protected void setNoTitleLabel() {
		this.renderTitleLabel = false;
	}

	protected void setTitleLabelDims(int posX, int posY) {
		this.titleLabelX = posX;
		this.titleLabelY = posY;
	}

	protected void setTitleLabelDimsCentered(int posXOffset, int posY) {
		this.titleLabelX = posXOffset;
		this.titleLabelY = posY;
		this.titleLabelCentered = true;
	}

	protected void setNoInventoryLabel() {
		this.renderInventoryLabel = false;
	}

	protected void setInventoryLabelDims(int posX, int posY) {
		this.inventoryLabelX = posX;
		this.inventoryLabelY = posY;
	}
	
	protected void setScreenCoords(int[] coordsIn) {
		this.screenCoords = coordsIn;
	}
	
	protected int[] getScreenCoords() {
		return this.screenCoords;
	}
	
	public BlockEntity getBlockEntity() {
		J container = this.menu;
		Level level = container.getLevel();
		BlockPos pos = container.getBlockPos();
		
		return level.getBlockEntity(pos);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		for (GuiEventListener list : this.children()) {
			if (list instanceof CosmosButtonBase button) {
				if (button.isMouseOver(mouseX, mouseY) && button.isActive() && button.visible) {
					if (mouseButton == 1) {
						button.onClick(false);
					} else if (mouseButton == 0) {
						button.onClick(true);
					}
				}
			}
		}
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}
}