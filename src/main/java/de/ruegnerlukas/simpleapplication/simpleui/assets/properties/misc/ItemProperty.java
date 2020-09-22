package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiTabPane;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.List;
import java.util.function.BiFunction;

public class ItemProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<ItemProperty, ItemProperty, Boolean> COMPARATOR = (a, b) -> false;

	/**
	 * The factory for creating the item/node.
	 */
	@Getter
	private final NodeFactory factory;




	/**
	 * Creates an item property without a factory.
	 */
	protected ItemProperty() {
		super(ItemProperty.class, COMPARATOR);
		this.factory = null;
	}




	/**
	 * @param item the factory for creating the item/node.
	 */
	public ItemProperty(final NodeFactory item) {
		super(ItemProperty.class, COMPARATOR);
		this.factory = item;
	}




	public static class PaneBuilder implements PropFxNodeBuilder<ItemProperty, Pane> {


		@Override
		public void build(final SuiNode node,
						  final ItemProperty property,
						  final Pane fxNode) {
			if (node.getChildNodeStore().hasChildren()) {
				SuiNode childNode = node.getChildNodeStore().get(0);
				fxNode.getChildren().setAll(childNode.getFxNodeStore().get());
			} else {
				fxNode.getChildren().clear();
			}
		}

	}






	public static class ScrollPaneBuilder implements PropFxNodeBuilder<ItemProperty, ScrollPane> {


		@Override
		public void build(final SuiNode node,
						  final ItemProperty property,
						  final ScrollPane fxNode) {
			Node fxChildNode = null;
			if (node.getChildNodeStore().hasChildren()) {
				SuiNode childNode = node.getChildNodeStore().get(0);
				fxChildNode = childNode.getFxNodeStore().get();
			}
			fxNode.setContent(fxChildNode);
		}

	}






	public static class TabPaneBuilder implements PropFxNodeBuilder<ItemListProperty, TabPane> {


		@Override
		public void build(final SuiNode node,
						  final ItemListProperty property,
						  final TabPane fxNode) {
			fxNode.getTabs().setAll(SuiTabPane.createTabs(List.of(node.getChildNodeStore().get(0))));
		}

	}






	public static class SplitPaneBuilder implements PropFxNodeBuilder<ItemListProperty, SplitPane> {


		@Override
		public void build(final SuiNode node,
						  final ItemListProperty property,
						  final SplitPane fxNode) {
			fxNode.getItems().setAll(node.getChildNodeStore().get(0).getFxNodeStore().get());
		}

	}


}
