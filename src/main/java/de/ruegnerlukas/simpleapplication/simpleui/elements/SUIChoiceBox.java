package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ChoiceBoxConverterProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ChoiceBoxListenerProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ChoicesProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.scene.control.ChoiceBox;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.get;

public final class SUIChoiceBox {


	/**
	 * Hidden constructor for utility classes
	 */
	private SUIChoiceBox() {
		// do nothing
	}




	/**
	 * Creates a new button node
	 *
	 * @param properties the properties
	 * @return the factory for a choicebox node
	 */
	public static NodeFactory choiceBox(final Property... properties) {
		Properties.checkIllegal(SUIChoiceBox.class, get().getEntry(SUIChoiceBox.class).getProperties(), properties);
		return state -> new SUINode(SUIChoiceBox.class, List.of(properties), state, null);
	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SUIChoiceBox.class, new ChoiceBoxNodeBuilder<>());
		registry.registerProperties(SUIChoiceBox.class, PropertyGroups.commonProperties());
		registry.registerProperties(SUIChoiceBox.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SUIChoiceBox.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SUIChoiceBox.class, List.of(
				PropertyEntry.of(ChoicesProperty.class, new ChoicesProperty.ChoicesPropertyUpdatingBuilder<>()),
				PropertyEntry.of(ChoiceBoxListenerProperty.class,
						new ChoiceBoxListenerProperty.CBListenerUpdatingBuilder<>()),
				PropertyEntry.of(ChoiceBoxConverterProperty.class,
						new ChoiceBoxConverterProperty.CBConverterUpdatingBuilder<>())
		));
	}




	private static class ChoiceBoxNodeBuilder<T> implements BaseFxNodeBuilder<ChoiceBox<T>> {


		@Override
		public ChoiceBox<T> build(final MasterNodeHandlers nodeHandlers, final SUINode node) {
			return new ChoiceBox<>();
		}

	}


}
