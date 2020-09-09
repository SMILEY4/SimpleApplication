package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.SuiServices;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import lombok.Getter;

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








	public static class ScrollPaneBuilder implements PropFxNodeBuilder<ItemProperty, ScrollPane> {


		@Override
		public void build(final SuiNode node,
						  final ItemProperty property,
						  final ScrollPane fxNode) {
			Node fxChildNode = null;
			if (node.getChildNodeStore().hasChildren()) {
				SuiNode childNode = node.getChildNodeStore().get(0);
				SuiServices.get().enrichWithFxNodes(childNode);
				fxChildNode = childNode.getFxNodeStore().get();
			}
			fxNode.setContent(fxChildNode);
		}

	}


}
