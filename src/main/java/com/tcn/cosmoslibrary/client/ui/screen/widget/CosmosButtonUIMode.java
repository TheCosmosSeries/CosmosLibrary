package com.tcn.cosmoslibrary.client.ui.screen.widget;

import java.util.function.Supplier;

import com.tcn.cosmoslibrary.CosmosReference;
import com.tcn.cosmoslibrary.client.ui.CosmosUISystem;
import com.tcn.cosmoslibrary.common.enums.EnumUIMode;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * @author TheCosmicNebula_
 */
@OnlyIn(Dist.CLIENT)
public class CosmosButtonUIMode extends Button {
	
	private int[] index = new int[] { 0, 12, 24, 36 };
	
	private final ResourceLocation TEXTURE = CosmosReference.RESOURCE.BASE.BUTTON_UI; 
	private EnumUIMode buttonType;
	protected int width;
	protected int height;
	public int x;
	public int y;
	protected boolean isHovered;
	public boolean active = true;
	public boolean visible = true;

	public CosmosButtonUIMode(EnumUIMode typeIn, int x, int y, boolean small, boolean enabled, boolean visible, Component title, Button.OnPress pressedAction) {
		this(typeIn, x, y, small ? 8 : 12 , enabled, visible, title, pressedAction, new Button.CreateNarration() {
			
			@Override
			public MutableComponent createNarrationMessage(Supplier<MutableComponent> narration) {
				return ComponentHelper.empty();
			}
		});
	}
	
	public CosmosButtonUIMode(EnumUIMode typeIn, int x, int y, int size, boolean enabled, boolean visible, Component title, Button.OnPress pressedAction, Button.CreateNarration narration) {
		super(x, y, size, size, title, pressedAction, narration);
		this.buttonType = typeIn;
		
		this.x = x;
		this.y = y;
		this.width = size;
		this.height = size;

		this.active = enabled;
		this.visible = visible;
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
		if (this.active) {
			this.onPress.onPress(this);
		}
	}

	@Override
	public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			if (this.buttonType != null) {
				if (this.width == 12 && this.height == 12) {
					CosmosUISystem.Setup.setTextureWithColourAlpha(graphics.pose(), TEXTURE, new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
					
					this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
					int i = this.getHoverState(this.isHovered);
		
				    if (this.buttonType.equals(EnumUIMode.DARK)) {
				    	graphics.blit(TEXTURE, this.x, this.y, index[i], 0, this.width, this.height);
				    } else {
				    	graphics.blit(TEXTURE, this.x, this.y, index[i + 2], 0, this.width, this.height);
				    }
				} else if (this.width == 8 && this.height == 8) {
					CosmosUISystem.Setup.setTextureWithColourAlpha(graphics.pose(), TEXTURE, new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
					
					this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
					int i = this.getHoverState(this.isHovered);
		
				    if (this.buttonType.equals(EnumUIMode.DARK)) {
				    	graphics.blit(TEXTURE, this.x, this.y, index[i], 12, this.width, this.height);
				    } else {
				    	graphics.blit(TEXTURE, this.x, this.y, index[i + 2], 12, this.width, this.height);
				    }
				}
			}
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
}