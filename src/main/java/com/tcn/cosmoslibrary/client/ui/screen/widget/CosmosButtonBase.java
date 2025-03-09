package com.tcn.cosmoslibrary.client.ui.screen.widget;

import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

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
	
	protected boolean active = true;
	protected boolean visible = true;
	
	protected final CosmosButtonBase.OnClick onClick;

	public CosmosButtonBase(int x, int y, int width, int height, boolean enabled, boolean visible, Component title) {
		this(x, y, width, height, enabled, visible, title, (msg) -> { return ComponentHelper.empty(); });
	}

	public CosmosButtonBase(int x, int y, int width, int height, boolean enabled, boolean visible, Component title, Button.CreateNarration narration) {
		this(x, y, width, height, enabled, visible, title, (button, isLeftClick) -> {}, narration);
	}

	public CosmosButtonBase(int x, int y, int width, int height, boolean enabled, boolean visible, Component title, CosmosButtonBase.OnClick clickedAction, Button.CreateNarration narration) {
		super(x, y, width, height, title, (button) -> { clickedAction.onClick(button, true); }, narration);
				
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.active = enabled;
		this.visible = visible;
		
		this.onClick = clickedAction;
	}

	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		if (!this.active || !this.visible) {
			return false;
		}
		
		return super.isMouseOver(mouseX, mouseY);
	}

	@Override
	public void onPress() { }
	
	@Override
	public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) { }

	@Override
	public boolean isActive() {
		return this.active;
	}
	
	protected int getHoverState(boolean mouseOver) {
		return !this.isActive() ? 2 : mouseOver ? 1 : 0;
	}

	public void onClick(boolean isLeftClick) {
		if (this.isActive() && this.isVisible()) {
			this.onClick.onClick(this, isLeftClick);
			
			if (!isLeftClick) {
				this.playDownSound(Minecraft.getInstance().getSoundManager());
			}
		}
	}
	
	public interface OnClick {
		void onClick(Button buttonIn, boolean isLeftClick);
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	
}