package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ChoicesConverterProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ChoicesProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SelectedItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnValueChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.scene.control.ChoiceBox;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.get;

public final class SuiChoiceBox {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiChoiceBox() {
		// do nothing
	}




	/**
	 * Creates a new button node
	 *
	 * @param properties the properties
	 * @return the factory for a choicebox node
	 */
	public static NodeFactory choiceBox(final Property... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiChoiceBox.class, get().getEntry(SuiChoiceBox.class).getProperties(), properties);
		return state -> new SuiNode(SuiChoiceBox.class, List.of(properties), state, null);
	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SuiRegistry registry) {
		registry.registerBaseFxNodeBuilder(SuiChoiceBox.class, new FxNodeBuilder<>());
		registry.registerProperties(SuiChoiceBox.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiChoiceBox.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiChoiceBox.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiChoiceBox.class, List.of(
				PropertyEntry.of(ChoicesProperty.class, new ChoicesProperty.ChoiceBoxUpdatingBuilder<>()),
				PropertyEntry.of(ChoicesConverterProperty.class, new ChoicesConverterProperty.ChoiceBoxUpdatingBuilder<>()),
				PropertyEntry.of(SelectedItemProperty.class, new SelectedItemProperty.ChoiceBoxUpdatingBuilder<>()),
				PropertyEntry.of(OnValueChangedEventProperty.class, new OnValueChangedEventProperty.ChoiceBoxUpdatingBuilder<>())
		));
	}




	private static class FxNodeBuilder<T> implements BaseFxNodeBuilder<ChoiceBox<T>> {


		@Override
		public ChoiceBox<T> build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
			return new ChoiceBox<>();
		}

	}


}
