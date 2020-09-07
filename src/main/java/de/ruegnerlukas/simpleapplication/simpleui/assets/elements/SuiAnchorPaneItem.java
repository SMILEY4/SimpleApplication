package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AnchorProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.CoreServices;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NoOpUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
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
	public static NodeFactory anchorPaneItem(final NodeFactory factory, final Property... properties) {
		final List<Property> list = new ArrayList<>();
		list.add(new ItemListProperty(factory));
		list.addAll(List.of(properties));
		return state -> SuiBaseNode.create(
				SuiAnchorPaneItem.class,
				list,
				state
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
				PropertyEntry.of(ItemListProperty.class, new NoOpUpdatingBuilder()),
				PropertyEntry.of(AnchorProperty.class, new AnchorProperty.UpdatingBuilder())
		));
	}




	public static class ChildItemNodeBuilder implements AbstractFxNodeBuilder<Node> {


		@Override
		public Node build(final SuiBaseNode node) {
			if (node.getChildNodeStore().hasChildren()) {
				SuiBaseNode childNode = node.getChildNodeStore().get(0);
				CoreServices.enrichWithFxNodes(childNode);
				return childNode.getFxNodeStore().get();
			} else {
				return null;
			}
		}

	}


}
