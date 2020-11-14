package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedPane;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.LayoutProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNodeChildListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNodeChildTransformListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry.PropertyEntry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;

import java.util.List;


public final class SuiContainer {


	/**
	 * Hidden constructor for utility classes.
	 */
	private SuiContainer() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiContainerBuilder create() {
		return new SuiContainerBuilder();
	}




	public static class SuiContainerBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiContainerBuilder>,
			RegionBuilderExtension<SuiContainerBuilder>,
			CommonEventBuilderExtension<SuiContainerBuilder>,
			ItemListProperty.PropertyBuilderExtension<SuiContainerBuilder>,
			ItemProperty.PropertyBuilderExtension<SuiContainerBuilder>,
			LayoutProperty.PropertyBuilderExtension<SuiContainerBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiContainer.class,
					state,
					tags,
					SuiNodeChildListener.DEFAULT,
					SuiNodeChildTransformListener.DEFAULT
			);
		}


	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SuiRegistry registry) {

		registry.registerBaseFxNodeBuilder(SuiContainer.class, new FxNodeBuilder());
		registry.registerProperties(SuiContainer.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiContainer.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiContainer.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiContainer.class, List.of(
				PropertyEntry.of(ItemListProperty.class, new ItemListProperty.PaneBuilder(), null),
				PropertyEntry.of(ItemProperty.class, new ItemProperty.PaneBuilder(), null),
				PropertyEntry.of(LayoutProperty.class, new LayoutProperty.UpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<ExtendedPane> {


		@Override
		public ExtendedPane build(final SuiNode node) {
			return new ExtendedPane();
		}

	}


}
