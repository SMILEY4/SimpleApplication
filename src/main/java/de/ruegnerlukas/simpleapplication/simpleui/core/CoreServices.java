package de.ruegnerlukas.simpleapplication.simpleui.core;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.RegistryEntry;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.Node;

public final class CoreServices {


	/**
	 * Hidden constructor
	 */
	private CoreServices() {
		// Hidden constructor
	}



	public static void enrichWithFxNodes(final SuiBaseNode node) {
		final RegistryEntry registryEntry = SuiRegistry.get().getEntry(node.getNodeType());
		final Node fxNode = registryEntry.getBaseFxNodeBuilder().build(nodeHandlers, node);
		node.getPropertyStore().stream().forEach(property -> {
			var propFxNodeBuilder = registryEntry.getPropFxNodeBuilders().get(property.getKey());
			propFxNodeBuilder.build(nodeHandlers, node, property, fxNode);
		});
		node.getFxNodeStore().set(fxNode);
	}


}
