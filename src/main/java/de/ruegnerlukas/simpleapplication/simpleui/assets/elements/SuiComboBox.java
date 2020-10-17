package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedComboBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnValueChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ContentItemConverterProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ContentItemsProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.EditableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.PromptTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SearchableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;

import java.util.Arrays;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiComboBox {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiComboBox() {
		// do nothing
	}




	public static SuiComboBoxBuilder create() {
		return new SuiComboBoxBuilder();
	}




	/**
	 * Creates a new combobox
	 *
	 * @param properties the properties
	 * @return the factory for a combobox
	 */
	public static NodeFactory comboBox(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		validateConflictSearchableEditable(properties);
		Properties.validate(SuiComboBox.class, properties);
		return (state, tags) -> SuiNode.create(
				SuiComboBox.class,
				List.of(properties),
				state,
				tags
		);
	}




	/**
	 * Check whether the properties contain a {@link SearchableProperty} and a {@link EditableProperty} which results in a conflict.
	 *
	 * @param properties the properties to check
	 */
	private static void validateConflictSearchableEditable(final SuiProperty... properties) {
		long count = Arrays.stream(properties)
				.filter(property -> property.getKey() == SearchableProperty.class || property.getKey() == EditableProperty.class)
				.filter(property -> {
					if (property.getKey() == SearchableProperty.class) {
						return ((SearchableProperty) property).isSearchable();
					}
					if (property.getKey() == EditableProperty.class) {
						return ((EditableProperty) property).isEditable();
					}
					return false;
				}).count();
		Validations.INPUT.isLessThan(count, 2)
				.exception("Searchable-Property and Editable-Property exclude each other. Can not both be true.");
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
				PropertyEntry.of(ContentItemsProperty.class, new ContentItemsProperty.ComboBoxUpdatingBuilder<>()),
				PropertyEntry.of(ContentItemConverterProperty.class, new ContentItemConverterProperty.ComboBoxUpdatingBuilder<>()),
				PropertyEntry.of(EditableProperty.class, new EditableProperty.ComboBoxUpdatingBuilder()),
				PropertyEntry.of(SearchableProperty.class, new SearchableProperty.ComboBoxUpdatingBuilder()),
				PropertyEntry.of(PromptTextProperty.class, new PromptTextProperty.ComboBoxBaseUpdatingBuilder<>()),
				PropertyEntry.of(OnValueChangedEventProperty.class, new OnValueChangedEventProperty.ComboBoxUpdatingBuilder<>()),
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder<T> implements AbstractFxNodeBuilder<ExtendedComboBox<T>> {


		@Override
		public ExtendedComboBox<T> build(final SuiNode node) {
			return new ExtendedComboBox<>();
		}

	}


}
