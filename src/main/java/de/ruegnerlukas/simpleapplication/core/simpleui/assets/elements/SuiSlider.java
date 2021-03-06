package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedSlider;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events.OnValueChangedEventProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.BlockIncrementProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.LabelFormatterProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.MinMaxProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.OrientationProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TickMarkProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiSlider {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiSlider() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiSliderBuilder create() {
		return new SuiSliderBuilder();
	}




	public static class SuiSliderBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiSliderBuilder>,
			RegionBuilderExtension<SuiSliderBuilder>,
			CommonEventBuilderExtension<SuiSliderBuilder>,
			TooltipProperty.PropertyBuilderExtension<SuiSliderBuilder>,
			MinMaxProperty.PropertyBuilderExtension<SuiSliderBuilder>,
			BlockIncrementProperty.PropertyBuilderExtension<SuiSliderBuilder>,
			TickMarkProperty.PropertyBuilderExtension<SuiSliderBuilder>,
			OrientationProperty.PropertyBuilderExtension<SuiSliderBuilder>,
			OnValueChangedEventProperty.PropertyBuilderExtension<SuiSliderBuilder>,
			LabelFormatterProperty.PropertyBuilderExtension<SuiSliderBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiSlider.class,
					state,
					tags
			);
		}


	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SuiRegistry registry) {
		registry.registerBaseFxNodeBuilder(SuiSlider.class, new FxNodeBuilder());
		registry.registerProperties(SuiSlider.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiSlider.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiSlider.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiSlider.class, List.of(
				PropertyEntry.of(MinMaxProperty.class, new MinMaxProperty.SliderUpdatingBuilder()),
				PropertyEntry.of(BlockIncrementProperty.class, new BlockIncrementProperty.SliderUpdatingBuilder()),
				PropertyEntry.of(TickMarkProperty.class, new TickMarkProperty.SliderUpdatingBuilder()),
				PropertyEntry.of(OrientationProperty.class, new OrientationProperty.SliderUpdatingBuilder()),
				PropertyEntry.of(LabelFormatterProperty.class, new LabelFormatterProperty.SliderUpdatingBuilder()),
				PropertyEntry.of(OnValueChangedEventProperty.class, new OnValueChangedEventProperty.SliderUpdatingBuilder()),
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<ExtendedSlider> {


		@Override
		public ExtendedSlider build(final SuiNode node) {
			final ExtendedSlider slider = new ExtendedSlider();
			slider.setShowTickMarks(TickMarkProperty.DEFAULT_STYLE.isShowTickMarks());
			slider.setShowTickLabels(TickMarkProperty.DEFAULT_STYLE.isShowLabels());
			slider.setMajorTickUnit(TickMarkProperty.DEFAULT_MAJOR_TICK_UNIT);
			slider.setMinorTickCount(TickMarkProperty.DEFAULT_MINOR_TICK_COUNT);
			slider.setSnapToTicks(TickMarkProperty.DEFAULT_SNAP_TO_TICKS);
			return slider;
		}

	}


}
