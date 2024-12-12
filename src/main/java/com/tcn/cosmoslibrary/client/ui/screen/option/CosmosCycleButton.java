package com.tcn.cosmoslibrary.client.ui.screen.option;

import java.util.Collection;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
public class CosmosCycleButton<T> extends AbstractButton {
	static final BooleanSupplier DEFAULT_ALT_LIST_SELECTOR = Screen::hasAltDown;
	private static final List<Boolean> BOOLEAN_OPTIONS = ImmutableList.of(Boolean.TRUE, Boolean.FALSE);
	private final Component name;
	private int index;
	private T value;
	private final CosmosCycleButton.ValueListSupplier<T> values;
	private final Function<T, Component> valueStringifier;
	private final Function<CosmosCycleButton<T>, MutableComponent> narrationProvider;
	private final CosmosCycleButton.OnValueChange<T> onValueChange;
	private final CosmosOptionInstance.TooltipSupplier<T> tooltipSupplier;
	private final boolean displayOnlyValue;

	CosmosCycleButton(int p_232484_, int p_232485_, int p_232486_, int p_232487_, Component p_232488_, Component p_232489_, int p_232490_, T p_232491_, CosmosCycleButton.ValueListSupplier<T> p_232492_, Function<T, Component> p_232493_, Function<CosmosCycleButton<T>, MutableComponent> p_232494_, CosmosCycleButton.OnValueChange<T> p_232495_, CosmosOptionInstance.TooltipSupplier<T> p_232496_, boolean p_232497_) {
		super(p_232484_, p_232485_, p_232486_, p_232487_, p_232488_);
		this.name = p_232489_;
		this.index = p_232490_;
		this.value = p_232491_;
		this.values = p_232492_;
		this.valueStringifier = p_232493_;
		this.narrationProvider = p_232494_;
		this.onValueChange = p_232495_;
		this.tooltipSupplier = p_232496_;
		this.displayOnlyValue = p_232497_;
	}

	public void onPress() {
		if (Screen.hasShiftDown()) {
			this.cycleValue(-1);
		} else {
			this.cycleValue(1);
		}

	}

	private void cycleValue(int p_168909_) {
		List<T> list = this.values.getSelectedList();
		this.index = Mth.positiveModulo(this.index + p_168909_, list.size());
		T t = list.get(this.index);
		this.updateValue(t);
		this.onValueChange.onValueChange(this, t);
	}

	private T getCycledValue(int p_168915_) {
		List<T> list = this.values.getSelectedList();
		return list.get(Mth.positiveModulo(this.index + p_168915_, list.size()));
	}

	public boolean mouseScrolled(double p_168885_, double p_168886_, double p_168887_) {
		if (p_168887_ > 0.0D) {
			this.cycleValue(-1);
		} else if (p_168887_ < 0.0D) {
			this.cycleValue(1);
		}

		return true;
	}

	public void setValue(T p_168893_) {
		List<T> list = this.values.getSelectedList();
		int i = list.indexOf(p_168893_);
		if (i != -1) {
			this.index = i;
		}

		this.updateValue(p_168893_);
	}

	private void updateValue(T p_168906_) {
		Component component = this.createLabelForValue(p_168906_);
		this.setMessage(component);
		this.value = p_168906_;
	}

	private Component createLabelForValue(T p_168911_) {
		return (Component) (this.displayOnlyValue ? this.valueStringifier.apply(p_168911_) : this.createFullName(p_168911_));
	}

	private MutableComponent createFullName(T p_168913_) {
		return CommonComponents.optionNameValue(this.name, this.valueStringifier.apply(p_168913_));
	}

	public T getValue() {
		return this.value;
	}

	@Override
	protected MutableComponent createNarrationMessage() {
		return this.narrationProvider.apply(this);
	}

	@Override
	public void updateWidgetNarration(NarrationElementOutput p_168889_) {
		p_168889_.add(NarratedElementType.TITLE, this.createNarrationMessage());
		
		if (this.active) {
			T t = this.getCycledValue(1);
			Component component = this.createLabelForValue(t);
			if (this.isFocused()) {
				p_168889_.add(NarratedElementType.USAGE, Component.translatable("narration.cycle_button.usage.focused", component));
			} else {
				p_168889_.add(NarratedElementType.USAGE, Component.translatable("narration.cycle_button.usage.hovered", component));
			}
		}
	}

	public MutableComponent createDefaultNarrationMessage() {
		return wrapDefaultNarrationMessage((Component) (this.displayOnlyValue ? this.createFullName(this.value) : this.getMessage()));
	}

	public static <T> CosmosCycleButton.Builder<T> builder(Function<T, Component> p_168895_) {
		return new CosmosCycleButton.Builder<>(p_168895_);
	}

	public static CosmosCycleButton.Builder<Boolean> booleanBuilder(Component p_168897_, Component p_168898_) {
		return (new CosmosCycleButton.Builder<Boolean>((p_168902_) -> {
			return p_168902_ ? p_168897_ : p_168898_;
		})).withValues(BOOLEAN_OPTIONS);
	}

	public static CosmosCycleButton.Builder<Boolean> onOffBuilder() {
		return (new CosmosCycleButton.Builder<Boolean>((p_168891_) -> {
			return p_168891_ ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF;
		})).withValues(BOOLEAN_OPTIONS);
	}

