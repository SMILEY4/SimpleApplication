package de.ruegnerlukas.simpleapplication.simpleui.builders;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import javafx.scene.Node;

public interface PropFxNodeBuilder<P extends Property, T extends Node> {


	/**
	 * Adds the given property to the given fx-node of the given node when first building the node.
	 *
	 * @param nodeHandlers the primary node handlers
	 * @param node         the node holding the property and fx-node
	 * @param property     the property to add to the fx-node
	 * @param fxNode       the fx-node to modify
	 */
	void build(MasterNodeHandlers nodeHandlers, SUINode node, P property, T fxNode);


}