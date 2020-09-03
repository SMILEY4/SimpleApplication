package de.ruegnerlukas.simpleapplication.simpleui.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.BlockIncrementProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.LabelFormatterProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.MinMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.properties.TickMarkProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnSelectedItemEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.scene.control.Slider;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.get;

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
	public static NodeFactory slider(final Property... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiSlider.class, get().getEntry(SuiSlider.class).getProperties(), properties);
		return state -> new SuiNode(SuiSlider.class, List.of(properties), state, null);
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
				PropertyEntry.of(OnSelectedItemEventProperty.class, new OnSelectedItemEventProperty.SliderUpdatingBuilder()),
				PropertyEntry.of(TickMarkProperty.class, new TickMarkProperty.SliderUpdatingBuilder())
				/*
				 * ticks (none, marks, labels, both) + minor,major tick unit + snap to ticks
				 */
		));
	}




	private static class FxNodeBuilder implements BaseFxNodeBuilder<Slider> {


		@Override
		public Slider build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
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
