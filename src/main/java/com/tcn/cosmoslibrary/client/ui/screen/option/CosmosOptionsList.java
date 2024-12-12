package com.tcn.cosmoslibrary.client.ui.screen.option;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CosmosOptionsList extends ContainerObjectSelectionList<CosmosOptionsList.Entry> {

	private int smallWidth;
	private int bigWidth;
	
	private int buttonHeight;
	
	public CosmosOptions options;
	
	public CosmosOptionsList(Minecraft minecraftIn, int widthIn, int heightIn, int yZeroIn, int itemHeight, int buttonHeightIn, CosmosOptions options) {
		this(minecraftIn, widthIn, heightIn, yZeroIn, itemHeight, buttonHeightIn, 310, options);
	}
	
	public CosmosOptionsList(Minecraft minecraftIn, int widthIn, int heightIn, int yZeroIn, int itemHeight, int buttonHeightIn, int bigWidthIn, CosmosOptions options) {
		super(minecraftIn, widthIn, heightIn, yZeroIn, itemHeight);
		this.centerListVertically = false;
		
		this.options = options;
		
		this.smallWidth = (bigWidthIn - 10) / 2;
		this.bigWidth = bigWidthIn;
		this.buttonHeight = buttonHeightIn;
	}

	public int addBig(CosmosOptionInstance<?> optionIn) {
		return this.addEntry(CosmosOptionsList.Entry.big(this.options, this.width, optionIn, this.bigWidth, this.buttonHeight));
	}

	public void addSmall(CosmosOptionInstance<?> optionIn, @Nullable CosmosOptionInstance<?> secondOptionIn) {
		this.addEntry(CosmosOptionsList.Entry.small(this.options, this.width, optionIn, secondOptionIn, this.smallWidth, this.bigWidth, this.buttonHeight));
	}

	public void addSmall(CosmosOptionInstance<?>[] optionsIn) {
		for (int i = 0; i < optionsIn.length; i += 2) {
			this.addSmall(optionsIn[i], i < optionsIn.length - 1 ? optionsIn[i + 1] : null);
		}
	}

	@Override
	public int getRowWidth() {
		return 400;
	}

	@Override
	protected int getScrollbarPosition() {
		return super.getScrollbarPosition() + 32;
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

		public static CosmosOptionsList.Entry big(CosmosOptions optionsIn, int screenWidthIn, CosmosOptionInstance<?> optionIn, int widthIn, int heightIn) {
			AbstractWidget abstractWidget = optionIn.createButton(optionsIn, screenWidthIn / 2 - (widthIn / 2), 0, widthIn, heightIn);
			return !optionIn.hasResetButton() ? 
					new CosmosOptionsList.Entry(ImmutableMap.of(optionIn, abstractWidget)) : 
						new CosmosOptionsList.Entry(ImmutableMap.of(optionIn, abstractWidget), optionIn.createResetButton(optionsIn, screenWidthIn / 2 - (widthIn / 2), 45, widthIn, heightIn));
		}

		public static CosmosOptionsList.Entry small(CosmosOptions optionsIn, int screenWidthIn, CosmosOptionInstance<?> optionOneIn, @Nullable CosmosOptionInstance<?> optionTwoIn, int widthIn, int bigWidthIn, int heightIn) {
			AbstractWidget abstractwidget = optionOneIn.createButton(optionsIn, screenWidthIn / 2 - (bigWidthIn / 2), 0, widthIn, heightIn);
			return optionTwoIn == null ? 
					new CosmosOptionsList.Entry(ImmutableMap.of(optionOneIn, abstractwidget)) : 
						new CosmosOptionsList.Entry(ImmutableMap.of(optionOneIn, abstractwidget, optionTwoIn, optionTwoIn.createButton(optionsIn, screenWidthIn / 2 - (bigWidthIn / 2) + (widthIn + 10), 0, widthIn, heightIn)));
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