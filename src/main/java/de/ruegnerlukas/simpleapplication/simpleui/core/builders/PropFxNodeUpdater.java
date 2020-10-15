package de.ruegnerlukas.simpleapplication.simpleui.core.builders;

import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.Node;

public interface PropFxNodeUpdater<P extends SuiProperty, T extends Node> {


	/**
	 * Updates the given property of the given node by modifying the given fx-node.
	 * This method is called with the new property and the sui node still holding the previous property.
	 *
	 * @param property the new updated property
	 * @param node     the node holding the (previous) property and fx-node
	 * @param fxNode   the fx-node to modify
	 * @return whether the fx-node could be mutated or has to be rebuild completely.
	 */
	MutationResult update(P property, SuiNode node, T fxNode);

	/**
	 * Removes the given property of the given node by modifying the given fx-node
	 *
	 * @param property the removed property
	 * @param node     the node holding the property and fx-node
	 * @param fxNode   the fx-node to modify
	 * @return whether the fx-node could be mutated or has to be rebuild completely.
	 */
	MutationResult remove(P property, SuiNode node, T fxNode);


	/**
	 * Utility method to call the update method of another property (if the node has that property).
	 *
	 * @param nodeType node type
	 * @param propType the type of the property to call the builder of
	 * @param node     the node
	 * @param fxNode   the javafx-node
	 */
	default void callUpdateOtherPropUpdater(final Class<?> nodeType,
											final Class<? extends SuiProperty> propType,
											final SuiNode node,
											final T fxNode) {
		node.getPropertyStore().getSafe(propType).ifPresent(prop -> {
			PropFxNodeUpdater updater = SuiRegistry.get().getEntry(nodeType).getPropFxNodeUpdaters().get(propType);
			if (updater != null) {
				updater.update(prop, node, fxNode);
			}
		});
	}


	/**
	 * Utility method to call the remove method of another property (if the node has that property).
	 *
	 * @param nodeType node type
	 * @param propType the type of the property to call the builder of
	 * @param node     the node
	 * @param fxNode   the javafx-node
	 */
	default void callRemoveOtherPropUpdater(final Class<?> nodeType,
											final Class<? extends SuiProperty> propType,
											final SuiNode node,
											final T fxNode) {
		node.getPropertyStore().getSafe(propType).ifPresent(prop -> {
			PropFxNodeUpdater updater = SuiRegistry.get().getEntry(nodeType).getPropFxNodeUpdaters().get(propType);
			if (updater != null) {
				updater.remove(prop, node, fxNode);
			}
		});
	}


}
