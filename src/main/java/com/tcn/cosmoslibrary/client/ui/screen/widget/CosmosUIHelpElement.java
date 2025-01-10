package com.tcn.cosmoslibrary.client.ui.screen.widget;

import java.util.Arrays;
import java.util.List;

import com.tcn.cosmoslibrary.CosmosReference;
import com.tcn.cosmoslibrary.client.ui.CosmosUISystem;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class CosmosUIHelpElement extends AbstractWidget implements IHelpModeIgnore {
	
	private Component[] desc;
	public boolean visible;
	private ComponentColour colour;

	public CosmosUIHelpElement(int xIn, int yIn, int widthIn, int heightIn, Component... descIn) {
		this(xIn, yIn, widthIn, heightIn, ComponentColour.GREEN, descIn);
	}

	public CosmosUIHelpElement(int xIn, int yIn, int widthIn, int heightIn, ComponentColour colourIn, Component... descIn) {
		super(xIn, yIn, widthIn, heightIn, descIn[0]);
		
		this.colour = colourIn;
		this.desc = descIn;
	}

	@Override
	public void updateWidgetNarration(NarrationElementOutput output) { }
	
	@Override
	public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		this.setHovered(mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height);
		
		if (this.isHovered) {
			CosmosUISystem.Setup.setTextureWithColourAlpha(graphics.pose(), CosmosReference.RESOURCE.BASE.UI_LIGHTEN_HIGHLIGHT, new float[] { 1.0F, 1.0F, 1.0F, 0.6F });
			graphics.blit(CosmosReference.RESOURCE.BASE.UI_LIGHTEN_HIGHLIGHT, this.getX(), this.getY(), 0, 0, this.width, this.height);

			CosmosUISystem.Setup.setTextureColour(this.colour);
			graphics.blit(CosmosReference.RESOURCE.BASE.UI_LIGHTEN_HIGHLIGHT, this.getX(), this.getY(), 0, 0, this.width, 1);
			graphics.blit(CosmosReference.RESOURCE.BASE.UI_LIGHTEN_HIGHLIGHT, this.getX(), this.getY() + this.height - 1, 0, 0, this.width, 1);
			graphics.blit(CosmosReference.RESOURCE.BASE.UI_LIGHTEN_HIGHLIGHT, this.getX(), this.getY() + 1, 0, 0, 1, this.height - 2);
			graphics.blit(CosmosReference.RESOURCE.BASE.UI_LIGHTEN_HIGHLIGHT, this.getX() + this.width - 1, this.getY() + 1, 0, 0, 1, this.height - 2);
		} else {
			CosmosUISystem.Setup.setTextureWithColourAlpha(graphics.pose(), CosmosReference.RESOURCE.BASE.UI_LIGHTEN, new float[] { 1.0F, 1.0F, 1.0F, 0.4F });
			graphics.blit(CosmosReference.RESOURCE.BASE.UI_LIGHTEN, this.getX(), this.getY(), 0, 0, this.width, this.height);
			
			CosmosUISystem.Setup.setTextureColour(this.colour);
			graphics.blit(CosmosReference.RESOURCE.BASE.UI_LIGHTEN, this.getX(), this.getY(), 0, 0, this.width, 1);
			graphics.blit(CosmosReference.RESOURCE.BASE.UI_LIGHTEN, this.getX(), this.getY() + this.height - 1, 0, 0, this.width, 1);
			graphics.blit(CosmosReference.RESOURCE.BASE.UI_LIGHTEN, this.getX(), this.getY() + 1, 0, 0, 1, this.height - 2);
			graphics.blit(CosmosReference.RESOURCE.BASE.UI_LIGHTEN, this.getX() + this.width - 1, this.getY() + 1, 0, 0, 1, this.height - 2);
		}
	}
	
	public List<Component> getHoverElement() {
		return Arrays.asList(this.desc);
	}
		
	public CosmosUIHelpElement setVisible() {
		this.visible = true;
		return this;
	}

	public CosmosUIHelpElement setVisible(boolean valueIn) {
		this.visible = valueIn;
		return this;
	}
	
	public CosmosUIHelpElement setHidden() {
		this.visible = false;
		return this;
	}

	public CosmosUIHelpElement setHidden(boolean valueIn) {
		this.visible = valueIn;
		return this;
	}
	
	protected void setHovered(boolean valueIn) {
		this.isHovered = valueIn;
	}
	
	protected int getHoverState(boolean mouseOver) {
		return !this.active ? 2 : mouseOver ? 1 : 0;
	}
}
