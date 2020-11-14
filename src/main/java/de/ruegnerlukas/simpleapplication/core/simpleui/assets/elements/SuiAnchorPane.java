package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNodeChildListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNodeChildTransformListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry.PropertyEntry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import javafx.scene.layout.AnchorPane;

import java.util.List;


public final class SuiAnchorPane {


	/**
	 * Hidden constructor for utility classes.
	 */
	private SuiAnchorPane() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiAnchorPaneBuilder build() {
		return new SuiAnchorPaneBuilder();
	}




	public static class SuiAnchorPaneBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiAnchorPaneBuilder>,
			RegionBuilderExtension<SuiAnchorPaneBuilder>,
			CommonEventBuilderExtension<SuiAnchorPaneBuilder>,
			ItemListProperty.PropertyBuilderExtension<SuiAnchorPaneBuilder>,
			ItemProperty.PropertyBuilderExtension<SuiAnchorPaneBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiAnchorPane.class,
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
		registry.registerBaseFxNodeBuilder(SuiAnchorPane.class, new AnchorPaneNodeBuilder());
		registry.registerProperties(SuiAnchorPane.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiAnchorPane.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiAnchorPane.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiAnchorPane.class, List.of(
				PropertyEntry.of(ItemListProperty.class, new ItemListProperty.PaneBuilder(), null),
				PropertyEntry.of(ItemProperty.class, new ItemProperty.PaneBuilder(), null)
		));
	}




	private static class AnchorPaneNodeBuilder implements AbstractFxNodeBuilder<AnchorPane> {


		@Override
		public AnchorPane build(final SuiNode node) {
			return new AnchorPane();
		}

	}


}
