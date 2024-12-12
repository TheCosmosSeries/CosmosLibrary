package com.tcn.cosmoslibrary.client.ui.screen.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * 
 * @author TheCosmicNebula_
 * 
 */
@OnlyIn(Dist.CLIENT)
public class CosmosButtonBase extends Button {
	
	protected int width;
	protected int height;
	public int x;
	public int y;
	
	protected boolean isHovered;
	
	public boolean active = true;
	public boolean visible = true;
	
	protected final CosmosButtonBase.OnClick onClick;

	public CosmosButtonBase(int x, int y, int width, int height, boolean enabled, boolean visible, Component title, CosmosButtonBase.OnClick clickedAction, Button.CreateNarration narration) {
		super(x, y, width, height, title, (button) -> {clickedAction.onClick(button, true);}, narration);
				
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.active = enabled;
		this.visible = visible;
		
		this.onClick = clickedAction;
	}

	public CosmosButtonBase(int x, int y, int width, int height, boolean enabled, boolean visible, Component title, Button.CreateNarration narration) {
		super(x, y, width, height, title, (button) -> {}, narration);
				
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.active = enabled;
		this.visible = visible;
		
		this.onClick = (button, isLeftClick) -> {};
	}

	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		if (!this.active || !this.visible) {
			return false;
		}
		
		return super.isMouseOver(mouseX, mouseY);
	}

	@Override
	public void onPress() {
		if (this.active || this.visible) {
			//this.onClick.onClick(this, true);
		}
	}
	
	public void onClick(boolean isLeftClick) {
		if (this.active && this.visible) {
			this.onClick.onClick(this, isLeftClick);
			
			if (!isLeftClick) {
				this.playDownSound(Minecraft.getInstance().getSoundManager());
			}
		}
	}
	
	@Override
	public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			//super.renderWidget(graphics, mouseX, mouseY, partialTicks);
		}
	}
	
	protected int getHoverState(boolean mouseOver) {
		int i = 0;

		if (!this.active) {
			i = 2;
		} else if (mouseOver) {
			i = 1;
		}
		return i;
	}
	
	public interface OnClick {
		void onClick(Button buttonIn, boolean isLeftClick);
	}
	
}