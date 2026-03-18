package com.tcn.cosmoslibrary.client.ui.screen;

import java.util.Arrays;
import java.util.UUID;

import com.tcn.cosmoslibrary.client.container.CosmosContainerMenuItemStack;
import com.tcn.cosmoslibrary.client.ui.CosmosUISystem;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosButtonUIMode;
import com.tcn.cosmoslibrary.common.enums.EnumUIMode;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class CosmosScreenItemStackUI<J extends CosmosContainerMenuItemStack> extends AbstractContainerScreen<J> {
	
	protected CosmosButtonUIMode uiModeButton; private int[] uiModeButtonIndex;
	private int[] screenCoords;
	
	private UUID playerUUID;
	private ItemStack stack;

	public CosmosScreenItemStackUI(J menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
		
		this.stack = menu.getStack();
		this.playerUUID = menu.getPlayer().getUUID();
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	protected void init() {
		this.setScreenCoords(CosmosUISystem.Init.getScreenCoords(this, this.imageWidth, this.imageHeight));
		super.init();
		this.addButtons();
	}

	@Override
	public void containerTick() {
		super.containerTick();
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(graphics, mouseX, mouseY, partialTicks);
		super.render(graphics, mouseX, mouseY, partialTicks);

		this.addButtons();
		
		this.renderComponentHoverEffect(graphics, Style.EMPTY, mouseX, mouseY);
		this.renderTooltip(graphics, mouseX, mouseY);
	}
	
	@Override
	protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) { }

	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		int textColour = this.getUIMode().equals(EnumUIMode.DARK) ? CosmosUISystem.DEFAULT_COLOUR_FONT_LIST : ComponentColour.BLACK.dec();
		
		graphics.drawString(this.font, this.title.getString(), this.titleLabelX, this.titleLabelY, textColour, false);
		graphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, textColour, false);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double directionX, double directionY) {
		return super.mouseScrolled(mouseX, mouseY, directionX, directionY);
	}

	@Override
	public boolean keyPressed(int keyCode, int mouseX, int mouseY) {
		return super.keyPressed(keyCode, mouseX, mouseY);
	}

	@Override
	public boolean charTyped(char charIn, int p_98522_) {
		return super.charTyped(charIn, p_98522_);
	}
	
	@Override
	public void resize(Minecraft mc, int width, int height) {
		super.resize(mc, width, height);
	}
	
	public void renderComponentHoverEffect(GuiGraphics graphics, Style style, int mouseX, int mouseY) {
		if (this.uiModeButton.isMouseOver(mouseX, mouseY)) {
			Component[] comp = new Component[] { 
				ComponentHelper.style(ComponentColour.WHITE, "cosmoslibrary.gui.ui_mode.info"),
				ComponentHelper.style(ComponentColour.GRAY, "cosmoslibrary.gui.ui_mode.value").append(this.getUIMode().getColouredComp())
			};
			graphics.renderComponentTooltip(this.font, Arrays.asList(comp), mouseX, mouseY);
		}
	}
	
	protected void addButtons() {
		this.clearWidgets();
		
		this.uiModeButton = this.addRenderableWidget(new CosmosButtonUIMode(this.getUIMode(), this.getScreenCoords()[0] + uiModeButtonIndex[0], this.getScreenCoords()[1] +  + uiModeButtonIndex[1], false, true, true, ComponentHelper.empty(), (button) -> { this.changeUIMode(); } ));
	}
	
	protected void pushButton(Button button) { }
	
	protected void setImageDims(int widthIn, int heightIn) {
		this.imageWidth = widthIn;
		this.imageHeight = heightIn;
	}
	
	protected void setTitleLabelDims(int posX, int posY) {
		this.titleLabelX = posX;
		this.titleLabelY = posY;
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

	protected void setUIModeButtonIndex(int posX, int posY) {
		this.uiModeButtonIndex = new int[] { posX, posY };
	}
	
	protected EnumUIMode getUIMode() {
		return EnumUIMode.DARK;
	}

	protected void changeUIMode() { }

	protected ItemStack getStack() {
		return this.stack;
	}
	
	protected UUID getPlayerUUID() {
		return this.playerUUID;
	}
}