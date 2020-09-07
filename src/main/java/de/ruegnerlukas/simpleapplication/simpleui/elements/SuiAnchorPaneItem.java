package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NoOpUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiBaseNode;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.properties.AnchorProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.PropertyEntry;


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




	public static class ChildItemNodeBuilder implements BaseFxNodeBuilder<Node> {


		@Override
		public Node build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
			if (node.hasChildren()) {
				return nodeHandlers.getFxNodeBuilder().build(node.getChild(0));
			} else {
				return null;
			}
		}

	}


}
