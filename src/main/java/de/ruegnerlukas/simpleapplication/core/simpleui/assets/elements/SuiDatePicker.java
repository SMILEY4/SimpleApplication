package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events.OnSelectedDateEventProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.ChronologyProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.EditableProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.PromptTextProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import javafx.scene.control.DatePicker;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiDatePicker {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiDatePicker() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiDatePickerBuilder create() {
		return new SuiDatePickerBuilder();
	}




	public static class SuiDatePickerBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiDatePickerBuilder>,
			RegionBuilderExtension<SuiDatePickerBuilder>,
			CommonEventBuilderExtension<SuiDatePickerBuilder>,
			TooltipProperty.PropertyBuilderExtension<SuiDatePickerBuilder>,
			PromptTextProperty.PropertyBuilderExtension<SuiDatePickerBuilder>,
			EditableProperty.PropertyBuilderExtension<SuiDatePickerBuilder>,
			ChronologyProperty.PropertyBuilderExtension<SuiDatePickerBuilder>,
			OnSelectedDateEventProperty.PropertyBuilderExtension<SuiDatePickerBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiDatePicker.class,
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
		registry.registerBaseFxNodeBuilder(SuiDatePicker.class, new FxNodeBuilder());
		registry.registerProperties(SuiDatePicker.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiDatePicker.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiDatePicker.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiDatePicker.class, List.of(
				PropertyEntry.of(PromptTextProperty.class, new PromptTextProperty.ComboBoxBaseUpdatingBuilder<>()),
				PropertyEntry.of(EditableProperty.class, new EditableProperty.DatePickerUpdatingBuilder()),
				PropertyEntry.of(OnSelectedDateEventProperty.class, new OnSelectedDateEventProperty.DatePickerUpdatingBuilder()),
				PropertyEntry.of(ChronologyProperty.class, new ChronologyProperty.DatePickerUpdatingBuilder()),
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<DatePicker> {


		@Override
		public DatePicker build(final SuiNode node) {
			final DatePicker datePicker = new DatePicker();
			datePicker.setChronology(ChronologyProperty.DEFAULT);
			return datePicker;
		}

	}


}
