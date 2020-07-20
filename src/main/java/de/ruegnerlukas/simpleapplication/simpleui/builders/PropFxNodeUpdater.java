package de.ruegnerlukas.simpleapplication.simpleui.builders;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import javafx.scene.Node;

public interface PropFxNodeUpdater<P extends Property, T extends Node> {


	/**
	 * Updates the given property of the given node by modifying the given fx-node
	 *
	 * @param nodeHandlers the primary node handlers
	 * @param property     the updated property
	 * @param node         the node holding the property and fx-node
	 * @param fxNode       the fx-node to modify
	 * @return whether the fx-node could be mutated or has to be rebuild completely.
	 */
	BaseNodeMutator.MutationResult update(MasterNodeHandlers nodeHandlers, P property, SUINode node, T fxNode);

	/**
	 * Removes the given property of the given node by modifying the given fx-node
	 *
	 * @param nodeHandlers the primary node handlers
	 * @param property     the removed property
	 * @param node         the node holding the property and fx-node
	 * @param fxNode       the fx-node to modify
	 * @return whether the fx-node could be mutated or has to be rebuild completely.
	 */
	BaseNodeMutator.MutationResult remove(MasterNodeHandlers nodeHandlers, P property, SUINode node, T fxNode);


}