	public static CosmosCycleButton.Builder<Boolean> onOffBuilder(boolean p_168917_) {
		return onOffBuilder().withInitialValue(p_168917_);
	}

	@OnlyIn(Dist.CLIENT)
	public static class Builder<T> {
		private int initialIndex;
		@Nullable
		private T initialValue;
		private final Function<T, Component> valueStringifier;
		private CosmosOptionInstance.TooltipSupplier<T> tooltipSupplier = (p_168964_) -> {
			return ImmutableList.of();
		};
		
		private Function<CosmosCycleButton<T>, MutableComponent> narrationProvider = CosmosCycleButton::createDefaultNarrationMessage;
		private CosmosCycleButton.ValueListSupplier<T> values = CosmosCycleButton.ValueListSupplier.create(ImmutableList.of());
		private boolean displayOnlyValue;

		public Builder(Function<T, Component> p_168928_) {
			this.valueStringifier = p_168928_;
		}

		public CosmosCycleButton.Builder<T> withValues(Collection<T> p_232503_) {
			return this.withValues(CosmosCycleButton.ValueListSupplier.create(p_232503_));
		}

		@SafeVarargs
		public final CosmosCycleButton.Builder<T> withValues(T... p_168962_) {
			return this.withValues(ImmutableList.copyOf(p_168962_));
		}

		public CosmosCycleButton.Builder<T> withValues(List<T> p_168953_, List<T> p_168954_) {
			return this.withValues(CosmosCycleButton.ValueListSupplier.create(CosmosCycleButton.DEFAULT_ALT_LIST_SELECTOR, p_168953_, p_168954_));
		}

		public CosmosCycleButton.Builder<T> withValues(BooleanSupplier p_168956_, List<T> p_168957_, List<T> p_168958_) {
			return this.withValues(CosmosCycleButton.ValueListSupplier.create(p_168956_, p_168957_, p_168958_));
		}

		public CosmosCycleButton.Builder<T> withValues(CosmosCycleButton.ValueListSupplier<T> p_232501_) {
			this.values = p_232501_;
			return this;
		}

		public CosmosCycleButton.Builder<T> withTooltip(CosmosOptionInstance.TooltipSupplier<T> p_232499_) {
			this.tooltipSupplier = p_232499_;
			return this;
		}

		public CosmosCycleButton.Builder<T> withInitialValue(T p_168949_) {
			this.initialValue = p_168949_;
			int i = this.values.getDefaultList().indexOf(p_168949_);
			if (i != -1) {
				this.initialIndex = i;
			}

			return this;
		}

		public CosmosCycleButton.Builder<T> withCustomNarration(Function<CosmosCycleButton<T>, MutableComponent> p_168960_) {
			this.narrationProvider = p_168960_;
			return this;
		}

		public CosmosCycleButton.Builder<T> displayOnlyValue() {
			this.displayOnlyValue = true;
			return this;
		}

		public CosmosCycleButton<T> create(int p_168931_, int p_168932_, int p_168933_, int p_168934_, MutableComponent p_168935_, String splitterIn) {
			return this.create(p_168931_, p_168932_, p_168933_, p_168934_, p_168935_, splitterIn, (p_168946_, p_168947_) -> { });
		}

		public CosmosCycleButton<T> create(int p_168937_, int p_168938_, int p_168939_, int p_168940_, MutableComponent captionIn, String splitterIn, CosmosCycleButton.OnValueChange<T> p_168942_) {
			List<T> list = this.values.getDefaultList();
			if (list.isEmpty()) {
				throw new IllegalStateException("No values for cycle button");
			} else {
				T t = (T) (this.initialValue != null ? this.initialValue : list.get(this.initialIndex));
				Component component = this.valueStringifier.apply(t);
				Component component1 = (Component) (this.displayOnlyValue ? component : captionIn.append(splitterIn).append(component)/*CommonComponents.optionNameValue(captionIn, component)*/);
				return new CosmosCycleButton<>(p_168937_, p_168938_, p_168939_, p_168940_, component1, captionIn, this.initialIndex, t, this.values, this.valueStringifier, this.narrationProvider, p_168942_, this.tooltipSupplier, this.displayOnlyValue);
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public interface OnValueChange<T> {
		void onValueChange(CosmosCycleButton<T> p_168966_, T p_168967_);
	}

	@OnlyIn(Dist.CLIENT)
	public interface ValueListSupplier<T> {
		List<T> getSelectedList();

		List<T> getDefaultList();

		static <T> CosmosCycleButton.ValueListSupplier<T> create(Collection<T> p_232505_) {
			final List<T> list = ImmutableList.copyOf(p_232505_);
			return new CosmosCycleButton.ValueListSupplier<T>() {
				public List<T> getSelectedList() {
					return list;
				}

				public List<T> getDefaultList() {
					return list;
				}
			};
		}

		static <T> CosmosCycleButton.ValueListSupplier<T> create(final BooleanSupplier p_168971_, List<T> p_168972_,
				List<T> p_168973_) {
			final List<T> list = ImmutableList.copyOf(p_168972_);
			final List<T> list1 = ImmutableList.copyOf(p_168973_);
			return new CosmosCycleButton.ValueListSupplier<T>() {
				public List<T> getSelectedList() {
					return p_168971_.getAsBoolean() ? list1 : list;
				}

				public List<T> getDefaultList() {
					return list;
				}
			};
		}
	}
}