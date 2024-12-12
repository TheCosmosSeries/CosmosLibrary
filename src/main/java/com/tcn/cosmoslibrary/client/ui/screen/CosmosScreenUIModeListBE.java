package com.tcn.cosmoslibrary.client.ui.screen;

import java.util.ArrayList;

import com.tcn.cosmoslibrary.CosmosReference.RESOURCE.BASE;
import com.tcn.cosmoslibrary.client.container.CosmosContainerMenuBlockEntity;
import com.tcn.cosmoslibrary.client.ui.lib.CosmosUISystem;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosListWidget;
import com.tcn.cosmoslibrary.common.enums.EnumUIMode;
import com.tcn.cosmoslibrary.common.interfaces.blockentity.IBlockEntityUIMode;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CosmosScreenUIModeListBE<J extends CosmosContainerMenuBlockEntity> extends CosmosScreenUIModeBE<J> {

	protected ResourceLocation WIDGET_TEXTURE;
	protected ArrayList<CosmosListWidget> widgetList = new ArrayList<CosmosListWidget>();
	protected ArrayList<String> fromList = new ArrayList<String>();

	private boolean scrollEnabled = false;
	private int currentScroll;
	
	private int topIndex = 0;
	protected int selectedIndex = -1;
	
	private int[] listIndex;
	private int[] scrollElementIndex;
	
	public CosmosScreenUIModeListBE(J containerIn, Inventory playerInventoryIn, Component titleIn) {
		super(containerIn, playerInventoryIn, titleIn);
	}
	
	@Override
	protected void init() {
		super.init();
	}
	
	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		super.render(graphics, mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void renderBg(GuiGraphics graphics, float mouseX, int mouseY, int partialTicks) {
		super.renderBg(graphics, mouseX, mouseY, partialTicks);
	}
	
	protected void updateWidgetList() {
		this.updateFromStringList();
		//this.renderSmallWidgetList();
	}
	
	@Override
	public void renderComponents(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		this.renderScrollElement(graphics);
		this.updateWidgetList();
		this.renderWidgetList(graphics, mouseX, mouseY, partialTicks);
		super.renderComponents(graphics, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		for (int i = 0; i < widgetList.size(); i++) {
			CosmosListWidget widget = this.getListWidget(i);
			
			if (widget.isMouseOver() && i != 0) {
				widget.mousePressed(this.minecraft, mouseX, mouseY);
				this.selectWidget(i);
				this.selectedIndex = i;
			} else {
				if (mouseX < this.listIndex[0] || mouseX > this.listIndex[0] + this.listIndex[2] || mouseY < this.listIndex[1] || mouseY > this.listIndex[1] + this.listIndex[3]) {
					//this.deselectWidget(i);
					//this.selectedIndex = -1;
				}
			}
		}
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
		if (this.scrollEnabled()) {
			int maxScroll = this.widgetList.size() - this.widgetCount();
			this.currentScroll += -scrollY;
			
			if (this.currentScroll >= 0) {
				if (this.currentScroll <= maxScroll) {
					if (scrollY == -1.0F) {
						if (this.topIndex < this.fromList.size()) {
							this.topIndex += 1;
						}
					} else {
						if (this.topIndex > 0) {
							this.topIndex -= 1;
						}
					}
					
					return true;
				} else {
					this.currentScroll = maxScroll;
					return false;
				}
			} else {
				this.currentScroll = 0;
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public void resize(Minecraft mc, int width, int height) {
		this.clearWidgetList();
		super.resize(mc, width, height);
	}
	
	protected void setListDims(int x, int y, int width, int height, int widgetHeightIn, int widgetSpacingIn) {
		this.listIndex = new int[] { x, y, width, height, widgetHeightIn, widgetSpacingIn};
	}
	
	protected void setScrollElementDims(int x, int y) {
		this.scrollEnabled = true;
		this.scrollElementIndex = new int[] { x, y };
	}

	protected void setWidgetTexture(ResourceLocation textureIn) {
		this.WIDGET_TEXTURE = textureIn;
	}
	
	protected void updateFromList(ArrayList<String> fromListIn) {
		this.fromList = fromListIn;
	}
	
	protected CosmosListWidget addListWidget(CosmosListWidget widget) {
		this.widgetList.add(widget);
		return widget;
	}

	protected CosmosListWidget getListWidget(int index) {
		return this.widgetList.get(index);
	}

	protected ArrayList<CosmosListWidget> getWidgetList() {
		return this.widgetList;
	}
	
	protected void removeElement() {
		if (this.topIndex > 0) {
			this.topIndex = this.topIndex - 1;
		}
		this.selectedIndex = -1;
	}
	
	protected void selectWidget(int index) {
		for (int i = 0; i < this.widgetList.size(); i++) {
			this.widgetList.get(i).deselect();
		}
		
		this.widgetList.get(index).setSelectedState(true);
	}

	protected void deselectWidget(int index) {
		this.widgetList.get(index).setSelectedState(false);
	}
	
	public int getSelectedWidgetIndex() {
		if (this.selectedIndex > 0) {
			return this.selectedIndex;
		}
		
		return 0;
	}
	
	public int widgetCount() {
		return (int) Math.floor(this.listIndex[3] / (this.listIndex[4] + this.listIndex[5]));
	}
	
	public boolean scrollEnabled() {
		if (this.widgetList.size() > this.widgetCount()) {
			return true;
		} else {
			return false;
		}
	}

	protected void clearWidgetList() {
		this.widgetList.clear();
	}
	
	private void renderWidgetList(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		for (int i = 0; i < this.widgetList.size(); i++) {
			CosmosListWidget widget = this.getListWidget(i);
			
			if (i >= this.topIndex && i <= this.topIndex + this.widgetCount() && i < this.widgetList.size()) {
				widget.renderWidget(graphics, font, this.getScreenCoords(), this.listIndex[0], this.listIndex[1] + ((this.listIndex[4] + this.listIndex[5]) * (i - this.topIndex)), mouseX, mouseY, i, (this.listIndex[1] + this.listIndex[3]));
			}
		}
	}
	
	protected void updateFromStringList() {
		this.clearWidgetList();
		
		int spacing_y = this.listIndex[4] + this.listIndex[5];
		
		for (int i = 0; i < this.fromList.size(); i++) {
			CosmosListWidget widget = new CosmosListWidget(this.listIndex[0], this.listIndex[1] + (spacing_y * i), this.listIndex[2], this.listIndex[4], this.WIDGET_TEXTURE, this.fromList.get(i), ComponentColour.WHITE);
			
			this.widgetList.add(widget);
			
			if (i == this.selectedIndex) {
				this.getListWidget(i).setSelectedState(true);
			}
		}
	}
	
	protected void renderScrollElement(GuiGraphics graphics) {
		int[] scrollType = new int[] { 15, 0, 15 };
		CosmosUISystem.setTextureWithColour(graphics.pose(), BASE.GUI_ELEMENT_MISC_LOC, new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
		
		if (this.widgetList.size() > 0) {
			int div;
			
			if (this.widgetList.size() == this.widgetCount()) {
				div = 1;
			} else {
				div = this.widgetList.size() - this.widgetCount();
			}
			
			int increment = (this.listIndex[3]) / div;
			
			int posX = this.scrollElementIndex[0];
			int posY = this.scrollElementIndex[1];
			
			int posYUpdated = Mth.clamp((posY + (this.currentScroll * increment)), posY, (this.listIndex[1] + this.listIndex[3] - 1) - this.listIndex[4]);
			
			BlockEntity entity = this.getBlockEntity();
			if (entity instanceof IBlockEntityUIMode) {
				int type = ((IBlockEntityUIMode) entity).getUIMode().equals(EnumUIMode.DARK) ? 0 : 1;
				
				CosmosUISystem.renderStaticElementToggled(graphics, BASE.GUI_ELEMENT_MISC_LOC, this.getScreenCoords(), posX, this.scrollEnabled ? posYUpdated : posY, scrollType[0], scrollType[type + 1], 13, 15, true);
			}
		}
	}
}