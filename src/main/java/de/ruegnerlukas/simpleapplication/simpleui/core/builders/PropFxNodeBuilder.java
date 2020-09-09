package de.ruegnerlukas.simpleapplication.simpleui.core.builders;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.Node;

public interface PropFxNodeBuilder<P extends SuiProperty, T extends Node> {


	/**
	 * Adds the given property to the given fx-node of the given node when first building the node.
	 *
	 * @param node         the node holding the property and fx-node
	 * @param property     the property to add to the fx-node
	 * @param fxNode       the fx-node to modify
	 */
	void build(SuiNode node, P property, T fxNode);


}
