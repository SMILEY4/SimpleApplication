package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnValueChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ChoicesConverterProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ChoicesProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SelectedItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.control.ChoiceBox;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.get;

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
		return state -> SuiBaseNode.create(
				SuiChoiceBox.class,
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




	private static class FxNodeBuilder<T> implements AbstractFxNodeBuilder<ChoiceBox<T>> {


		@Override
		public ChoiceBox<T> build(final SuiBaseNode node) {
			return new ChoiceBox<>();
		}

	}


}
