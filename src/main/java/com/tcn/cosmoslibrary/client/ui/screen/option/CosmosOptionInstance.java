package com.tcn.cosmoslibrary.client.ui.screen.option;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractOptionSliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.util.OptionEnum;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
public class CosmosOptionInstance<T> {
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final CosmosOptionInstance.Enum<Boolean> BOOLEAN_VALUES = new CosmosOptionInstance.Enum<>(ImmutableList.of(Boolean.TRUE, Boolean.FALSE), Codec.BOOL);
	private static final int TOOLTIP_WIDTH = 200;
	private final CosmosOptionInstance.TooltipSupplierFactory<T> tooltip;
	final Function<T, Component> toString;
	public final CosmosOptionInstance.ValueSet<T> values;
	private final Codec<T> codec;
	private final T initialValue;
	private final T defaultValue;
	private final Consumer<T> onValueUpdate;
	final MutableComponent caption;
	T value;
	private Component message;
	private boolean resetButton;
	private String splitter;

	public static CosmosOptionInstance<Boolean> createScreenSwitchOption(MutableComponent captionIn, Consumer<Boolean> consumerFunctionIn, String splitterIn) {
		return createScreenSwitchOption(captionIn, noTooltip(), consumerFunctionIn, splitterIn);
	}
	
	public static CosmosOptionInstance<Boolean> createScreenSwitchOption(MutableComponent captionIn, CosmosOptionInstance.TooltipSupplierFactory<Boolean> tooltipIn, Consumer<Boolean> consumerFunctionIn, String splitterIn) {
		return new CosmosOptionInstance<>(captionIn, tooltipIn, (comp, value) -> { return ComponentHelper.empty(); }, BOOLEAN_VALUES, false, false, consumerFunctionIn, false, splitterIn);
	}
	
	public static CosmosOptionInstance<Boolean> createBoolean(MutableComponent captionIn, boolean initialValueIn, Consumer<Boolean> consumerFunctionIn) {
		return createBoolean(captionIn, noTooltip(), initialValueIn, consumerFunctionIn, ":");
	}

	public static CosmosOptionInstance<Boolean> createBoolean(MutableComponent captionIn, boolean initialValueIn) {
		return createBoolean(captionIn, noTooltip(), initialValueIn, (value) -> { }, ":");
	}

	public static CosmosOptionInstance<Boolean> createBoolean(MutableComponent captionIn, CosmosOptionInstance.TooltipSupplierFactory<Boolean> tooltipIn, boolean initialValueIn) {
		return createBoolean(captionIn, tooltipIn, initialValueIn, (value) -> { }, ":");
	}

	public static CosmosOptionInstance<Boolean> createBoolean(MutableComponent captionIn, CosmosOptionInstance.TooltipSupplierFactory<Boolean> tooltipIn, boolean initialValueIn, Consumer<Boolean> consumerFunctionIn, String splitterIn) {
		return new CosmosOptionInstance<>(captionIn, tooltipIn, (comp, value) -> {
			return value ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF;
		}, BOOLEAN_VALUES, initialValueIn, false, consumerFunctionIn, false, splitterIn);
	}

	public static CosmosOptionInstance<Integer> createIntSlider(MutableComponent captionIn, CosmosOptionInstance.TooltipSupplierFactory<Integer> tooltipIn, Integer initialValue, Integer minValue, Integer maxValue, Integer normalValue, Consumer<Integer> consumerFunctionIn) {
		return createIntSlider(captionIn, tooltipIn, initialValue, minValue, maxValue, normalValue, ComponentColour.WHITE, ComponentHelper.empty(), ComponentHelper.empty(), ComponentHelper.empty(), consumerFunctionIn);
	}
	
	public static CosmosOptionInstance<Integer> createIntSlider(MutableComponent captionIn, CosmosOptionInstance.TooltipSupplierFactory<Integer> tooltipIn, Integer initialValue, Integer minValue, Integer maxValue, Integer normalValue, ComponentColour valueColour, MutableComponent minSuffix, MutableComponent normalSuffix, MutableComponent maxSuffix, Consumer<Integer> consumerFunctionIn) {
		return new CosmosOptionInstance<>(captionIn, tooltipIn, (comp, value) -> {
			return value == maxValue ? CosmosOptionInstance.genericValueLabel(comp, maxSuffix) : value == minValue ? CosmosOptionInstance.genericValueLabel(comp, minSuffix) : genericValueLabel(comp, ComponentHelper.style(valueColour, "" + value + " ").append(normalSuffix));
		}, (new CosmosOptionInstance.IntRange(minValue, maxValue)).xmap((valueTwo) -> {
			return valueTwo;
		}, (valueThree) -> {
			return valueThree;
		}), Codec.intRange(minValue, maxValue), initialValue, normalValue, consumerFunctionIn, true, ":");
	}
	
