package com.tcn.cosmoslibrary.client.ui.screen;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tcn.cosmoslibrary.client.container.CosmosContainerRecipeBookBlockEntity;
import com.tcn.cosmoslibrary.client.ui.lib.CosmosUISystem;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosButtonBase;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosButtonUIHelp;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosButtonUILock;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosButtonUIMode;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosUIHelpElement;
import com.tcn.cosmoslibrary.common.enums.EnumUIHelp;
import com.tcn.cosmoslibrary.common.enums.EnumUIMode;
import com.tcn.cosmoslibrary.common.interfaces.blockentity.IBlockEntityUIMode;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmoslibrary.runtime.network.PacketUIHelp;
import com.tcn.cosmoslibrary.runtime.network.PacketUILock;
import com.tcn.cosmoslibrary.runtime.network.PacketUIMode;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.PacketDistributor;

public class CosmosScreenUIModeRecipeBook<A extends RecipeInput, B extends Recipe<A>, J extends CosmosContainerRecipeBookBlockEntity<A, B>> extends AbstractContainerScreen<J> {

	protected ResourceLocation TEXTURE_LIGHT;
	protected ResourceLocation TEXTURE_DARK;

	protected ResourceLocation DUAL_TEXTURE_LIGHT;
	protected ResourceLocation DUAL_TEXTURE_DARK;
	
	protected CosmosButtonUIMode uiModeButton; private int[] uiModeButtonIndex;
	protected CosmosButtonUIHelp uiHelpButton; private int[] uiHelpButtonIndex;
	protected CosmosButtonUILock uiLockButton; private int[] uiLockButtonIndex;
	
	protected List<CosmosUIHelpElement> uiHelpElements = Lists.newArrayList();
	
	private int[] screenCoords;
	
	private boolean hasDualScreen = false;
	private int[] dualScreenIndex;
	
	private boolean renderTitleLabel = true;
	private boolean renderInventoryLabel = true;
	
	private boolean hasUIHelp = false;
	private boolean hasUIHelpElementDeadzone = false;
	private int[] uiHelpElementDeadzone;
	private int uiHelpTitleYOffset = 0;

	private boolean hasUILock = false;
	
	public CosmosScreenUIModeRecipeBook(J containerIn, Inventory playerInventoryIn, Component titleIn) {
		super(containerIn, playerInventoryIn, titleIn);
	}
	
	@Override
	protected void init() {
		this.setScreenCoords(CosmosUISystem.getScreenCoords(this, this.imageWidth, this.imageHeight));
		super.init();
		this.addButtons();
		this.addUIHelpElements();
	}
	
	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(graphics, mouseX, mouseY, partialTicks);
		super.render(graphics, mouseX, mouseY, partialTicks);

