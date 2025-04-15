package com.tcn.cosmoslibrary.client.ui.screen.option;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CosmosOptionsList extends ContainerObjectSelectionList<CosmosOptionsList.Entry> {
	
	private static final ResourceLocation SCROLLER_SPRITE = ResourceLocation.withDefaultNamespace("widget/scroller");
    
    @Nullable
    private CosmosOptionsList.Entry hovered;
    
	private int smallWidth;
	private int bigWidth;
	
	private int buttonHeight;
	private int footerHeight;
	private int headerHeightAlt;
	
	public CosmosOptionsList(Minecraft minecraftIn, int widthIn, int heightIn, int headerHeightIn, int yZeroIn, int itemHeight, int buttonHeightIn, int footerHeightIn) {
		this(minecraftIn, widthIn, heightIn, headerHeightIn, yZeroIn, itemHeight, buttonHeightIn, 310, footerHeightIn);
	}
	
	public CosmosOptionsList(Minecraft minecraftIn, int widthIn, int heightIn, int headerHeightIn, int yZeroIn, int itemHeight, int buttonHeightIn, int bigWidthIn, int footerHeightIn) {
		super(minecraftIn, widthIn, heightIn - headerHeightIn, yZeroIn, itemHeight);
		this.centerListVertically = false;
		
		this.smallWidth = (bigWidthIn - 10) / 2;
		this.bigWidth = bigWidthIn;
		this.buttonHeight = buttonHeightIn;
		this.footerHeight = footerHeightIn;
		this.headerHeightAlt = headerHeightIn;
	}

	public int addBig(CosmosOptionInstance<?> optionIn) {
		return this.addEntry(CosmosOptionsList.Entry.big(this.width, optionIn, this.bigWidth, this.buttonHeight));
	}

	public void addSmall(CosmosOptionInstance<?> optionIn, @Nullable CosmosOptionInstance<?> secondOptionIn) {
		this.addEntry(CosmosOptionsList.Entry.small(this.width, optionIn, secondOptionIn, this.smallWidth, this.bigWidth, this.buttonHeight));
	}

	public void addSmall(CosmosOptionInstance<?>[] optionsIn) {
		for (int i = 0; i < optionsIn.length; i += 2) {
			this.addSmall(optionsIn[i], i < optionsIn.length - 1 ? optionsIn[i + 1] : null);
		}
	}
	
	public void clear() {
		this.children().clear();
	}
	
	public void resize(Minecraft minecraft, int width, int height) {
		this.width = width;
		this.height = height - this.headerHeightAlt;
	}

	@Override
	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		this.hovered = this.isMouseOver((double) mouseX, (double) mouseY) ? this.getEntryAtPosition((double) mouseX, (double) mouseY) : null;
		//this.renderListBackground(guiGraphics);
		this.enableScissor(guiGraphics);

		this.renderListItems(guiGraphics, mouseX, mouseY, partialTick);
		guiGraphics.disableScissor();
//		this.renderListSeparators(guiGraphics);
		if (this.scrollbarVisible()) {
			int l = this.getScrollbarPosition();
			int i1 = (int) ((float) (this.height * this.height) / (float) this.getMaxPosition());
			i1 = Mth.clamp(i1, 32, this.height - 8 - this.footerHeight);
			int k = (int) this.getScrollAmount() * (this.height - this.footerHeight - i1) / this.getMaxScroll() + this.getY();
			if (k < this.getY()) {
				k = this.getY();
			}

			RenderSystem.enableBlend();
			// guiGraphics.blitSprite(SCROLLER_BACKGROUND_SPRITE, l, this.getY(), 6,
			// this.getHeight());
			guiGraphics.blitSprite(SCROLLER_SPRITE, l, k, 6, i1);
			RenderSystem.disableBlend();
		}

		this.renderDecorations(guiGraphics, mouseX, mouseY);
		RenderSystem.disableBlend();
	}

	@Override
	public int getRowWidth() {
		return 400;
	}

	@Override
    protected int getMaxPosition() {
        return this.getItemCount() == 1 ? this.getItemCount() * (this.itemHeight) + this.headerHeight : this.getItemCount() * (this.itemHeight) + this.headerHeight;
    }

	@Override
	public int getScrollbarPosition() {
		return super.getScrollbarPosition() - 10;
	}

	@Override
    public boolean scrollbarVisible() {
        return this.getMaxScroll() > 0;
    }

	@Override
    protected int getRowTop(int index) {
        return this.getY() + 4 - (int)this.getScrollAmount() + index * this.itemHeight + this.headerHeight;
    }

	@Override
    protected int getRowBottom(int index) {
        return this.getRowTop(index) + this.itemHeight;
    }

	@Override
    public int getBottom() {
        return this.getY() + this.getHeight() - this.headerHeight - this.footerHeight;
    }

    public int getMaxScroll() {
        return Math.max(0, this.getMaxPosition() - (this.height - 4) + this.footerHeight);
    }

	@Nullable
	public AbstractWidget findOption(CosmosOptionInstance<?> optionIn) {
		for (CosmosOptionsList.Entry optionslist$entry : this.children()) {
			AbstractWidget abstractwidget = optionslist$entry.options.get(optionIn);
			if (abstractwidget != null) {
				return abstractwidget;
			}
		}

		return null;
	}

	public Optional<AbstractWidget> getMouseOver(double mouseX, double mouseY) {
		for (CosmosOptionsList.Entry optionslist$entry : this.children()) {
			for (AbstractWidget abstractwidget : optionslist$entry.children) {
				if (abstractwidget.isMouseOver(mouseX, mouseY)) {
					return Optional.of(abstractwidget);
				}
			}
		}

		return Optional.empty();
	}

	@OnlyIn(Dist.CLIENT)
	public static class Entry extends ContainerObjectSelectionList.Entry<CosmosOptionsList.Entry> {
		final Map<CosmosOptionInstance<?>, AbstractWidget> options;
		final List<AbstractWidget> children;

		private Entry(Map<CosmosOptionInstance<?>, AbstractWidget> optionMap) {
			this.options = optionMap;
			this.children = ImmutableList.copyOf(optionMap.values());
		}

		private Entry(Map<CosmosOptionInstance<?>, AbstractWidget> optionMap, AbstractWidget addedChild) {
			this.options = optionMap;
			
			this.children = Lists.newCopyOnWriteArrayList(optionMap.values());
			this.children.add(addedChild);
		}

		public static CosmosOptionsList.Entry big(int screenWidthIn, CosmosOptionInstance<?> optionIn, int widthIn, int heightIn) {
			AbstractWidget abstractWidget = optionIn.createButton(screenWidthIn / 2 - (widthIn / 2), 0, widthIn, heightIn);
			return !optionIn.hasResetButton() ? 
					new CosmosOptionsList.Entry(ImmutableMap.of(optionIn, abstractWidget)) : 
						new CosmosOptionsList.Entry(ImmutableMap.of(optionIn, abstractWidget), optionIn.createResetButton(screenWidthIn / 2 - (widthIn / 2), 45, widthIn, heightIn));
		}

		public static CosmosOptionsList.Entry small(int screenWidthIn, CosmosOptionInstance<?> optionOneIn, @Nullable CosmosOptionInstance<?> optionTwoIn, int widthIn, int bigWidthIn, int heightIn) {
			AbstractWidget abstractwidget = optionOneIn.createButton(screenWidthIn / 2 - (bigWidthIn / 2), 0, widthIn, heightIn);
			return optionTwoIn == null ? 
					new CosmosOptionsList.Entry(ImmutableMap.of(optionOneIn, abstractwidget)) : 
						new CosmosOptionsList.Entry(ImmutableMap.of(optionOneIn, abstractwidget, optionTwoIn, optionTwoIn.createButton(screenWidthIn / 2 - (bigWidthIn / 2) + (widthIn + 10), 0, widthIn, heightIn)));
		}
		
		@Override
		public void render(GuiGraphics graphics, int xPosIn, int yPosIn, int p_94499_, int p_94500_, int p_94501_, int mouseX, int mouseY, boolean p_94504_, float partialTicks) {
			this.children.forEach((widget) -> {
				renderWidget(widget, graphics, xPosIn, yPosIn, mouseX, mouseY, partialTicks);
			});
		}
		
		public void renderWidget(AbstractWidget widgetIn, GuiGraphics graphics, int xPosIn, int yPosIn, int mouseX, int mouseY, float partialTicks) {
			if (!(widgetIn instanceof EditBox)) {
				widgetIn.setY(yPosIn);
				widgetIn.render(graphics, mouseX, mouseY, partialTicks);
			}
			else if (widgetIn instanceof EditBox) {
				widgetIn.setY(yPosIn + 2);
				widgetIn.render(graphics, mouseX, mouseY, partialTicks);
			}
		}

		@Override
		public List<? extends GuiEventListener> children() {
			return this.children;
		}

		@Override
		public List<? extends NarratableEntry> narratables() {
			return this.children;
		}
	}
}