	public CosmosOptionInstance(MutableComponent captionIn, CosmosOptionInstance.TooltipSupplierFactory<T> tooltipIn, CosmosOptionInstance.CaptionBasedToString<T> toStringIn, CosmosOptionInstance.ValueSet<T> valueSetIn, T initialValueIn, T normalValueIn, Consumer<T> consumerFunctionIn, boolean resetButton, String splitterIn) {
		this(captionIn, tooltipIn, toStringIn, valueSetIn, valueSetIn.codec(), initialValueIn, normalValueIn, consumerFunctionIn, resetButton, splitterIn);
	}

	public CosmosOptionInstance(MutableComponent captionIn, CosmosOptionInstance.TooltipSupplierFactory<T> tooltipIn, CosmosOptionInstance.CaptionBasedToString<T> toStringIn, CosmosOptionInstance.ValueSet<T> valueSetIn, Codec<T> codecIn, T initialValueIn, T defaultValueIn, Consumer<T> consumerFunctionIn, boolean resetButton, String splitter) {
		this.caption = captionIn;
		this.tooltip = tooltipIn;
		this.toString = (t) -> {
			return toStringIn.toString(this.caption, t);
		};
		this.values = valueSetIn;
		this.codec = codecIn;
		this.initialValue = initialValueIn;
		this.defaultValue = defaultValueIn;
		this.onValueUpdate = consumerFunctionIn;
		this.value = this.initialValue;
		this.resetButton = resetButton;
		this.splitter = splitter;
	}

	public static <T> CosmosOptionInstance.TooltipSupplierFactory<T> noTooltip() {
		return (p_231500_) -> {
			return (p_231553_) -> {
				return ImmutableList.of();
			};
		};
	}

	public static <T> CosmosOptionInstance.TooltipSupplierFactory<T> cachedConstantTooltip(int widthIn, Component tooltipIn) {
		return (minecraft) -> {
			List<FormattedCharSequence> list = splitTooltip(minecraft, tooltipIn, widthIn);
			return (tObject) -> {
				return list;
			};
		};
	}

	public static <T> CosmosOptionInstance.TooltipSupplierFactory<T> getTooltipSplitComponent(MutableComponent... tooltipIn) {
		return getTooltipSplitComponent(200, tooltipIn);
	}

	public static <T> CosmosOptionInstance.TooltipSupplierFactory<T> getTooltipSplitComponent(int widthIn, MutableComponent... tooltipIn) {
		return (minecraft) -> {
			List<FormattedCharSequence> list = new ArrayList<FormattedCharSequence>();
			
			for (int i = 0; i < tooltipIn.length; i++) {				
				list.addAll(splitTooltip(minecraft, tooltipIn[i], widthIn));
			}
			
			return (tObject) -> {
				return list;
			};
		};
	}

	public static <T extends OptionEnum> CosmosOptionInstance.CaptionBasedToString<T> forOptionEnum() {
		return (p_231538_, p_231539_) -> {
			return p_231539_.getCaption();
		};
	}

	protected static List<FormattedCharSequence> splitTooltip(Minecraft minecraftIn, Component captionIn, int widthIn) {
		return minecraftIn.font.split(captionIn, widthIn);
	}

	public AbstractWidget createButton(CosmosOptions optionsIn, int xPosIn, int yPosIn, int widthIn, int heightIn) {
		CosmosOptionInstance.TooltipSupplier<T> tooltipsupplier = this.tooltip.apply(Minecraft.getInstance());
		return this.values.createButton(tooltipsupplier, optionsIn, xPosIn, yPosIn, this.resetButton ? (widthIn - heightIn - (heightIn / 2)) : widthIn, heightIn, this.getMessage(optionsIn), this.getSplitter()).apply(this);
	}

