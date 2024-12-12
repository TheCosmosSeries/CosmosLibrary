package com.tcn.cosmoslibrary.client.ui.screen.option;

import java.util.function.Consumer;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.network.chat.MutableComponent;

public class CosmosOptionBoolean extends CosmosOptionInstance<Boolean> {
	
	public CosmosOptionBoolean(ComponentColour colour, String flags, String caption, TYPE type, boolean initialValueIn, Consumer<Boolean> consumerFunctionIn) {
		this(colour, flags, caption, type, CosmosOptionInstance.noTooltip(), initialValueIn, consumerFunctionIn, ":");
	}

	public CosmosOptionBoolean(ComponentColour colour, String flags, String caption, TYPE type, CosmosOptionInstance.TooltipSupplierFactory<Boolean> tooltipIn, boolean initialValue, Consumer<Boolean> consumerFunctionIn, String splitterIn) {
		super(ComponentHelper.style(colour, flags, caption), tooltipIn, (comp, value) -> {
			return value ? type.getOnStateComp() : type.getOffStateComp();
		}, CosmosOptionInstance.BOOLEAN_VALUES, initialValue, false, consumerFunctionIn, false, splitterIn);
	}
	
	public void set(CosmosOptions options, String valueIn) {
		this.set(options, "true".equals(valueIn));
	}

	public void toggle(CosmosOptions options) {
		this.set(options, !this.value);
		options.save();
	}

	private void set(CosmosOptions options, boolean valueIn) {
		this.value = valueIn;
	}

	public enum TYPE {
		ON_OFF(ComponentColour.GREEN, ComponentColour.RED, ComponentHelper.style(ComponentColour.GREEN, "bold", "cosmoslibrary.options.on.composed"), ComponentHelper.style(ComponentColour.RED, "bold", "cosmoslibrary.options.off.composed")),
		YES_NO(ComponentColour.GREEN, ComponentColour.RED, ComponentHelper.style(ComponentColour.GREEN, "bold", "cosmoslibrary.options.yes.composed"), ComponentHelper.style(ComponentColour.RED, "bold", "cosmoslibrary.options.no.composed"));
		
		MutableComponent onState;
		MutableComponent offState;
		
		TYPE(ComponentColour onStateColourIn, ComponentColour offStateColourIn, MutableComponent onStateIn, MutableComponent offStateIn) {
			this.onState = onStateIn;
			this.offState = offStateIn;
		}
		
		public MutableComponent getOnStateComp() {
			return this.onState;
		}

		public MutableComponent getOffStateComp() {
			return this.offState;
		}
	}
}