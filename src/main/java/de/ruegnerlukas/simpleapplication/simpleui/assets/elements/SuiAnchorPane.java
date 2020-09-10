package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildTransformListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;
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
	 * Creates a new anchor-pane node.
	 *
	 * @param properties the properties
	 * @return the factory for an anchor-pane node
	 */
	public static NodeFactory anchorPane(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiAnchorPane.class, SuiRegistry.get().getEntry(SuiAnchorPane.class).getProperties(), properties);
		return (state, tags) -> SuiNode.create(
				SuiAnchorPane.class,
				List.of(properties),
				state,
				tags,
				SuiNodeChildListener.DEFAULT,
				SuiNodeChildTransformListener.DEFAULT
		);
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
				PropertyEntry.of(ItemListProperty.class, new ItemListProperty.Builder(), null)
		));
	}




	private static class AnchorPaneNodeBuilder implements AbstractFxNodeBuilder<AnchorPane> {


		@Override
		public AnchorPane build(final SuiNode node) {
			return new AnchorPane();
		}

	}


}
