package de.ruegnerlukas.simpleapplication.simpleui.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ChoicesProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnSelectedIndexEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnSelectedItemEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.scene.control.ComboBox;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.get;

public final class SuiTextComboBox {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiTextComboBox() {
		// do nothing
	}




	/**
	 * Creates a new text combobox
	 *
	 * @param properties the properties
	 * @return the factory for a text combobox
	 */
	public static NodeFactory textComboBox(final Property... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiTextComboBox.class, get().getEntry(SuiTextComboBox.class).getProperties(), properties);
		return state -> new SuiNode(SuiTextComboBox.class, List.of(properties), state, null);
	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SuiRegistry registry) {
		registry.registerBaseFxNodeBuilder(SuiTextComboBox.class, new FxNodeBuilder());
		registry.registerProperties(SuiTextComboBox.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiTextComboBox.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiTextComboBox.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiTextComboBox.class, List.of(
				PropertyEntry.of(ChoicesProperty.class, new ChoicesProperty.TextComboBoxUpdatingBuilder()),
				PropertyEntry.of(OnSelectedItemEventProperty.class, new OnSelectedItemEventProperty.TextComboBoxUpdatingBuilder()),
				PropertyEntry.of(OnSelectedIndexEventProperty.class, new OnSelectedIndexEventProperty.TextComboBoxUpdatingBuilder())
				/*
				todo
				isSearchable (see test app)
				isEditable (clashes with searchable ?)
				 */
		));
	}




	private static class FxNodeBuilder implements BaseFxNodeBuilder<ComboBox<String>> {


		@Override
		public ComboBox<String> build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
			return new ComboBox<>();
		}

	}


}
