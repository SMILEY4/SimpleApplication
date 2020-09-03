package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeBuilder;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemListProperty extends Property {


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
	protected boolean isPropertyEqual(final Property other) {
		final ItemListProperty otherList = (ItemListProperty) other;
		return false; // TODO
	}




	@Override
	public String printValue() {
		return "n=" + getFactories().size();
	}




	public static class Builder implements PropFxNodeBuilder<ItemListProperty, Pane> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final ItemListProperty property,
						  final Pane fxNode) {
			List<Node> childFxNodes = node.streamChildren()
					.map(child -> nodeHandlers.getFxNodeBuilder().build(child))
					.collect(Collectors.toList());
			fxNode.getChildren().setAll(childFxNodes);
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
