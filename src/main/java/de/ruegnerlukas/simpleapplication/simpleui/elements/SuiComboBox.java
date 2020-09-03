package de.ruegnerlukas.simpleapplication.simpleui.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.elements.jfxelements.SearchableComboBox;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ChoicesConverterProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ChoicesProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.EditableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SearchableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnSelectedItemEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.scene.control.ComboBox;

import java.util.Arrays;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.get;

public final class SuiComboBox {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiComboBox() {
		// do nothing
	}




	/**
	 * Creates a new combobox
	 *
	 * @param properties the properties
	 * @return the factory for a combobox
	 */
	public static NodeFactory comboBox(final Property... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		validateConflictSearchableEditable(properties);
		Properties.validate(SuiComboBox.class, get().getEntry(SuiComboBox.class).getProperties(), properties);
		return state -> new SuiNode(SuiComboBox.class, List.of(properties), state, null);
	}




	/**
	 * Check whether the properties contain a {@link SearchableProperty} and a {@link EditableProperty} which results in a conflict.
	 *
	 * @param properties the properties to check
	 */
	private static void validateConflictSearchableEditable(final Property... properties) {
		Validations.INPUT.notEqual(2,
				(int) Arrays.stream(properties)
						.map(Property::getKey)
						.filter(key -> key == SearchableProperty.class || key == EditableProperty.class)
						.count())
				.exception("Searchable-Property and Editable-Property exclude each other. Can not add both.");
	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SuiRegistry registry) {
		registry.registerBaseFxNodeBuilder(SuiComboBox.class, new FxNodeBuilder<>());
		registry.registerProperties(SuiComboBox.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiComboBox.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiComboBox.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiComboBox.class, List.of(
				PropertyEntry.of(ChoicesProperty.class, new ChoicesProperty.ComboBoxUpdatingBuilder<>()),
				PropertyEntry.of(ChoicesConverterProperty.class, new ChoicesConverterProperty.ComboBoxUpdatingBuilder<>()),
				PropertyEntry.of(OnSelectedItemEventProperty.class, new OnSelectedItemEventProperty.ComboBoxUpdatingBuilder<>()),
				PropertyEntry.of(EditableProperty.class, new EditableProperty.ComboBoxBaseUpdatingBuilder()),
				PropertyEntry.of(SearchableProperty.class, new SearchableProperty.UpdatingBuilder())
		));
	}




	private static class FxNodeBuilder<T> implements BaseFxNodeBuilder<ComboBox<T>> {


		@Override
		public ComboBox<T> build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
			if (SearchableProperty.isSearchable(node)) {
				return new SearchableComboBox<>();
			} else {
				return new ComboBox<>();
			}
		}

	}


}
