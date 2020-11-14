package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedChoiceBox;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events.OnValueChangedEventProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.ContentItemConverterProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.ContentItemsProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiChoiceBox {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiChoiceBox() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiChoiceBoxBuilder create() {
		return new SuiChoiceBoxBuilder();
	}




	public static class SuiChoiceBoxBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiChoiceBoxBuilder>,
			RegionBuilderExtension<SuiChoiceBoxBuilder>,
			CommonEventBuilderExtension<SuiChoiceBoxBuilder>,
			ContentItemsProperty.PropertyBuilderExtensionWithSelected<SuiChoiceBoxBuilder>,
			ContentItemConverterProperty.PropertyBuilderExtension<SuiChoiceBoxBuilder>,
			TooltipProperty.PropertyBuilderExtension<SuiChoiceBoxBuilder>,
			OnValueChangedEventProperty.PropertyBuilderExtension<SuiChoiceBoxBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiChoiceBox.class,
					state,
					tags
			);
		}


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
				PropertyEntry.of(ContentItemConverterProperty.class, new ContentItemConverterProperty.ChoiceBoxUpdatingBuilder<>()),
				PropertyEntry.of(OnValueChangedEventProperty.class, new OnValueChangedEventProperty.ChoiceBoxUpdatingBuilder<>()),
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder<T> implements AbstractFxNodeBuilder<ExtendedChoiceBox<T>> {


		@Override
		public ExtendedChoiceBox<T> build(final SuiNode node) {
			return new ExtendedChoiceBox<>();
		}

	}


}
