package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnSelectedDateEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ChronologyProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.EditableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.PromptTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.scene.control.DatePicker;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiDatePicker {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiDatePicker() {
		// do nothing
	}




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
			return SuiNode.create(
					SuiDatePicker.class,
					getFactoryInternalProperties(),
					state,
					tags
			);
		}


	}




	/**
	 * Creates a new date picker
	 *
	 * @param properties the properties
	 * @return the factory for a date picker
	 */
	public static NodeFactory datePicker(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiDatePicker.class, properties);
		return (state, tags) -> SuiNode.create(
				SuiDatePicker.class,
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