	public AbstractWidget createResetButton(CosmosOptions optionsIn, int xPosIn, int yPosIn, int widthIn, int heightIn) {
		CosmosOptionInstance.TooltipSupplier<T> tooltipsupplier = this.tooltip.apply(Minecraft.getInstance());
		return this.values.createResetButton(tooltipsupplier, optionsIn, xPosIn + widthIn - heightIn, yPosIn, heightIn, heightIn, ComponentHelper.style(ComponentColour.TURQUOISE, "R")).apply(this);
	}

	public MutableComponent getMessage(Options options) {
		return this.caption;
	}
	
	public String getSplitter() {
		return this.splitter;
	}
	
	public T get() {
		return this.value;
	}

	public Codec<T> codec() {
		return this.codec;
	}

	@Override
	public String toString() {
		return this.caption.getString();
	}
	
	public boolean hasResetButton() {
		return this.resetButton;
	}

	public void set(T valueIn) {
		T t = this.values.validateValue(valueIn).orElseGet(() -> {
			LOGGER.error("Illegal option value " + valueIn + " for " + this.caption);
			return this.initialValue;
		});
		if (!Minecraft.getInstance().isRunning()) {
			this.value = t;
		} else {
			if (!Objects.equals(this.value, t)) {
				this.value = t;
				this.onValueUpdate.accept(this.value);
			}
		}
	}

	public CosmosOptionInstance.ValueSet<T> values() {
		return this.values;
	}

	@OnlyIn(Dist.CLIENT)
	public static record AltEnum<T> (List<T> values, List<T> altValues, BooleanSupplier altCondition, CosmosOptionInstance.CycleableValueSet.ValueSetter<T> valueSetter, Codec<T> codec) implements CosmosOptionInstance.CycleableValueSet<T> {

		@Override
		public CosmosCycleButton.ValueListSupplier<T> valueListSupplier() {
			return CosmosCycleButton.ValueListSupplier.create(this.altCondition, this.values, this.altValues);
		}

		@Override
		public Optional<T> validateValue(T p_231570_) {
			return (this.altCondition.getAsBoolean() ? this.altValues : this.values).contains(p_231570_)
					? Optional.of(p_231570_)
					: Optional.empty();
		}

		@Override
		public CosmosOptionInstance.CycleableValueSet.ValueSetter<T> valueSetter() {
			return this.valueSetter;
		}

