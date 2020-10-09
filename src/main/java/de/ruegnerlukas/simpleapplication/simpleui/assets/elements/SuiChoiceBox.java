package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedChoiceBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnValueChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ChoicesConverterProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ContentItemsProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SelectedItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

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
	public static NodeFactory choiceBox(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiChoiceBox.class, properties);
		return (state, tags) -> SuiNode.create(
				SuiChoiceBox.class,
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
		registry.registerBaseFxNodeBuilder(SuiChoiceBox.class, new FxNodeBuilder<>());
		registry.registerProperties(SuiChoiceBox.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiChoiceBox.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiChoiceBox.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiChoiceBox.class, List.of(
				PropertyEntry.of(ContentItemsProperty.class, new ContentItemsProperty.ChoiceBoxUpdatingBuilder<>()),
				PropertyEntry.of(ChoicesConverterProperty.class, new ChoicesConverterProperty.ChoiceBoxUpdatingBuilder<>()),
				PropertyEntry.of(SelectedItemProperty.class, new SelectedItemProperty.ChoiceBoxUpdatingBuilder<>()),
				PropertyEntry.of(OnValueChangedEventProperty.class, new OnValueChangedEventProperty.ChoiceBoxUpdatingBuilder<>())
		));
	}




	private static class FxNodeBuilder<T> implements AbstractFxNodeBuilder<ExtendedChoiceBox<T>> {


		@Override
		public ExtendedChoiceBox<T> build(final SuiNode node) {
			return new ExtendedChoiceBox<>();
		}

	}


}
