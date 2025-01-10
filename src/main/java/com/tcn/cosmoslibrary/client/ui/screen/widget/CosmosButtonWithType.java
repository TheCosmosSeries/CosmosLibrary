package com.tcn.cosmoslibrary.client.ui.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tcn.cosmoslibrary.CosmosReference.RESOURCE.BASE;
import com.tcn.cosmoslibrary.CosmosReference.RESOURCE.INFO;
import com.tcn.cosmoslibrary.client.ui.CosmosUISystem;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CosmosButtonWithType extends CosmosButtonBase {
	
	public enum TYPE {
		ICON (0, "icon", BASE.BUTTON_ICON_PATH, BASE.BUTTON_ICON_PATH_ALT),
		GENERAL (1, "general", BASE.BUTTON_GENERAL_PATH, BASE.BUTTON_GENERAL_PATH_ALT),
		ENERGY (2, "energy", BASE.BUTTON_ENERGY_PATH, BASE.BUTTON_ENERGY_PATH_ALT),
		FLUID (3, "fluid", BASE.BUTTON_FLUID_PATH, BASE.BUTTON_FLUID_PATH_ALT),
		ITEM (4, "item", BASE.BUTTON_ITEM_PATH, BASE.BUTTON_ITEM_PATH_ALT),
		STORAGE (5, "storage", BASE.BUTTON_STORAGE_PATH, BASE.BUTTON_STORAGE_PATH_ALT);
		
		private int index;
		private String name;
		private final ResourceLocation textureNormal;
		private final ResourceLocation textureAlt;
		
		TYPE (int index, String name, ResourceLocation textureNormalIn, ResourceLocation textureAltIn) {
			this.index = index;
			this.name = name;
			this.textureNormal = textureNormalIn;
			this.textureAlt = textureAltIn;
		}
		
		public int getIndex() {
			return this.index;
		}
		
		public String getName() {
			return this.name;
		}
		
		public ResourceLocation getButtonTexture(boolean alt) {
			if (!alt) {
				return this.textureNormal;
			} else {
				return this.textureAlt;
			}
		}
	}
	
	private TYPE buttonType;
	private int identifier;

	public CosmosButtonWithType(TYPE typeIn, int x, int y, boolean enabled, boolean visible, int identifier, Component title, CosmosButtonBase.OnClick clickedAction) {
		this(typeIn, x, y, 20, enabled, visible, identifier, title, clickedAction);
	}

	public CosmosButtonWithType(TYPE typeIn, int x, int y, int size, boolean enabled, boolean visible, int identifier, Component title, CosmosButtonBase.OnClick clickedAction) {
		super(x, y, size, size, enabled, visible, title, clickedAction, (msg) -> ComponentHelper.empty());
		
		this.buttonType = typeIn;
		this.identifier = identifier;
	}

	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		return this.active ? super.isMouseOver(mouseX, mouseY) : false;
	}

	@Override
	public void onPress() {
		if (this.visible && this.active) {
			//this.onClick.onClick(this, true);
		}
	}

	@Override
	public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			if (this.buttonType != null) {
				CosmosUISystem.Setup.setTextureWithColourAlpha(graphics.pose(), this.buttonType.getButtonTexture(false), new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
				
				this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
				int i = this.getHoverState(this.isHovered);
	
				if (this.identifier >= 0 && this.identifier <= 23) {
					if (this.identifier >= 0 && this.identifier <= 5) {
						if (this.width == 20 && this.height == 20) {
							graphics.blit(this.buttonType.getButtonTexture(false), this.x, this.y, INFO.BUTTON_STATE_X[identifier], INFO.BUTTON_STATE_Y[i], this.width, this.height);
						} else if (this.width == 18 && this.height == 18) {
							graphics.blit(this.buttonType.getButtonTexture(false), this.x, this.y, INFO.BUTTON_STATE_X_SMALL[identifier], INFO.BUTTON_STATE_Y_SMALL[i], this.width, this.height);
						}
					} else if (this.identifier > 5 && this.identifier <= 11) {
						if (this.width == 20 && this.height == 20) {
							graphics.blit(this.buttonType.getButtonTexture(false), this.x, this.y, INFO.BUTTON_STATE_X[identifier - 6], INFO.BUTTON_STATE_Y[i + 3], this.width, this.height);
						} else if (this.width == 18 && this.height == 18) {
							graphics.blit(this.buttonType.getButtonTexture(false), this.x, this.y, INFO.BUTTON_STATE_X_SMALL[identifier - 6], INFO.BUTTON_STATE_Y_SMALL[i + 3], this.width, this.height);
						}
					} else if (this.identifier > 11 && this.identifier <= 17) {
						if (this.width == 20 && this.height == 20) {
							graphics.blit(this.buttonType.getButtonTexture(false), this.x, this.y, INFO.BUTTON_STATE_X[identifier - 12], INFO.BUTTON_STATE_Y[i + 6], this.width, this.height);
						} else if (this.width == 18 && this.height == 18) {
							graphics.blit(this.buttonType.getButtonTexture(false), this.x, this.y, INFO.BUTTON_STATE_X_SMALL[identifier - 12], INFO.BUTTON_STATE_Y_SMALL[i + 6], this.width, this.height);
						}
					} else if (this.identifier > 17 && this.identifier <= 23) {
						if (this.width == 20 && this.height == 20) {
							graphics.blit(this.buttonType.getButtonTexture(false), this.x, this.y, INFO.BUTTON_STATE_X[identifier - 18], INFO.BUTTON_STATE_Y[i + 9], this.width, this.height);
						} else if (this.width == 18 && this.height == 18) {
							graphics.blit(this.buttonType.getButtonTexture(false), this.x, this.y, INFO.BUTTON_STATE_X_SMALL[identifier - 18], INFO.BUTTON_STATE_Y_SMALL[i + 9], this.width, this.height);
						}
					} 
				}
				
				else if (this.identifier > 23 && this.identifier <= 47) {
					RenderSystem.setShaderTexture(0, this.buttonType.getButtonTexture(true));
					
					if (this.identifier > 23 && this.identifier <= 29) {
						if (this.width == 20 && this.height == 20) {
							graphics.blit(this.buttonType.getButtonTexture(true), this.x, this.y, INFO.BUTTON_STATE_X[identifier - 24], INFO.BUTTON_STATE_Y[i], this.width, this.height);
						} else if (this.width == 18 && this.height == 18) {
							graphics.blit(this.buttonType.getButtonTexture(true), this.x, this.y, INFO.BUTTON_STATE_X_SMALL[identifier - 24], INFO.BUTTON_STATE_Y_SMALL[i], this.width, this.height);
						}
					} else if (this.identifier > 29 && this.identifier <= 35) {
						if (this.width == 20 && this.height == 20) {
							graphics.blit(this.buttonType.getButtonTexture(true), this.x, this.y, INFO.BUTTON_STATE_X[identifier - 30], INFO.BUTTON_STATE_Y[i + 3], this.width, this.height);
						} else if (this.width == 18 && this.height == 18) {
							graphics.blit(this.buttonType.getButtonTexture(true), this.x, this.y, INFO.BUTTON_STATE_X_SMALL[identifier - 30], INFO.BUTTON_STATE_Y_SMALL[i + 3], this.width, this.height);
						}
					} else if (this.identifier > 35 && this.identifier <= 41) {
						if (this.width == 20 && this.height == 20) {
							graphics.blit(this.buttonType.getButtonTexture(true), this.x, this.y, INFO.BUTTON_STATE_X[identifier - 36], INFO.BUTTON_STATE_Y[i + 6], this.width, this.height);
						} else if (this.width == 18 && this.height == 18) {
							graphics.blit(this.buttonType.getButtonTexture(true), this.x, this.y, INFO.BUTTON_STATE_X_SMALL[identifier - 36], INFO.BUTTON_STATE_Y_SMALL[i + 6], this.width, this.height);
						}
					} else if (this.identifier > 41 && this.identifier <= 47) {
						if (this.width == 20 && this.height == 20) {
							graphics.blit(this.buttonType.getButtonTexture(true), this.x, this.y, INFO.BUTTON_STATE_X[identifier - 42], INFO.BUTTON_STATE_Y[i + 9], this.width, this.height);
						} else if (this.width == 18 && this.height == 18) {
							graphics.blit(this.buttonType.getButtonTexture(true), this.x, this.y, INFO.BUTTON_STATE_X_SMALL[identifier - 42], INFO.BUTTON_STATE_Y_SMALL[i + 9], this.width, this.height);
						}
					}
				}
			}
		}
	}
	
	@Override
	protected int getHoverState(boolean mouseOver) {
		return !this.active ? 2 : mouseOver ? 1 : 0;
	}
}