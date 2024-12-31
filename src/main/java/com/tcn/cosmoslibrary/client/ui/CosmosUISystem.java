package com.tcn.cosmoslibrary.client.ui;

import java.util.Arrays;
import java.util.List;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.tcn.cosmoslibrary.CosmosReference;
import com.tcn.cosmoslibrary.common.enums.EnumUIMode;
import com.tcn.cosmoslibrary.common.interfaces.IEnergyEntity;
import com.tcn.cosmoslibrary.common.interfaces.blockentity.IBEUIMode;
import com.tcn.cosmoslibrary.common.interfaces.blockentity.IEnergyHolder;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper.Value;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.IFluidTank;

@OnlyIn(Dist.CLIENT)
public class CosmosUISystem {
	
	public static final int DEFAULT_COLOUR_BACKGROUND = 4210752;
	public static final int DEFAULT_COLOUR_FONT_LIST = 16777215;
	
	public static final int BLACK = 0x000000;
	public static final int WHITE = 0xFFFFFF;
	public static final int LIGHT_BLUE = 0x5882FA;
	public static final int BLUE = 0x0000FF;
	public static final int LIGHT_GREY = 0xA4A4A4;
	public static final int GREY = 0x424242;
	public static final int GREEN = 0x00FF00;
	public static final int DARK_GREEN = 0x0B610B;
	public static final int RED = 0xFF0000;
	public static final int YELLOW = 0xFFFF00;
	public static final int ORANGE = 0xFF8000;
	public static final int CYAN = 0x01A9DB;
	public static final int MAGENTA = 0xDF01D7;
	public static final int PURPLE = 0x8904B1;
	public static final int PINK = 0xFE2EC8;
	public static final int BROWN = 0x61210B;
	
	public static final float[] NORMAL_COLOUR = new float[] { 1.0F, 1.0F, 1.0F, 1.0F };
	
	public class Init {
		public static int[] getScreenCoords(Screen screen, int imageWidth, int imageHeight) {
			return new int[] { ((screen.width - imageWidth) / 2), ((screen.height - imageHeight) / 2) };
		}
		
		public static int[] getScreenCoords(AbstractContainerScreen<?> screen, int imageWidth, int imageHeight) {
			return new int[] { ((screen.width - imageWidth) / 2), ((screen.height - imageHeight) / 2) };
		}
	}
	
	public class Setup {
		public static void setTextureWithColour(PoseStack poseStack, ResourceLocation textureIn, ComponentColour colourIn) {
			setTexture(poseStack, textureIn);
			setTextureColour(colourIn);
		}
	
		public static void setTextureWithColour(PoseStack poseStack, ResourceLocation textureIn, float[] colourIn) {
			setTextureWithColour(poseStack, textureIn, colourIn[0], colourIn[1], colourIn[2]);
		}
	
		public static void setTextureWithColour(PoseStack poseStack, ResourceLocation textureIn, float redIn, float greenIn, float blueIn) {
			setTexture(poseStack, textureIn);
			setTextureColour(redIn, greenIn, blueIn);
		}
	
		public static void setTextureWithColourAlpha(PoseStack poseStack, ResourceLocation textureIn, float[] colourIn) {
			setTextureWithColourAlpha(poseStack, textureIn, colourIn[0], colourIn[1], colourIn[2], colourIn[3]);
		}
	
		public static void setTextureWithColourAlpha(PoseStack poseStack, ResourceLocation textureIn, float redIn, float greenIn, float blueIn, float alphaIn) {
			setTexture(poseStack, textureIn);
			setTextureColour(redIn, greenIn, blueIn, alphaIn);
			enableAlpha();
		}
		
		public static void setTexture(PoseStack poseStack, ResourceLocation textureIn) {
			setTextureColour(NORMAL_COLOUR);
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, textureIn);
		}
	
		public static void nullTexture(PoseStack poseStack, ResourceLocation textureIn) {
			RenderSystem.deleteTexture(0);
		}
		
		public static void enableAlpha() {
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.enableDepthTest();
		}
		
