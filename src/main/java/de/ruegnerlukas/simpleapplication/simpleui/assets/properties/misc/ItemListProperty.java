package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiTabPane;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemListProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<ItemListProperty, ItemListProperty, Boolean> COMPARATOR = (a, b) -> false;


	/**
	 * The factories for creating the items/nodes.
	 */
	@Getter
	private final List<NodeFactory> factories;




	/**
	 * @param items the factories for creating the items/nodes.
	 */
	public ItemListProperty(final NodeFactory... items) {
		super(ItemListProperty.class, COMPARATOR);
		this.factories = List.of(items);
	}




	/**
	 * @param items the factories for creating the items/nodes.
	 */
	public ItemListProperty(final Collection<NodeFactory> items) {
		super(ItemListProperty.class, COMPARATOR);
		this.factories = List.copyOf(items);
	}




	/**
	 * @param items the factories for creating the items/nodes.
	 */
	public ItemListProperty(final Stream<NodeFactory> items) {
		super(ItemListProperty.class, COMPARATOR);
		this.factories = List.copyOf(items.collect(Collectors.toList()));
	}




	/**
	 * @param factory the factory for creating factories for creating the items/nodes.
	 */
	public ItemListProperty(final ItemListProperty.ItemListFactory factory) {
		super(ItemListProperty.class, COMPARATOR);
		this.factories = factory.build();
	}




	public interface ItemListFactory {


		/**
		 * Build a list of node factories
		 *
		 * @return the list of node factories
		 */
		List<NodeFactory> build();

	}






	public static class PaneBuilder implements PropFxNodeBuilder<ItemListProperty, Pane> {


		@Override
		public void build(final SuiNode node,
						  final ItemListProperty property,
						  final Pane fxNode) {
			fxNode.getChildren().setAll(
					node.getChildNodeStore().stream()
							.map(child -> child.getFxNodeStore().get())
							.collect(Collectors.toList())
			);
		}

	}






	public static class TabPaneBuilder implements PropFxNodeBuilder<ItemListProperty, TabPane> {


		@Override
		public void build(final SuiNode node,
						  final ItemListProperty property,
						  final TabPane fxNode) {
			fxNode.getTabs().setAll(SuiTabPane.createTabs(node.getChildNodeStore().getUnmodifiable()));
		}

	}






	public static class SplitPaneBuilder implements PropFxNodeBuilder<ItemListProperty, SplitPane> {


		@Override
		public void build(final SuiNode node,
						  final ItemListProperty property,
						  final SplitPane fxNode) {
			fxNode.getItems().setAll(
					node.getChildNodeStore().stream()
							.map(child -> child.getFxNodeStore().get())
							.collect(Collectors.toList())
			);

		}

	}


}
