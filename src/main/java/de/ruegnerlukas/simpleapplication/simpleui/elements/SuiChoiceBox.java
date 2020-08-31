package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ChoiceBoxConverterProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ChoicesProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnSelectedIndexEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnSelectedItemEventProperty;
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
				PropertyEntry.of(ChoiceBoxConverterProperty.class, new ChoiceBoxConverterProperty.ChoiceBoxUpdatingBuilder<>()),
				PropertyEntry.of(OnSelectedItemEventProperty.class, new OnSelectedItemEventProperty.ChoiceBoxUpdatingBuilder<>()),
				PropertyEntry.of(OnSelectedIndexEventProperty.class, new OnSelectedIndexEventProperty.ChoiceBoxUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder<T> implements BaseFxNodeBuilder<ChoiceBox<T>> {


		@Override
		public ChoiceBox<T> build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
			return new ChoiceBox<>();
		}

	}


}