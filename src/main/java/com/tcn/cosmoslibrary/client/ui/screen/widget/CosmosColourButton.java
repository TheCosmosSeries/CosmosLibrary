package com.tcn.cosmoslibrary.client.ui.screen.widget;

import com.tcn.cosmoslibrary.CosmosReference;
import com.tcn.cosmoslibrary.CosmosReference.RESOURCE.INFO;
import com.tcn.cosmoslibrary.client.ui.CosmosUISystem;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CosmosColourButton extends CosmosButtonBase {
	
	private ComponentColour colour = ComponentColour.EMPTY;

	public CosmosColourButton(ComponentColour startingColourIn, int x, int y, boolean enabled, boolean visible, Component title, CosmosButtonBase.OnClick clickedAction) {
		this(startingColourIn, x, y, 20, enabled, visible, title, clickedAction);
	}
	
	public CosmosColourButton(ComponentColour startingColourIn, int x, int y, int size, boolean enabled, boolean visible, Component title, CosmosButtonBase.OnClick clickedAction) {
		this(startingColourIn, x, y, size, size, enabled, visible, title, clickedAction);
	}

	public CosmosColourButton(ComponentColour startingColourIn, int x, int y, int sizeX, int sizeY, boolean enabled, boolean visible, Component title, CosmosButtonBase.OnClick clickedAction) {
		super(x, y, sizeX, sizeY, enabled, visible, title, clickedAction, (msg) -> ComponentHelper.empty());
		this.colour = startingColourIn;
	}

	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		if (!this.active) {
			return false;
		}
		
		return super.isMouseOver(mouseX, mouseY);
	}

	@Override
	public void onPress() {
		if (this.visible && this.active) {
			this.onPress.onPress(this);
		}
	}

	@Override
	public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			CosmosUISystem.Setup.setTextureWithColourAlpha(graphics.pose(), CosmosReference.RESOURCE.BASE.BUTTON_COLOUR, new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
			
			this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			int i = this.getHoverState(this.isHovered);
	
			if (this.width == 20 && this.height == 20) {
				graphics.blit(CosmosReference.RESOURCE.BASE.BUTTON_COLOUR, this.x, this.y, INFO.BUTTON_STATE_X[0], INFO.BUTTON_STATE_Y[i], this.width, this.height);
			} else if (this.width == 18 && this.height == 18) {
				graphics.blit(CosmosReference.RESOURCE.BASE.BUTTON_COLOUR, this.x, this.y, INFO.BUTTON_STATE_X_SMALL[0], INFO.BUTTON_STATE_Y_SMALL[i], this.width, this.height);
			}
			
			if (!this.colour.equals(ComponentColour.EMPTY)) {
				CosmosUISystem.Setup.setTextureColour(this.colour.equals(ComponentColour.POCKET_PURPLE) ? ComponentColour.POCKET_PURPLE_LIGHT : this.colour);
				
				if (this.width == 20 && this.height == 20) {
					graphics.blit(CosmosReference.RESOURCE.BASE.BUTTON_COLOUR, this.x, this.y, INFO.BUTTON_STATE_X[1], INFO.BUTTON_STATE_Y[i], this.width, this.height);
				} else if (this.width == 18 && this.height == 18) {
					graphics.blit(CosmosReference.RESOURCE.BASE.BUTTON_COLOUR, this.x, this.y, INFO.BUTTON_STATE_X_SMALL[1], INFO.BUTTON_STATE_Y_SMALL[i], this.width, this.height);
				}
			}
			
			CosmosUISystem.Setup.setTextureColour(ComponentColour.WHITE);
		}
	}
	
	@Override
	protected int getHoverState(boolean mouseOver) {
		return !this.active ? 2 : mouseOver ? 1 : 0;
	}
	
	public ComponentColour getColour() {
		return this.colour;
	}
}