		@Override
		public Codec<T> codec() {
			return this.codec;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public interface CaptionBasedToString<T> {
		Component toString(Component p_231581_, T p_231582_);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static record ClampingLazyMaxIntRange(int minInclusive, IntSupplier maxSupplier) implements CosmosOptionInstance.IntRangeBase, CosmosOptionInstance.SliderableOrCyclableValueSet<Integer> {

		@Override
		public Optional<Integer> validateValue(Integer p_231590_) {
			return Optional.of(Mth.clamp(p_231590_, this.minInclusive(), this.maxInclusive()));
		}

		@Override
		public int maxInclusive() {
			return this.maxSupplier.getAsInt();
		}

		@Override
		public Codec<Integer> codec() {
			Function<Integer, DataResult<Integer>> function = (p_231596_) -> {
				int i = this.maxSupplier.getAsInt() + 1;
				return p_231596_.compareTo(this.minInclusive) >= 0 && p_231596_.compareTo(i) <= 0 ? DataResult.success(p_231596_) : DataResult.error(() -> { return "Value " + p_231596_ + " outside of range [" + this.minInclusive + ":" + i + "]"; }, p_231596_);
			};
			return Codec.INT.flatXmap(function, function);
		}

		@Override
		public boolean createCosmosCycleButton() {
			return true;
		}

		@Override
		public CosmosCycleButton.ValueListSupplier<Integer> valueListSupplier() {
			return CosmosCycleButton.ValueListSupplier.create(IntStream.range(this.minInclusive, this.maxInclusive() + 1).boxed().toList());
		}

		@Override
		public int minInclusive() {
			return this.minInclusive;
		}
	}

	@OnlyIn(Dist.CLIENT)
	interface CycleableValueSet<T> extends CosmosOptionInstance.ValueSet<T> {
		CosmosCycleButton.ValueListSupplier<T> valueListSupplier();

		default CosmosOptionInstance.CycleableValueSet.ValueSetter<T> valueSetter() {
			return CosmosOptionInstance::set;
		}

		@Override
		default Function<CosmosOptionInstance<T>, AbstractWidget> createButton(CosmosOptionInstance.TooltipSupplier<T> toolTipIn, CosmosOptions optionsIn, int xPosIn, int yPosIn, int widthIn, int heightIn, MutableComponent messageIn, String splitterIn) {
			return (instance) -> {
				return CosmosCycleButton.builder(instance.toString).withValues(this.valueListSupplier()).withTooltip(toolTipIn).withInitialValue(instance.value).create(xPosIn, yPosIn, widthIn, heightIn, instance.caption, splitterIn, (button, tObject) -> {
					this.valueSetter().set(instance, tObject);
					optionsIn.save();
				});
			};
		}
		
		@Override
		default Function<CosmosOptionInstance<T>, AbstractWidget> createResetButton(CosmosOptionInstance.TooltipSupplier<T> toolTipIn, CosmosOptions optionsIn, int xPosIn, int yPosIn, int widthIn, int heightIn, MutableComponent messageIn) {
			return (instance) -> {
				Button.Builder builder = new Button.Builder(messageIn, (button) -> {
					instance.set(instance.defaultValue);
					optionsIn.save();
				});
				builder.pos(xPosIn, yPosIn);
				builder.size(widthIn, heightIn);
				
				return builder.build();
			};
		}

		@OnlyIn(Dist.CLIENT)
		public interface ValueSetter<T> {
			void set(CosmosOptionInstance<T> p_231623_, T p_231624_);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static record Enum<T> (List<T> values, Codec<T> codec) implements CosmosOptionInstance.CycleableValueSet<T> {

		@Override
		public Optional<T> validateValue(T p_231632_) {
			return this.values.contains(p_231632_) ? Optional.of(p_231632_) : Optional.empty();
		}

		@Override
		public CosmosCycleButton.ValueListSupplier<T> valueListSupplier() {
			return CosmosCycleButton.ValueListSupplier.create(this.values);
		}

		@Override
		public Codec<T> codec() {
			return this.codec;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static record IntRange(int minInclusive, int maxInclusive) implements CosmosOptionInstance.IntRangeBase {

		@Override
		public Optional<Integer> validateValue(Integer p_231645_) {
			return p_231645_.compareTo(this.minInclusive()) >= 0 && p_231645_.compareTo(this.maxInclusive()) <= 0 ? Optional.of(p_231645_) : Optional.empty();
		}

		@Override
		public Codec<Integer> codec() {
			return Codec.intRange(this.minInclusive, this.maxInclusive + 1);
		}

		@Override
		public int minInclusive() {
			return this.minInclusive;
		}

		@Override
		public int maxInclusive() {
			return this.maxInclusive;
		}
	}

	@OnlyIn(Dist.CLIENT)
	interface IntRangeBase extends CosmosOptionInstance.SliderableValueSet<Integer> {
		int minInclusive();

		int maxInclusive();

		@Override
		default double toSliderValue(Integer p_231663_) {
			return (double) Mth.map((float) p_231663_.intValue(), (float) this.minInclusive(),
					(float) this.maxInclusive(), 0.0F, 1.0F);
		}

		@Override
		default Integer fromSliderValue(double p_231656_) {
			return Mth.floor(Mth.map(p_231656_, 0.0D, 1.0D, (double) this.minInclusive(), (double) this.maxInclusive()));
		}

		default <R> CosmosOptionInstance.SliderableValueSet<R> xmap(final IntFunction<? extends R> p_231658_, final ToIntFunction<? super R> p_231659_) {
			return new CosmosOptionInstance.SliderableValueSet<R>() {

				@Override
				public Optional<R> validateValue(R p_231674_) {
					return IntRangeBase.this.validateValue(Integer.valueOf(p_231659_.applyAsInt(p_231674_)))
							.map(p_231658_::apply);
				}

				@Override
				public double toSliderValue(R p_231678_) {
					return IntRangeBase.this.toSliderValue(p_231659_.applyAsInt(p_231678_));
				}

				@Override
				public R fromSliderValue(double p_231676_) {
					return p_231658_.apply(IntRangeBase.this.fromSliderValue(p_231676_));
				}

				@Override
				public Codec<R> codec() {
					return IntRangeBase.this.codec().xmap(p_231658_::apply, p_231659_::applyAsInt);
				}
			};
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static record LazyEnum<T> (Supplier<List<T>> values, Function<T, Optional<T>> validateValue, Codec<T> codec) implements CosmosOptionInstance.CycleableValueSet<T> {
		
		@Override
		public Optional<T> validateValue(T p_231689_) {
			return this.validateValue.apply(p_231689_);
		}

		@Override
		public CosmosCycleButton.ValueListSupplier<T> valueListSupplier() {
			return CosmosCycleButton.ValueListSupplier.create(this.values.get());
		}

		@Override
		public Codec<T> codec() {
			return this.codec;
		}
	}

	@OnlyIn(Dist.CLIENT)
	static final class OptionInstanceSliderButton<N> extends AbstractOptionSliderButton {
		private final CosmosOptionInstance<N> instance;
		private final CosmosOptionInstance.SliderableValueSet<N> values;
		private final CosmosOptionInstance.TooltipSupplier<N> tooltip;

		OptionInstanceSliderButton(CosmosOptions optionsIn, int xPos, int yPos, int widthIn, int heightIn, CosmosOptionInstance<N> instanceIn, CosmosOptionInstance.SliderableValueSet<N> valueSetIn, CosmosOptionInstance.TooltipSupplier<N> tooltipIn) {
			super(optionsIn, xPos, yPos, widthIn, heightIn, valueSetIn.toSliderValue(instanceIn.get()));
			this.instance = instanceIn;
			this.values = valueSetIn;
			this.tooltip = tooltipIn;
			this.updateMessage();
		}

		@Override
		protected void updateMessage() {
			this.setMessage(this.instance.toString.apply(this.instance.get()));
		}

		@Override
		protected void applyValue() {
			this.instance.set(this.values.fromSliderValue(this.value));
			this.options.save();
		}
		/*
		@Override
		public List<FormattedCharSequence> tooltip() {
			return this.tooltip.apply(this.values.fromSliderValue(this.value));
		}*/
	}

	@OnlyIn(Dist.CLIENT)
	interface SliderableOrCyclableValueSet<T> extends CosmosOptionInstance.CycleableValueSet<T>, CosmosOptionInstance.SliderableValueSet<T> {
		boolean createCosmosCycleButton();
		
		@Override
		default Function<CosmosOptionInstance<T>, AbstractWidget> createButton(CosmosOptionInstance.TooltipSupplier<T> tooltipIn, CosmosOptions optionsIn, int xPosIn, int yPosIn, int widthIn, int heightIn, MutableComponent messageIn, String splitterIn) {
			return this.createCosmosCycleButton() ? CosmosOptionInstance.CycleableValueSet.super.createButton(tooltipIn, optionsIn, xPosIn, yPosIn, widthIn, heightIn, messageIn, splitterIn) : CosmosOptionInstance.SliderableValueSet.super.createButton(tooltipIn, optionsIn, xPosIn, yPosIn, widthIn, heightIn, messageIn, splitterIn);
		}
		
		@Override
		default Function<CosmosOptionInstance<T>, AbstractWidget> createResetButton(CosmosOptionInstance.TooltipSupplier<T> tooltipIn, CosmosOptions optionsIn, int xPosIn, int yPosIn, int widthIn, int heightIn, MutableComponent messageIn) {
			return this.createCosmosCycleButton() ? CosmosOptionInstance.CycleableValueSet.super.createResetButton(tooltipIn, optionsIn, xPosIn, yPosIn, widthIn, heightIn, messageIn) : CosmosOptionInstance.SliderableValueSet.super.createResetButton(tooltipIn, optionsIn, xPosIn, yPosIn, widthIn, heightIn, messageIn);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public interface SliderableValueSet<T> extends CosmosOptionInstance.ValueSet<T> {
		double toSliderValue(T p_231732_);

		T fromSliderValue(double p_231731_);

		@Override
		default Function<CosmosOptionInstance<T>, AbstractWidget> createButton(CosmosOptionInstance.TooltipSupplier<T> tooltipIn, CosmosOptions optionsIn, int xPosIn, int yPosIn, int widthIn, int heightIn, MutableComponent messageIn, String splitterIn) {
			return (instance) -> {
				return new CosmosOptionInstance.OptionInstanceSliderButton<>(optionsIn, xPosIn, yPosIn, widthIn, heightIn, instance, this, tooltipIn);
			};
		}
		
		@Override
		default Function<CosmosOptionInstance<T>, AbstractWidget> createResetButton(CosmosOptionInstance.TooltipSupplier<T> tooltipIn, CosmosOptions optionsIn, int xPosIn, int yPosIn, int widthIn, int heightIn, MutableComponent messageIn) {
			return (instance) -> {
				Button.Builder builder = new Button.Builder(messageIn, (button) -> {
					instance.set(instance.defaultValue);
					optionsIn.save();
				});
				builder.pos(xPosIn, yPosIn);
				builder.size(widthIn, heightIn);
				
				return builder.build();
			};
		}
	}

	@FunctionalInterface
	@OnlyIn(Dist.CLIENT)
	public interface TooltipSupplier<T> extends Function<T, List<FormattedCharSequence>> { }

	@OnlyIn(Dist.CLIENT)
	public interface TooltipSupplierFactory<T> extends Function<Minecraft, CosmosOptionInstance.TooltipSupplier<T>> { }

	@OnlyIn(Dist.CLIENT)
	public static enum UnitDouble implements CosmosOptionInstance.SliderableValueSet<Double> {
		INSTANCE;

		@Override
		public Optional<Double> validateValue(Double p_231747_) {
			return p_231747_ >= 0.0D && p_231747_ <= 1.0D ? Optional.of(p_231747_) : Optional.empty();
		}

		@Override
		public double toSliderValue(Double p_231756_) {
			return p_231756_;
		}

		@Override
		public Double fromSliderValue(double p_231741_) {
			return p_231741_;
		}

		public <R> CosmosOptionInstance.SliderableValueSet<R> xmap(final DoubleFunction<? extends R> p_231751_, final ToDoubleFunction<? super R> p_231752_) {
			return new CosmosOptionInstance.SliderableValueSet<R>() {

				@Override
				public Optional<R> validateValue(R p_231773_) {
					return UnitDouble.this.validateValue(p_231752_.applyAsDouble(p_231773_)).map(p_231751_::apply);
				}

				@Override
				public double toSliderValue(R p_231777_) {
					return UnitDouble.this.toSliderValue(p_231752_.applyAsDouble(p_231777_));
				}

				@Override
				public R fromSliderValue(double p_231775_) {
					return p_231751_.apply(UnitDouble.this.fromSliderValue(p_231775_));
				}

				@Override
				public Codec<R> codec() {
					return UnitDouble.this.codec().xmap(p_231751_::apply, p_231752_::applyAsDouble);
				}
			};
		}

		@Override
		public Codec<Double> codec() {
			return Codec.either(Codec.doubleRange(0.0D, 1.0D), Codec.BOOL).xmap((p_231743_) -> {
				return p_231743_.map((p_231760_) -> {
					return p_231760_;
				}, (p_231745_) -> {
					return p_231745_ ? 1.0D : 0.0D;
				});
			}, Either::left);
		}
	}

	@OnlyIn(Dist.CLIENT)
	interface ValueSet<T> {
		Function<CosmosOptionInstance<T>, AbstractWidget> createButton(CosmosOptionInstance.TooltipSupplier<T> p_231779_, CosmosOptions p_231780_, int xPos, int yPos, int widthIn, int heightIn, MutableComponent messageIn, String splitterIn);
		
		Function<CosmosOptionInstance<T>, AbstractWidget> createResetButton(CosmosOptionInstance.TooltipSupplier<T> p_231779_, CosmosOptions p_231780_, int xPos, int yPos, int widthIn, int heightIn, MutableComponent messageIn);
		
		Optional<T> validateValue(T p_231784_);
		
		Codec<T> codec();
	}

	public static Component genericValueLabel(Component p_231922_, Component p_231923_) {
		return Component.translatable("options.generic_value", p_231922_, p_231923_);
	}

	public static Component genericValueLabel(Component p_231901_, int p_231902_) {
		return genericValueLabel(p_231901_, Component.literal(Integer.toString(p_231902_)));
	}
	
}