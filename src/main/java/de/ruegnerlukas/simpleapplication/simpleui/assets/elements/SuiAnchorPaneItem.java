package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AnchorProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiServices;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NoOpUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;


public final class SuiAnchorPaneItem {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiAnchorPaneItem() {
		// do nothing
	}




	/**
	 * Creates a new anchor-pane child node
	 *
	 * @param properties the properties
	 * @return the factory for an anchor-pane child node
	 */
	public static NodeFactory anchorPaneItem(final NodeFactory factory, final SuiProperty... properties) {
		Validations.INPUT.notNull(factory).exception("The node factory may not be null.");
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiAnchorPaneItem.class, properties);
		final List<SuiProperty> list = new ArrayList<>();
		list.add(new ItemProperty(factory));
		list.addAll(List.of(properties));
		return (state, tags) -> SuiNode.create(
				SuiAnchorPaneItem.class,
				list,
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
		registry.registerBaseFxNodeBuilder(SuiAnchorPaneItem.class, new ChildItemNodeBuilder());
		registry.registerProperties(SuiAnchorPaneItem.class, List.of(
				PropertyEntry.of(ItemProperty.class, new NoOpUpdatingBuilder()),
				PropertyEntry.of(AnchorProperty.class, new AnchorProperty.UpdatingBuilder())
		));
	}




	public static class ChildItemNodeBuilder implements AbstractFxNodeBuilder<Node> {


		@Override
		public Node build(final SuiNode node) {
			if (node.getChildNodeStore().hasChildren()) {
				SuiNode childNode = node.getChildNodeStore().get(0);
				SuiServices.get().enrichWithFxNodes(childNode);
				return childNode.getFxNodeStore().get();
			} else {
				return null;
			}
		}

	}


}
