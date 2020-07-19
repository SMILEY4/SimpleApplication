package de.ruegnerlukas.simpleapplication.simpleui.builders;

import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import javafx.scene.Node;

public interface PropFxNodeBuilder<P extends Property, T extends Node> {


	/**
	 * Adds the given property to the given fx-node of the given node when first building the node.
	 * @param context the scene context
	 * @param node the node holding the property and fx-node
	 * @param property the property to add to the fx-node
	 * @param fxNode the fx-node to modify
	 */
	void build(SceneContext context, SNode node, P property, T fxNode);


}
