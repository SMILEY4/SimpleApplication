package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnValueChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.BlockIncrementProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.LabelFormatterProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MinMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.OrientationProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TickMarkProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.control.Slider;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiSlider {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiSlider() {
		// do nothing
	}




	/**
	 * Creates a new slider
	 *
	 * @param properties the properties
	 * @return the factory for a slider
	 */
	public static NodeFactory slider(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiSlider.class, properties);
		return (state, tags) -> SuiNode.create(
				SuiSlider.class,
				List.of(properties),
				state,
				tags
		);
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
				PropertyEntry.of(LabelFormatterProperty.class, new LabelFormatterProperty.SliderUpdatingBuilder()),
				PropertyEntry.of(OnValueChangedEventProperty.class, new OnValueChangedEventProperty.SliderUpdatingBuilder()),
				PropertyEntry.of(TickMarkProperty.class, new TickMarkProperty.SliderUpdatingBuilder()),
				PropertyEntry.of(OrientationProperty.class, new OrientationProperty.SliderUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<Slider> {


		@Override
		public Slider build(final SuiNode node) {
			final Slider slider = new Slider();
			slider.setShowTickMarks(TickMarkProperty.DEFAULT_STYLE.isShowTickMarks());
			slider.setShowTickLabels(TickMarkProperty.DEFAULT_STYLE.isShowLabels());
			slider.setMajorTickUnit(TickMarkProperty.DEFAULT_MAJOR_TICK_UNIT);
			slider.setMinorTickCount(TickMarkProperty.DEFAULT_MINOR_TICK_COUNT);
			slider.setSnapToTicks(TickMarkProperty.DEFAULT_SNAP_TO_TICKS);
			return slider;
		}

	}


}
