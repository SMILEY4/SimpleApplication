package de.ruegnerlukas.simpleapplication.core.simpleui.core.builders;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import javafx.scene.Node;

public interface PropFxNodeBuilder<P extends SuiProperty, T extends Node> {


	/**
	 * Adds the given property to the given fx-node of the given node when first building the node.
	 *
	 * @param node     the node holding the property and fx-node
	 * @param property the property to add to the fx-node
	 * @param fxNode   the fx-node to modify
	 */
	void build(SuiNode node, P property, T fxNode);

	/**
	 * Utility method to call the builder of another property if the node has that property.
	 *
	 * @param nodeType node type
	 * @param propType the type of the property to call the builder of
	 * @param node     the node
	 * @param fxNode   the javafx-node
	 */
	default void callOtherPropBuilder(final Class<?> nodeType,
									  final Class<? extends SuiProperty> propType,
									  final SuiNode node,
									  final T fxNode) {
		node.getPropertyStore().getSafe(propType).ifPresent(prop -> {
			final SuiRegistry suiRegistry = new Provider<>(SuiRegistry.class).get();
			PropFxNodeBuilder propFxNodeBuilder = suiRegistry.getEntry(nodeType).getPropFxNodeBuilders().get(propType);
			if (propFxNodeBuilder != null) {
				propFxNodeBuilder.build(node, prop, fxNode);
			}
		});
	}


}
