package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.Node;

import java.util.List;

public final class SuiSplitPane {




	/**
	 * Hidden constructor for utility classes
	 */
	private SuiSplitPane() {
		// do nothing
	}




	/**
	 * Creates a new split pane
	 *
	 * @param properties the properties
	 * @return the factory for a split pane
	 */
	public static NodeFactory splitPane(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiSplitPane.class, properties);
		return (state, tags) -> SuiNode.create(
				SuiSplitPane.class,
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
		registry.registerBaseFxNodeBuilder(SuiSplitPane.class, new FxNodeBuilder());
		registry.registerProperties(SuiSplitPane.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiSplitPane.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiSplitPane.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiSplitPane.class, List.of(
				// todo: vert or horz line (orientation)
				// todo: items (any amount)
				// todo: divider positions
				// todo: move divider listener (-> with index when more than one divider)
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<Node> {


		@Override
		public Node build(final SuiNode node) {
			return null;
		}

	}


}
