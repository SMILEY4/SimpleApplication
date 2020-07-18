package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeBuilder;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import lombok.Getter;

public class ItemProperty extends Property {


	@Getter
	private final NodeFactory factory;




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
		public void build(final SceneContext context, final SNode node, final ItemProperty property, final ScrollPane fxNode) {
			Node childNode = null;
			if (!node.getChildren().isEmpty()) {
				childNode = context.getFxNodeBuilder().build(node.getChildren().get(0));
			}
			fxNode.setContent(childNode);
		}

	}


}
