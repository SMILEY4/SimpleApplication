package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NoOpUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.AnchorProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.PropertyEntry;


public final class SuiAnchorPaneItem extends ChildItem {


	/**
	 * @param properties the properties
	 * @param state      the state
	 */
	public SuiAnchorPaneItem(final List<Property> properties, final SuiState state) {
		super(SuiAnchorPaneItem.class, properties, state, null);
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
		return state -> new SuiAnchorPaneItem(list, state);
	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SuiRegistry registry) {
		registry.registerBaseFxNodeBuilder(SuiAnchorPaneItem.class, new ChildItem.ChildItemNodeBuilder());
		registry.registerProperties(SuiAnchorPaneItem.class, List.of(
				PropertyEntry.of(ItemListProperty.class, new NoOpUpdatingBuilder()),
				PropertyEntry.of(AnchorProperty.class, new AnchorProperty.UpdatingBuilder())
		));
	}


}
