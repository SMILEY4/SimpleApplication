package de.ruegnerlukas.simpleapplication.simpleui.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiBaseNode;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ChronologyProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.EditableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PromptTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnSelectedDateEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.scene.control.DatePicker;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.get;

public final class SuiDatePicker {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiDatePicker() {
		// do nothing
	}




	/**
	 * Creates a new date picker
	 *
	 * @param properties the properties
	 * @return the factory for a date picker
	 */
	public static NodeFactory datePicker(final Property... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiDatePicker.class, get().getEntry(SuiDatePicker.class).getProperties(), properties);
		return state -> SuiBaseNode.create(
				SuiDatePicker.class,
				List.of(properties),
				state
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
				PropertyEntry.of(EditableProperty.class, new EditableProperty.ComboBoxBaseUpdatingBuilder()),
				PropertyEntry.of(OnSelectedDateEventProperty.class, new OnSelectedDateEventProperty.DatePickerUpdatingBuilder()),
				PropertyEntry.of(ChronologyProperty.class, new ChronologyProperty.DatePickerUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements BaseFxNodeBuilder<DatePicker> {


		@Override
		public DatePicker build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
			final DatePicker datePicker = new DatePicker();
			datePicker.setChronology(ChronologyProperty.DEFAULT);
			return datePicker;
		}

	}


}
