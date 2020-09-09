package de.ruegnerlukas.simpleapplication.simpleui.core.node;

import de.ruegnerlukas.simpleapplication.common.utils.LoopUtils;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChildNodeBuilder {


	/**
	 * The default child node builder instance.
	 */
	public static final ChildNodeBuilder DEFAULT = new ChildNodeBuilder();


	/**
	 * The amount of child nodes that have to be created to use the async processing.
	 */
	private static final int CREATE_CHILD_LIST_ASYNC_CUTOFF = 1028;




	/**
	 * Creates child nodes from the given properties of the (parent) node.
	 *
	 * @param properties all properties of the (parent) node
	 * @return the created child nodes
	 */
	public List<SuiBaseNode> build(final SuiState state, final List<Property> properties) {
		for (Property property : properties) {
			if (property.getKey() == ItemListProperty.class) {
				return fromItemListProperty(state, (ItemListProperty) property);
			}
			if (property.getKey() == ItemProperty.class) {
				return fromItemProperty(state, (ItemProperty) property);
			}
		}
		return List.of();
	}




	/**
	 * Create the child nodes from given item list property and the given state.
	 *
	 * @param state    the current state
	 * @param property the item list property
	 */
	private List<SuiBaseNode> fromItemListProperty(final SuiState state, final ItemListProperty property) {
		if (property.getFactories().size() < CREATE_CHILD_LIST_ASYNC_CUTOFF) {
			return property.getFactories().stream()
					.map(factory -> factory.create(state))
					.collect(Collectors.toList());
		} else {
			return LoopUtils.asyncCollectingLoop(property.getFactories(), factory -> factory.create(state));
		}
	}




	/**
	 * Create the child nodes from given item property and the given state.
	 *
	 * @param state    the current state
	 * @param property the item property
	 */
	private List<SuiBaseNode> fromItemProperty(final SuiState state, final ItemProperty property) {
		return Optional.ofNullable(property.getFactory())
				.map(factory -> factory.create(state))
				.map(List::of)
				.orElse(List.of());
	}


}