		public static void disableAlpha() {
			RenderSystem.disableBlend();
			RenderSystem.disableDepthTest();
		}
		
		public static float[] colourToRGBA(ComponentColour colourIn, float alphaIn) {
			float[] rgb = ComponentColour.rgbFloatArray(colourIn);
			return new float[] { rgb[0], rgb[1], rgb[2], alphaIn };
		}
		
		public static float[] colourToRGBA(ComponentColour colourIn) {
			return colourToRGBA(colourIn, 1.0F);
		}
		
		public static void setTextureColour(ComponentColour colourIn) {
			setTextureColour(colourToRGBA(colourIn));
		}
	
		public static void setTextureColour(float redIn, float greenIn, float blueIn, float alphaIn) {
			setTextureColour(new float[] { redIn, greenIn, blueIn, alphaIn });
		}
	
		public static void setTextureColour(float redIn, float greenIn, float blueIn) {
			setTextureColour(new float[] { redIn, greenIn, blueIn, 1.0F });
		}
	
		public static void setTextureColour(float[] colourIn) {
			RenderSystem.setShaderColor(colourIn[0], colourIn[1], colourIn[2], colourIn[3]);
		}
		
		public static List<Component> getItemTooltip(Screen screen, ItemStack stackIn) {
			Minecraft minecraft = screen.getMinecraft();
			return stackIn.getTooltipLines(Item.TooltipContext.of(minecraft.player.level()), minecraft.player, minecraft.options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL);
		}
	}
	
	public class Render {
		public static void renderItemStack(Screen screen, Font font, GuiGraphics graphics, ItemStack itemStackIn, int[] screenCoords, int x, int y, int mouseX, int mouseY, boolean withTooltip) {
			int renderX = screenCoords[0] + x;
			int renderY = screenCoords[1] + y;
			
			graphics.renderItem(itemStackIn, renderX, renderY);
	
			if (withTooltip) {
				if (mouseX > renderX && mouseX < renderX + 16) {
					if (mouseY > renderY && mouseY < renderY + 16) {
						graphics.renderComponentTooltip(font, Setup.getItemTooltip(screen, itemStackIn), mouseX, mouseY);
					}
				}
			}
		}
		
		public static void renderEnergyDisplay(GuiGraphics graphics, ComponentColour colourIn, IEnergyHolder energyHolderIn, int[] screenCoords, int drawX, int drawY, int widthIn, int heightIn, boolean horizontal) {
			renderEnergyDisplay(graphics, colourIn, energyHolderIn.getEnergyStored(), energyHolderIn.getMaxEnergyStored(), screenCoords, drawX, drawY, widthIn, heightIn, horizontal);
		}
	
		public static void renderEnergyDisplay(GuiGraphics graphics, ComponentColour colourIn, IEnergyEntity energyHolderIn, int[] screenCoords, int drawX, int drawY, int widthIn, int heightIn, boolean horizontal) {
			renderEnergyDisplay(graphics, colourIn, energyHolderIn.getEnergyStored(), energyHolderIn.getMaxEnergyStored(), screenCoords, drawX, drawY, widthIn, heightIn, horizontal);
		}
	
		public static void renderEnergyDisplay(GuiGraphics graphics, ComponentColour colourIn, int energyIn, int maxEnergyIn, int[] screenCoords, int drawX, int drawY, int widthIn, int heightIn, boolean horizontal) {
			renderEnergyDisplay(graphics, colourIn, energyIn, maxEnergyIn, (energyIn / 1000) * (horizontal ? widthIn : heightIn) / (maxEnergyIn / 1000), screenCoords, drawX, drawY, widthIn, heightIn, horizontal);
		}

		public static void renderEnergyDisplay(GuiGraphics graphics, ComponentColour colourIn, int energyIn, int maxEnergyIn, int scaleIn, int[] screenCoords, int drawX, int drawY, int widthIn, int heightIn, boolean horizontal) {
			ResourceLocation location = horizontal ? CosmosReference.RESOURCE.BASE.UI_ENERGY_HORIZONTAL : CosmosReference.RESOURCE.BASE.UI_ENERGY_VERTICAL;
			Setup.setTexture(graphics.pose(), location);
			renderStaticElement(graphics, screenCoords, drawX, drawY, 0, 0, widthIn, heightIn, Setup.colourToRGBA(ComponentColour.LIGHT_GRAY), location);
			
			if (energyIn > 0) {				
				int adjustedScale = Math.max(1, (energyIn / 1000) * scaleIn / (maxEnergyIn / 1000));
				renderStaticElement(graphics, screenCoords, drawX, drawY + (horizontal ? 0 : heightIn - adjustedScale), horizontal ? (0 + adjustedScale) : 0, horizontal ? 0 : 255 - adjustedScale, horizontal ?  adjustedScale : widthIn, horizontal ? heightIn : adjustedScale, Setup.colourToRGBA(colourIn), location);
			}
			Setup.setTextureColour(NORMAL_COLOUR);
		}
		
		@Deprecated(forRemoval = true, since = "1.22")
		public static void renderSlot(GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int[] slot_location) {
			Setup.setTexture(graphics.pose(), CosmosReference.RESOURCE.BASE.GUI_SLOT_LOC);
			graphics.blit(CosmosReference.RESOURCE.BASE.GUI_SLOT_LOC, screenCoords[0] + drawX, screenCoords[1] + drawY, slot_location[0], slot_location[1], slot_location[2], slot_location[3]);
		}
		
		public static void renderBackground(Screen screen, GuiGraphics graphics, int[] screenCoords, ResourceLocation location) {
			renderBackground(screen, graphics, screenCoords, 0, 0, location);
		}
		
		public static void renderBackground(Screen screen, GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, ResourceLocation location) {
			renderStaticElement(graphics, screenCoords, drawX, drawY, 0, 0, screen.width, screen.height, location);
		}
		
		public static void renderFluidTank(GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, IFluidTank tank, int scaledIn, int scaleMax) {
			PoseStack poseStack = graphics.pose();
			
			if (tank.getFluidAmount() > 0) {
				Fluid renderFluid = tank.getFluid().getFluid();
				
				IClientFluidTypeExtensions props = IClientFluidTypeExtensions.of(renderFluid.defaultFluidState());
				TextureAtlas texture = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS);
				ResourceLocation textureLoc = props.getStillTexture();
				TextureAtlasSprite fluidTexture = texture.getSprite(textureLoc);
				
				int color = props.getTintColor();
				
				float r = ((color >> 16) & 0xFF) / 255f; // red
			    float g = ((color >> 8) & 0xFF) / 255f; // green
			    float b = ((color >> 0) & 0xFF) / 255f; // blue
			    float a = ((color >> 24) & 0xFF) / 255f; // alpha
			    
			    Setup.setTextureColour(r, g, b, a);
				
			    if (fluidTexture != null) {
			    	poseStack.pushPose();
			    	
			    	if (scaledIn > 0) {
			    		int limited = Mth.clamp(scaledIn, 0, 16);
			    		Extension.blit16(graphics, screenCoords[0] + drawX, screenCoords[1] + drawY + scaleMax - limited, 0, 16, limited, fluidTexture);
			    	}
			    	
					if (scaledIn > 16) {
						int scaled = scaledIn - 16;
						int limited = Mth.clamp(scaled, 0, 16);
						Extension.blit16(graphics, screenCoords[0] + drawX, screenCoords[1] + drawY + (scaleMax - 16) - limited, 0, 16, limited, fluidTexture);
					}
					
					if (scaledIn > 32) {
						int scaled = scaledIn - 32;
						int limited = Mth.clamp(scaled, 0, 16);
						Extension.blit16(graphics, screenCoords[0] + drawX, screenCoords[1] + drawY + (scaleMax - 32) - limited, 0, 16, limited, fluidTexture);
					}
					
					if (scaledIn > 48) {
						int scaled = scaledIn - 48;
						int limited = Mth.clamp(scaled, 0, 16);
						Extension.blit16(graphics, screenCoords[0] + drawX, screenCoords[1] + drawY + (scaleMax - 48) - limited, 0, 16, limited, fluidTexture);
					}
					
					if (scaledIn > 64) {
						int scaled = scaledIn - 64;
						int limited = Mth.clamp(scaled, 0, 16);
						Extension.blit16(graphics, screenCoords[0] + drawX, screenCoords[1] + drawY + (scaleMax - 64) - limited, 0, 16, limited, fluidTexture);
					}
					
					poseStack.popPose();
				}
			}
			Setup.setTextureColour(NORMAL_COLOUR);
		}
		
		public static void renderToolTipPowerProducer(Screen screen, GuiGraphics graphics, Font font, int[] screenCoords, int drawX, int drawY, int mouseX, int mouseY, int stored, int generation_rate, boolean producing) {
			if (Hovering.isHoveringPower(mouseX, mouseY, screenCoords[0] + drawX, screenCoords[1] + drawY)) {
				graphics.renderComponentTooltip(font, producing ? TextLists.generationText(stored, generation_rate) : TextLists.storedTextNo(stored), mouseX - screenCoords[0], mouseY - screenCoords[1]);
			}
		}
		
		public static void renderToolTipFluidLarge(Screen screen, GuiGraphics graphics, Font font, int[] screenCoords, int drawX, int drawY, int mouseX, int mouseY, IFluidTank tank) {
			if (Hovering.isHoveringFluidLarge(mouseX, mouseY, screenCoords[0] + drawX, screenCoords[1] + drawY)) {
				graphics.renderComponentTooltip(font, tank.getFluidAmount() > 0 ? TextLists.fluidText(tank.getFluid().getHoverName().toString(), tank.getFluidAmount(), tank.getCapacity()) : TextLists.fluidTextEmpty(), mouseX - screenCoords[0], mouseY - screenCoords[1]);
			}
		}
		
		public static void renderToolTipEmptyFluidButton(Screen screen, GuiGraphics graphics, Font font, int[] screenCoords, int drawX, int drawY, int mouseX, int mouseY, boolean has_fluid) {
			if (Hovering.isHoveringButtonStandard(mouseX, mouseY, screenCoords[0] + drawX, screenCoords[1] + drawY)) {
				if (has_fluid) {
					graphics.renderComponentTooltip(font, Screen.hasShiftDown() ?  TextLists.emptyFluidTankDo() : TextLists.emptyFluidTank(), mouseX - screenCoords[0], mouseY - screenCoords[1]);
				}
			}
		}
		
		public static void renderScaledElementUpNestled(GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, int scaledIn, ResourceLocation location) {
//			graphics.blit(location, screenCoords[0] + drawX, (screenCoords[1] + drawY + height) - scaledIn, textureInX, (textureInY + height) - scaledIn, width, scaledIn);
			renderStaticElementInternal(graphics, screenCoords[0] + drawX, (screenCoords[1] + drawY + height) - scaledIn, textureInX, (textureInY + height) - scaledIn, width, scaledIn, NORMAL_COLOUR, location);
		}
		
		public static void renderScaledElementDownNestled(GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int scaledIn, ResourceLocation location) {
			renderStaticElement(graphics, screenCoords, drawX, drawY, textureInX, textureInY, width, scaledIn, location);
		}

		public static void renderScaledElementLeftNestled(GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, int scaledIn, ResourceLocation location) {
//			graphics.blit(location, (screenCoords[0] + drawX + width) - scaledIn, screenCoords[1] + drawY, (textureInX + width) - scaledIn, textureInY, scaledIn, height);
			renderStaticElementInternal(graphics, (screenCoords[0] + drawX + width) - scaledIn, screenCoords[1] + drawY, (textureInX + width) - scaledIn, textureInY, scaledIn, height, NORMAL_COLOUR, location);
		}
		
		public static void renderScaledElementRightNestled(GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int height, int scaledIn, ResourceLocation location) {
			renderStaticElement(graphics, screenCoords, drawX, drawY, textureInX, textureInY, scaledIn + 1, height, location);
		}
		
		public static void renderStaticElementWithUIMode(GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, IBEUIMode entity, ResourceLocation[] locations) {
			renderStaticElementWithUIMode(graphics, screenCoords, drawX, drawY, textureInX, textureInY, width, height, Setup.colourToRGBA(ComponentColour.WHITE), entity.getUIMode(), locations);
		}
	
		public static void renderStaticElementWithUIMode(GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, float[] colourIn, IBEUIMode entity, ResourceLocation[] locations) {
			renderStaticElementWithUIMode(graphics, screenCoords, drawX, drawY, textureInX, textureInY, width, height, colourIn, entity.getUIMode(), locations);
		}
	
		public static void renderStaticElementWithUIMode(GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, EnumUIMode uiMode, ResourceLocation[] locations) {
			renderStaticElementWithUIMode(graphics, screenCoords, drawX, drawY, textureInX, textureInY, width, height, Setup.colourToRGBA(ComponentColour.WHITE), uiMode, locations);
		}
	
		public static void renderStaticElementWithUIMode(GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, float[] colourIn, EnumUIMode uiMode, ResourceLocation[] locations) {
			renderStaticElement(graphics, screenCoords, drawX, drawY, textureInX, textureInY, width, height, colourIn, uiMode.equals(EnumUIMode.DARK) ? locations[1] : locations[0]);
		}
		
		public static void renderStaticElement(GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, ResourceLocation location) {
			renderStaticElement(graphics, screenCoords, drawX, drawY, textureInX, textureInY, width, height, Setup.colourToRGBA(ComponentColour.WHITE), location);
		}
	
		public static void renderStaticElementToggled(GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, boolean enabled, ResourceLocation location) {
			if (enabled) {
				renderStaticElement(graphics, screenCoords, drawX, drawY, textureInX, textureInY, width, height, Setup.colourToRGBA(ComponentColour.WHITE), location);
			}
		}
		
		public static void renderStaticElement(GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, float[] colourIn, ResourceLocation location) {
			renderStaticElementInternal(graphics, screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY, width, height, colourIn, location);
		}
		
		private static void renderStaticElementInternal(GuiGraphics graphics, int drawX, int drawY, int textureInX, int textureInY, int width, int height, float[] colourIn, ResourceLocation location) {
			Setup.setTextureWithColour(graphics.pose(), location, colourIn);
			graphics.blit(location, drawX, drawY, textureInX, textureInY, width, height);
			Setup.setTextureColour(NORMAL_COLOUR);
		}
	}
	
	public static class FontRenderer {
		public static void drawCenteredComponent(GuiGraphics graphics, Font font, int[] screenCoords, int xOffset, int yOffset, Component comp) {
			drawCenteredComponent(graphics, font, screenCoords, xOffset, yOffset, comp, false);
		}
		
		public static void drawCenteredComponent(GuiGraphics graphics, Font font, int[] screenCoords, int xOffset, int yOffset, Component string, boolean shadow) {
			drawCenteredComponent(graphics, font, screenCoords, xOffset, yOffset, string.getStyle().getColor().getValue(), string, shadow);
		}

		public static void drawCenteredComponent(GuiGraphics graphics, Font font, int[] screenCoords, int xOffset, int yOffset, int colourIn, Component string, boolean shadow) {
			int x = (screenCoords[0] * 2) / 2;
			int y = screenCoords[1] * 2 / 2 + 33;

			graphics.drawString(font, string, ((int)(x - font.width(string) / 2) + xOffset), y + yOffset, colourIn, shadow);
		}

		public static void drawCenteredString(GuiGraphics graphics, Font font, int[] screenCoords, int xOffset, int yOffset, int colourIn, String string) {
			drawCenteredString(graphics, font, screenCoords, xOffset, yOffset, colourIn, string, false);
		}
		
		public static void drawCenteredString(GuiGraphics graphics, Font font, int[] screenCoords, int xOffset, int yOffset, int colourIn, String string, boolean shadow) {
			int x = (screenCoords[0] * 2) / 2;
			int y = screenCoords[1] * 2 / 2 + 33;

			graphics.drawString(font, string, ((float)(x - font.width(string) / 2) + xOffset), y + yOffset, colourIn, shadow);
		}

		public static void drawWrappedStringBR(GuiGraphics graphics, Font font, int[] screenCoords, int xOffset, int yOffset, int length, int colourIn, String string) {
			drawWrappedComponentBR(graphics, font, screenCoords, xOffset, yOffset, length, ComponentHelper.compRaw(string));
		}

		public static void drawWrappedComponentBR(GuiGraphics graphics, Font font, int[] screenCoords, int xOffset, int yOffset, int length, Component comp) {
			int prevLines = 0;
			
			for (String str : comp.getString().split("<br>")) {
				int x = (screenCoords[0] * 2) / 2;
				int y = (screenCoords[1] * 2) / 2 + 33;

				graphics.drawString(font, str, ((x - font.width(str) / 2) + xOffset), y + yOffset + (font.lineHeight * prevLines), comp.getStyle().getColor().getValue());
				
				prevLines += (int) Math.ceil((float) (str.length() * 7) / (float) 204);
			}
		}
		
		public static void drawInventoryString(GuiGraphics graphics, Font font, int[] screenCoords, int drawX, int drawY, int colourIn) {
			graphics.drawString(font, I18n.get("screen.inventory"), screenCoords[0] + drawX, screenCoords[1] + drawY, colourIn);
		}
		
		public static void drawString(GuiGraphics graphics, Font font, int[] screenCoords, int x, int y, boolean drawFrom, Component comp) {
			drawString(graphics, font, screenCoords, x, y, drawFrom, comp, false);
		}
		
		public static void drawStringShadow(GuiGraphics graphics, Font font, int[] screenCoords, int x, int y, boolean drawFrom, Component comp) {
			drawString(graphics, font, screenCoords, x, y, drawFrom, comp, true);
		}
		
		public static void drawString(GuiGraphics graphics, Font font, int[] screenCoords, int x, int y, boolean drawFrom, Component comp, boolean shadow) {
			graphics.drawString(font, comp, !drawFrom ? x : screenCoords[0] + x, !drawFrom ? y : screenCoords[1] + y, comp.getStyle().getColor().getValue(), shadow);
		}
	}
	
	public static class Hovering {
		public static boolean isHoveringPower(int mouseX, int mouseY, int x, int y) {
			if (mouseX >= x && mouseX <= x + 17) {
				if (mouseY >= y && mouseY <= y + 62) {
					return true;
				}
			}
			return false;
		}
		
		public static boolean isHoveringPowerSmall(int mouseX, int mouseY, int x, int y) {
			if (mouseX >= x && mouseX <= x + 17) {
				if (mouseY >= y && mouseY <= y + 40) {
					return true;
				}
			}
			return false;
		}
		
		public static boolean isHovering(int mouseX, int mouseY, int minX, int maxX, int minY, int maxY) {
			if (mouseX >= minX && mouseX <= maxX) {
				if (mouseY >= minY && mouseY <= maxY) {
					return true;
				}
			}
			return false;
		}
		
		public static boolean isHoveringFluid(int mouseX, int mouseY, int x, int y) {
			if (mouseX >= x && mouseX <= x + 16) {
				if (mouseY >= y && mouseY <= y + 38) {
					return true;
				}
			}
			return false;
		}
		
		public static boolean isHoveringFluidLarge(int mouseX, int mouseY, int x, int y) {
			if (mouseX >= x - 1 && mouseX <= x + 16) {
				if (mouseY >= y && mouseY <= y + 57) {
					return true;
				}
			}
			return false;
		}
		
		public static boolean isHoveringButtonStandard(int mouseX, int mouseY, int x, int y) {
			if (mouseX >= x && mouseX <= x + 18) {
				if (mouseY >= y && mouseY <= y + 18) {
					return true;
				}
			}
			return false;
		}
		
		public static boolean isHoveringButton(int mouseX, int mouseY, int x, int y, int xSize, int ySize) {
			if (mouseX >= x && mouseX <= x + xSize) {
				if (mouseY >= y && mouseY <= y + ySize) {
					return true;
				}
			}
			return false;
		}
	}
	
	private static class TextLists {
		static List<Component> storedTextNo(int stored) {
			MutableComponent[] description = {
				ComponentHelper.style(ComponentColour.PURPLE, "", "Stored: " + Value.RED + stored)
			};
			
			return Arrays.asList(description);
		}
		
		static List<Component> fluidText(String name, int amount, int capacity) {
			MutableComponent[] description = {
				ComponentHelper.style(ComponentColour.CYAN, "", "Fluid: " + name), 
				ComponentHelper.style(ComponentColour.ORANGE, "", "Amount: " + amount + " / " + capacity + " mB")
			};
			
			return Arrays.asList(description);
		}
		
		static List<Component> fluidTextEmpty() {
			MutableComponent[] description = { 
				ComponentHelper.style(ComponentColour.CYAN, "", "Empty:"), 
				ComponentHelper.style(ComponentColour.ORANGE, "", "Amount: 0 mB")
			};
			
			return Arrays.asList(description);
		}
		
		static List<Component> emptyFluidTankDo() {
			MutableComponent[] description = { 
				ComponentHelper.style(ComponentColour.GREEN, "", "Empty tank."), 
				ComponentHelper.style(ComponentColour.RED, "", "Warning: " + Value.ORANGE + "Cannot be undone!")
			};
			
			return Arrays.asList(description);
		}

		static List<Component> emptyFluidTank() {
			MutableComponent[] description = {
				ComponentHelper.style(ComponentColour.GREEN, "", "Shift click " + Value.LIGHT_GRAY + "to empty tank.")
			};
			
			return Arrays.asList(description);
		}
				
		static List<Component> generationText(int storedIn, int generationRateIn) {
			MutableComponent[] description = { 
				ComponentHelper.style(ComponentColour.PURPLE, "", "Stored: " + Value.ORANGE + storedIn), 
				ComponentHelper.style(ComponentColour.RED, "", "Producing: " + Value.CYAN + generationRateIn + Value.RED + " FE/t.")
			};
			
			return Arrays.asList(description);
		}
	}

    /** - DO NOT USE THIS - */
	protected static class Extension {
		private static void blit16(GuiGraphics graphicsIn, int x, int y, int blitOffset, int width, int height, TextureAtlasSprite sprite) {
	        blitSprite16(graphicsIn, sprite, x, y, blitOffset, width, height);
	    }
		
	    private static void blitSprite16(GuiGraphics graphicsIn, TextureAtlasSprite sprite, int x, int y, int blitOffset, int width, int height) {	    	
	        if (width != 0 && height != 0) {
				innerBlitMipped(graphicsIn, sprite.atlasLocation(), x, x + width, y, y + height, blitOffset, sprite.getU0(), sprite.getU1(), sprite.getV0(), sprite.getV1());
			}
		}

		private static void innerBlitMipped(GuiGraphics graphicsIn, ResourceLocation atlasLocation, int x1, int x2, int y1, int y2, int blitOffset, float minU, float maxU, float minV, float maxV) {
			float ref = 0.001F * (16 - (y2 - y1));

			RenderSystem.setShaderTexture(0, atlasLocation);
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			Matrix4f matrix4f = graphicsIn.pose().last().pose();
			BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			bufferbuilder.addVertex(matrix4f, (float) x1, (float) y1, (float) blitOffset).setUv(minU, minV + ref);
			bufferbuilder.addVertex(matrix4f, (float) x1, (float) y2, (float) blitOffset).setUv(minU, maxV);
			bufferbuilder.addVertex(matrix4f, (float) x2, (float) y2, (float) blitOffset).setUv(maxU, maxV);
			bufferbuilder.addVertex(matrix4f, (float) x2, (float) y1, (float) blitOffset).setUv(maxU, minV + ref);
			BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
		}
	}
}