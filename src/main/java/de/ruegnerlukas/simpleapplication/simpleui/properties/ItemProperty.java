package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeBuilder;
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




	public static class ScrollPaneContentBuilder implements PropFxNodeBuilder<ItemProperty, ScrollPane> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final ItemProperty property,
						  final ScrollPane fxNode) {
			Node childNode = null;
			if (node.hasChildren()) {
				childNode = nodeHandlers.getFxNodeBuilder().build(node.getChild(0));
			}
			fxNode.setContent(childNode);
		}

	}


}
