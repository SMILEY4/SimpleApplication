package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.PropertyEntry;
import de.ruegnerlukas.simpleapplication.simpleui.utils.SuiUtils;
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
	public static NodeFactory anchorPane(final Property... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiAnchorPane.class, SuiRegistry.get().getEntry(SuiAnchorPane.class).getProperties(), properties);
		return state -> new SuiNode(
				SuiAnchorPane.class,
				List.of(properties),
				state,
				SuiUtils.defaultPaneChildListener(),
				SuiUtils.defaultPaneChildTransformListener());
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




	private static class AnchorPaneNodeBuilder implements BaseFxNodeBuilder<AnchorPane> {


		@Override
		public AnchorPane build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
			return new AnchorPane();
		}

	}


}
