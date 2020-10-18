package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedCheckbox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnCheckedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.CheckedProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.IconProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.WrapTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiCheckbox {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiCheckbox() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiCheckBoxBuilder create() {
		return new SuiCheckBoxBuilder();
	}




	public static class SuiCheckBoxBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiCheckBoxBuilder>,
			RegionBuilderExtension<SuiCheckBoxBuilder>,
			CommonEventBuilderExtension<SuiCheckBoxBuilder>,
			TextContentProperty.PropertyBuilderExtension<SuiCheckBoxBuilder>,
			IconProperty.PropertyBuilderExtension<SuiCheckBoxBuilder>,
			WrapTextProperty.PropertyBuilderExtension<SuiCheckBoxBuilder>,
			AlignmentProperty.PropertyBuilderExtension<SuiCheckBoxBuilder>,
			TooltipProperty.PropertyBuilderExtension<SuiCheckBoxBuilder>,
			CheckedProperty.PropertyBuilderExtension<SuiCheckBoxBuilder>,
			OnCheckedEventProperty.PropertyBuilderExtension<SuiCheckBoxBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiCheckbox.class,
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
		registry.registerBaseFxNodeBuilder(SuiCheckbox.class, new FxNodeBuilder());
		registry.registerProperties(SuiCheckbox.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiCheckbox.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiCheckbox.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiCheckbox.class, List.of(
				PropertyEntry.of(TextContentProperty.class, new TextContentProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(IconProperty.class, new IconProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(WrapTextProperty.class, new WrapTextProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(CheckedProperty.class, new CheckedProperty.CheckBoxUpdatingBuilder()),
				PropertyEntry.of(OnCheckedEventProperty.class, new OnCheckedEventProperty.CheckboxUpdatingBuilder()),
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<ExtendedCheckbox> {


		@Override
		public ExtendedCheckbox build(final SuiNode node) {
			return new ExtendedCheckbox();
		}

	}


}
