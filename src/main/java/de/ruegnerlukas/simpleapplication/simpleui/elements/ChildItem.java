package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.SUIState;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import javafx.scene.Node;

import java.util.List;

public class ChildItem extends SUINode {


	/**
	 * @param nodeType      the more specific node type of this child item
	 * @param propertyList  the properties
	 * @param state         the state
	 * @param childListener the child listener
	 */
	public ChildItem(final Class<?> nodeType, final List<Property> propertyList,
					 final SUIState state, final ChildListener childListener) {
		super(nodeType, propertyList, state, childListener);
	}




	public static class ChildItemNodeBuilder implements BaseFxNodeBuilder<Node> {


		@Override
		public Node build(final MasterNodeHandlers nodeHandlers, final SUINode node) {
			if (node.hasChildren()) {
				return nodeHandlers.getFxNodeBuilder().build(node.getChild(0));
			} else {
				return null;
			}
		}

	}

}
