package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.core.CoreServices;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import lombok.Getter;

public class ItemProperty extends Property {


	/**
	 * The factory for creating the item/node.
	 */
	@Getter
	private final NodeFactory factory;




	/**
	 * Creates an item property without a factory.
	 */
	protected ItemProperty() {
		super(ItemProperty.class);
		this.factory = null;
	}




	/**
	 * @param item the factory for creating the item/node.
	 */
	public ItemProperty(final NodeFactory item) {
		super(ItemProperty.class);
		this.factory = item;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		final ItemProperty otherItem = (ItemProperty) other;
		return false; // TODO
	}




	@Override
	public String printValue() {
		return "n=1";
	}




	public static class ScrollPaneBuilder implements PropFxNodeBuilder<ItemProperty, ScrollPane> {


		@Override
		public void build(final SuiBaseNode node,
						  final ItemProperty property,
						  final ScrollPane fxNode) {
			Node fxChildNode = null;
			if (node.getChildNodeStore().hasChildren()) {
				SuiBaseNode childNode = node.getChildNodeStore().get(0);
				CoreServices.enrichWithFxNodes(childNode);
				fxChildNode = childNode.getFxNodeStore().get();
			}
			fxNode.setContent(fxChildNode);
		}

	}


}