		this.renderComponents(graphics, mouseX, mouseY, partialTicks);
		this.renderComponentHoverEffect(graphics, Style.EMPTY, mouseX, mouseY);
		this.renderTooltip(graphics, mouseX, mouseY);
	}
	
	public void renderComponents(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		this.addButtons();
		this.addUIHelpElements();
		this.renderUIHelpElements(graphics, mouseX, mouseY, partialTicks);
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float mouseX, int mouseY, int partialTicks) {
		BlockEntity entity = this.getBlockEntity();
		
		if (entity instanceof IBlockEntityUIMode) {
			IBlockEntityUIMode entityMode = (IBlockEntityUIMode) entity;
			float[] colour = new float[] { 1.0F, 1.0F, 1.0F, 1.0F };

			if (entityMode.getUIHelp().equals(EnumUIHelp.SHOWN)) {
				colour = new float[] { 0.5F, 0.5F, 0.5F, 1.0F };
			}
			
			CosmosUISystem.renderStaticElementWithUIMode(this, graphics, this.screenCoords, 0, 0, 0, 0, Mth.clamp(this.imageWidth, 0, 256), this.imageHeight, colour, entityMode.getUIMode(), new ResourceLocation[] { TEXTURE_LIGHT, TEXTURE_DARK });
			
			if (this.hasDualScreen) {
				if(this.dualScreenIndex != null && this.DUAL_TEXTURE_LIGHT != null && this.DUAL_TEXTURE_LIGHT != null) {
					CosmosUISystem.renderStaticElementWithUIMode(this, graphics, this.screenCoords, this.dualScreenIndex[0], this.dualScreenIndex[1], 0, 0, 
							Mth.clamp(256 + this.dualScreenIndex[2], 0, 256), this.dualScreenIndex[3], colour, entityMode.getUIMode(), new ResourceLocation[] { DUAL_TEXTURE_LIGHT, DUAL_TEXTURE_DARK });
				}
			}
		}
	}

	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		BlockEntity entity = this.getBlockEntity();
		
		if (entity instanceof IBlockEntityUIMode) {
			IBlockEntityUIMode blockEntity = (IBlockEntityUIMode) entity;
			
			if (this.renderTitleLabel) {
				graphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, blockEntity.getUIMode().equals(EnumUIMode.DARK) ? CosmosUISystem.DEFAULT_COLOUR_FONT_LIST : ComponentColour.SCREEN_LIGHT.dec());
			}
			
			if (this.renderInventoryLabel) {
				graphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, blockEntity.getUIMode().equals(EnumUIMode.DARK) ? CosmosUISystem.DEFAULT_COLOUR_FONT_LIST : ComponentColour.SCREEN_LIGHT.dec());
			}
		}
	}
	
	@Override
	protected void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
		if (this.getHasUIHelpShow()) {
			if (this.hasUIHelpElementDeadzone) {
				if (!(mouseX > (this.getScreenCoords()[0] + this.uiHelpElementDeadzone[0]) && mouseX < (this.getScreenCoords()[0] + this.uiHelpElementDeadzone[2]) 
						&& mouseY > (this.getScreenCoords()[1] + this.uiHelpElementDeadzone[1]) && mouseY < (this.getScreenCoords()[1] + this.uiHelpElementDeadzone[3]))) {
					super.renderTooltip(graphics, mouseX, mouseY);
				}
			} else {
				super.renderTooltip(graphics, mouseX, mouseY);
			}
		} else {
			super.renderTooltip(graphics, mouseX, mouseY);
		}
	}
	
	public void renderComponentHoverEffect(GuiGraphics graphics, Style style, int mouseX, int mouseY) {
		BlockEntity entity = this.getBlockEntity();
		
		if (entity instanceof IBlockEntityUIMode) {
			IBlockEntityUIMode blockEntity = (IBlockEntityUIMode) entity;
			
			if (blockEntity.getUIHelp().equals(EnumUIHelp.HIDDEN)) {
				this.renderStandardHoverEffect(graphics, style, mouseX, mouseY);
			} else {
				this.renderHelpElementHoverEffect(graphics, mouseX, mouseY);
			}
			
			if (this.uiModeButton.isMouseOver(mouseX, mouseY)) {
				Component[] comp = new Component[] { 
					ComponentHelper.style(ComponentColour.WHITE, "cosmoslibrary.gui.ui_mode.info"),
					ComponentHelper.style(ComponentColour.GRAY, "cosmoslibrary.gui.ui_mode.value").append(blockEntity.getUIMode().getColouredComp())
				};
				
				graphics.renderComponentTooltip(this.font, Arrays.asList(comp), mouseX, mouseY);
			}
			
			if (this.getHasUIHelp()) {
				if (this.uiHelpButton.isMouseOver(mouseX, mouseY)) {
					Component[] comp = new Component[] { 
						ComponentHelper.style(ComponentColour.WHITE, "cosmoslibrary.gui.ui_help.info"),
						ComponentHelper.style(ComponentColour.GRAY, "cosmoslibrary.gui.ui_help.value").append(blockEntity.getUIHelp().getColouredComp())
					};
					
					graphics.renderComponentTooltip(this.font, Arrays.asList(comp), mouseX, mouseY);
				}
			}

			if (this.getHasUILock()) {
				if (this.uiLockButton.isMouseOver(mouseX, mouseY)) {
					Component[] comp = new Component[] { 
						ComponentHelper.style(ComponentColour.WHITE, "cosmoslibrary.gui.ui_lock.info"),
						ComponentHelper.style(ComponentColour.GRAY, "cosmoslibrary.gui.ui_lock.value").append(blockEntity.getUILock().getColouredComp())
					};
					
					graphics.renderComponentTooltip(this.font, Arrays.asList(comp), mouseX, mouseY);
				}
			}
		}
	}
	
	protected void renderStandardHoverEffect(GuiGraphics graphics, Style style, int mouseX, int mouseY) { }
	
	protected void renderHelpElementHoverEffect(GuiGraphics graphics, int mouseX, int mouseY) {
		BlockEntity entity = this.getBlockEntity();
		
		if (entity instanceof IBlockEntityUIMode) {
			IBlockEntityUIMode blockEntity = (IBlockEntityUIMode) entity;
			
			if (this.getHasUIHelpShow()) {
				if (blockEntity.getUIHelp().equals(EnumUIHelp.SHOWN)) {
					for (CosmosUIHelpElement element : this.uiHelpElements) {
						if (element.isMouseOver(mouseX, mouseY)) {
							RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);	
							
							graphics.renderComponentTooltip(this.font, element.getHoverElement(), mouseX, mouseY);
						}
					}
					
					RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
					
					Component title = ComponentHelper.style(ComponentColour.GREEN, "cosmoslibrary.gui_help_title");
					//this.font.draw(poseStack, title, ((this.getScreenCoords()[0] * 2) / 2) + (this.imageWidth / 2) - (this.font.width(title) / 2), this.getScreenCoords()[1] - 8, CosmosColour.WHITE.dec());
					graphics.renderComponentTooltip(this.font, Arrays.asList(title), ((this.getScreenCoords()[0] * 2) / 2) + (this.imageWidth / 2) - (this.font.width(title) / 2) - 13, this.getScreenCoords()[1] - 2 + this.uiHelpTitleYOffset);
				}
			}
		}
	}
	
	protected void renderUIHelpElements(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		if (this.getHasUIHelpShow()) {
			for (CosmosUIHelpElement element : this.uiHelpElements) {
				element.render(graphics, mouseX, mouseY, partialTicks);
			}
			
			for (Renderable widget : this.renderables) {
				if (!(widget instanceof CosmosUIHelpElement) && !(widget instanceof CosmosButtonUIHelp) && !(widget instanceof CosmosButtonUIMode)) {
					if (widget instanceof Button) {
						Button button = (Button) widget;
						
						button.active = false;
					}
				}
			}
		}
	}
	
	protected void addButtons() {
		this.clearWidgets();
		int[] screen_coords = CosmosUISystem.getScreenCoords(this, this.imageWidth, this.imageHeight);
		
		BlockEntity entity = this.getBlockEntity();
		
		if (entity instanceof IBlockEntityUIMode) {
			IBlockEntityUIMode blockEntity = (IBlockEntityUIMode) entity;
			
			this.addUIModeButton(blockEntity, screen_coords, uiModeButtonIndex, (button) -> { this.clickButton(this.uiModeButton, true); });
			
			if (this.getHasUIHelp()) {
				this.addUIHelpButton(blockEntity, screen_coords, uiHelpButtonIndex, (button) -> { this.clickButton(this.uiHelpButton, true); });
			}
			
			if (this.getHasUILock()) {
				this.addUILockButton(blockEntity, screen_coords, uiLockButtonIndex, (button) -> { this.clickButton(this.uiLockButton, true); });
			}
		}
	}
	
	protected void clickButton(Button button, boolean isLeftClick) {
		BlockEntity entity = this.getBlockEntity();
		
		if (entity instanceof IBlockEntityUIMode) {
			IBlockEntityUIMode blockEntity = (IBlockEntityUIMode) entity;
			
			if (button.equals(this.uiModeButton)) {				
				PacketDistributor.sendToServer(new PacketUIMode(this.menu.getBlockPos(), this.menu.getLevel().dimension()));
				blockEntity.cycleUIMode();
			}
			
			else if (button.equals(this.uiHelpButton)) {				
				PacketDistributor.sendToServer(new PacketUIHelp(this.menu.getBlockPos(), this.menu.getLevel().dimension()));
				blockEntity.cycleUIHelp();
			}

			else if (button.equals(this.uiLockButton)) {
				PacketDistributor.sendToServer(new PacketUILock(this.menu.getBlockPos(), this.menu.getLevel().dimension(), this.menu.getPlayer().getUUID()));
				if (blockEntity.checkIfOwner(this.menu.getPlayer())) {
					blockEntity.cycleUILock();
				}
			}
		}
	}
	
	protected void addUIModeButton(IBlockEntityUIMode entityIn, int[] screen_coords, int[] indexIn, Button.OnPress pressAction) {
		this.uiModeButton = this.addRenderableWidget(new CosmosButtonUIMode(entityIn.getUIMode(), screen_coords[0] + indexIn[0], screen_coords[1] + indexIn[1], true, true, ComponentHelper.empty(), pressAction));
	}

	protected void addUIHelpButton(IBlockEntityUIMode entityIn, int[] screen_coords, int[] indexIn, Button.OnPress pressAction) {
		this.uiHelpButton = this.addRenderableWidget(new CosmosButtonUIHelp(entityIn.getUIHelp(), screen_coords[0] + indexIn[0], screen_coords[1] + indexIn[1], true, true, ComponentHelper.empty(), pressAction));
	}
	
	protected void addUIHelpElements() { 
		this.clearUIHelpElementList();
	}

	protected void addRenderableUIHelpElement(int[] screenCoords, int xIn, int yIn, int widthIn, int heightIn, Component... descIn) {
		this.addRenderableUIHelpElement(screenCoords, xIn, yIn, widthIn, heightIn, true, descIn);
	}
	
	protected void addRenderableUIHelpElement(int[] screenCoords, int xIn, int yIn, int widthIn, int heightIn, ComponentColour colourIn, Component... descIn) {
		this.addRenderableUIHelpElement(screenCoords, xIn, yIn, widthIn, heightIn, true, colourIn, descIn);
	}
	
	protected void addRenderableUIHelpElement(int[] screenCoords, int xIn, int yIn, int widthIn, int heightIn, boolean isVisible, Component... descIn) {
		this.addUIHelpElement(new CosmosUIHelpElement(screenCoords[0] + xIn, screenCoords[1] + yIn, widthIn, heightIn, descIn).setVisible(isVisible));
	}

	protected void addRenderableUIHelpElement(int[] screenCoords, int xIn, int yIn, int widthIn, int heightIn, boolean isVisible, ComponentColour colourIn, Component... descIn) {
		this.addUIHelpElement(new CosmosUIHelpElement(screenCoords[0] + xIn, screenCoords[1] + yIn, widthIn, heightIn, colourIn, descIn).setVisible(isVisible));
	}
	
	protected void addUILockButton(IBlockEntityUIMode entityIn, int[] screen_coords, int[] indexIn, Button.OnPress pressAction) {
		this.uiLockButton = this.addRenderableWidget(new CosmosButtonUILock(entityIn.getUILock(), screen_coords[0] + indexIn[0], screen_coords[1] + indexIn[1], true, true, ComponentHelper.empty(), pressAction));
	}
	
	private CosmosUIHelpElement addUIHelpElement(CosmosUIHelpElement elementIn) {
		this.uiHelpElements.add(elementIn);
		//this.renderables.add(elementIn);
		return elementIn;
	}
	
	protected void clearUIHelpElementList() {
		this.uiHelpElements.clear();
	}
	
	protected boolean getHasUIHelp() {
		return this.hasUIHelp;
	}

	protected void setHasUIHelp() {
		this.hasUIHelp = true;
	}

	protected boolean getHasUIHelpShow() {
		BlockEntity entity = this.getBlockEntity();
		
		if (entity instanceof IBlockEntityUIMode) {
			IBlockEntityUIMode blockEntity = (IBlockEntityUIMode) entity;
			
			return this.hasUIHelp && blockEntity.getUIHelp().equals(EnumUIHelp.SHOWN);
		}
		return false;
	}
	
	protected void setDark(ResourceLocation textureIn) {
		this.TEXTURE_DARK = textureIn;
	}

	protected void setLight(ResourceLocation textureIn) {
		this.TEXTURE_LIGHT = textureIn;
	}
	
	protected void setUIModeButtonIndex(int posX, int posY) {
		this.uiModeButtonIndex = new int[] { posX, posY };
	}
	
	protected boolean getHasUILock() {
		return this.hasUILock;
	}

	protected void setHasUILock() {
		this.hasUILock = true;
	}
	
	protected void setUILockButtonIndex(int posX, int posY) {
		this.setHasUILock();
		this.uiLockButtonIndex = new int[] { posX, posY };
	}

	protected void setUIHelpButtonIndex(int posX, int posY) {
		this.setHasUIHelp();
		this.uiHelpButtonIndex = new int[] { posX, posY };
	}

	protected void setHasUIElementDeadzone() {
		this.hasUIHelpElementDeadzone = true;
	}

	protected void setUIHelpElementDeadzone(int minX, int minY, int maxX, int maxY) {
		this.setHasUIElementDeadzone();
		this.uiHelpElementDeadzone = new int[] { minX, minY, maxX, maxY };
	}
	
	protected void setUIHelpTitleOffset(int yOffset) {
		this.uiHelpTitleYOffset = yOffset;
	}
	
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
	
	protected void setDualScreen() {
		this.hasDualScreen = true;
	}
	
	protected void setDualScreenIndex(int posX, int posY, int width, int height) {
		this.setDualScreen();
		this.dualScreenIndex = new int[] { posX, posY, width, height };
	}

	protected void setDualDark(ResourceLocation textureIn) {
		this.setDualScreen();
		this.DUAL_TEXTURE_DARK = textureIn;
	}

	protected void setDualLight(ResourceLocation textureIn) {
		this.setDualScreen();
		this.DUAL_TEXTURE_LIGHT = textureIn;
	}

	protected void setNoTitleLabel() {
		this.renderTitleLabel = false;
	}
	
	protected void setNoInventoryLabel() {
		this.renderInventoryLabel = false;
	}
	
	protected int[] getScreenCoords() {
		return this.screenCoords;
	}
	
	public BlockEntity getBlockEntity() {
		CosmosContainerRecipeBookBlockEntity<A, B> container = (CosmosContainerRecipeBookBlockEntity<A, B>) this.menu;
		Level level = container.getLevel();
		BlockPos pos = container.getBlockPos();
		
		return level.getBlockEntity(pos);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		for (GuiEventListener list : this.children()) {
			if (list instanceof CosmosButtonBase) {
				CosmosButtonBase button = (CosmosButtonBase) list;
				
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