package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.SuiServices;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemListProperty extends SuiProperty {


	/**
	 * The factories for creating the items/nodes.
	 */
	@Getter
	private final List<NodeFactory> factories;




	/**
	 * @param items the factories for creating the items/nodes.
	 */
	public ItemListProperty(final NodeFactory... items) {
		super(ItemListProperty.class);
		this.factories = List.of(items);
	}




	/**
	 * @param items the factories for creating the items/nodes.
	 */
	public ItemListProperty(final Collection<NodeFactory> items) {
		super(ItemListProperty.class);
		this.factories = List.copyOf(items);
	}




	/**
	 * @param items the factories for creating the items/nodes.
	 */
	public ItemListProperty(final Stream<NodeFactory> items) {
		super(ItemListProperty.class);
		this.factories = List.copyOf(items.collect(Collectors.toList()));
	}




	/**
	 * @param factory the factory for creating factories for creating the items/nodes.
	 */
	public ItemListProperty(final ItemListProperty.ItemListFactory factory) {
		super(ItemListProperty.class);
		this.factories = factory.build();
	}




	@Override
	protected boolean isPropertyEqual(final SuiProperty other) {
		final ItemListProperty otherList = (ItemListProperty) other;
		return false; // TODO
	}




	public static class Builder implements PropFxNodeBuilder<ItemListProperty, Pane> {


		@Override
		public void build(final SuiNode node,
						  final ItemListProperty property,
						  final Pane fxNode) {
			fxNode.getChildren().setAll(
					node.getChildNodeStore().stream()
							.peek(child -> SuiServices.get().enrichWithFxNodes(child))
							.map(child -> child.getFxNodeStore().get())
							.collect(Collectors.toList())
			);
		}

	}






	public interface ItemListFactory {


		/**
		 * Build a list of node factories
		 *
		 * @return the list of node factories
		 */
		List<NodeFactory> build();

	}

}
