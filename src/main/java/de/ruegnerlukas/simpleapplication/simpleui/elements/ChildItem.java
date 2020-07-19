package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.State;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import javafx.scene.Node;

import java.util.List;

public class ChildItem extends SNode {


	/**
	 * @param nodeType      the more specific node type of this child item
	 * @param propertyList  the properties
	 * @param state         the state
	 * @param childListener the child listener
	 */
	public ChildItem(final Class<?> nodeType, final List<Property> propertyList, final State state, final ChildListener childListener) {
		super(nodeType, propertyList, state, childListener);
	}




	public static class ChildItemNodeBuilder implements BaseFxNodeBuilder<Node> {


		@Override
		public Node build(final SceneContext context, final SNode node) {
			if (node.getChildren().isEmpty()) {
				return null;
			} else {
				return context.getFxNodeBuilder().build(node.getChildren().get(0));
			}
		}

	}

}
