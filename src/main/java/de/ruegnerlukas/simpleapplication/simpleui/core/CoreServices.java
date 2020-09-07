package de.ruegnerlukas.simpleapplication.simpleui.core;

import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.BaseNodeMutator;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationStrategyDecider;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.NodeMutator;
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
		final Node fxNode = buildBaseFxNode(node, registryEntry);
		applyProperties(node, registryEntry, fxNode);
		node.getFxNodeStore().set(fxNode);
	}




	/**
	 * Builds the base javafx-node.
	 *
	 * @param node          the simpleui node
	 * @param registryEntry the registry entry for the node
	 * @return the created javafx-node
	 */
	private static Node buildBaseFxNode(final SuiBaseNode node, final RegistryEntry registryEntry) {
		return registryEntry.getBaseFxNodeBuilder().build(node);
	}




	/**
	 * Applies all properties of the given simpleui node to the given javafx node.
	 *
	 * @param node          the simpleui node
	 * @param registryEntry the registry entry of the node
	 * @param fxNode        the javafx node
	 */
	private static void applyProperties(final SuiBaseNode node, final RegistryEntry registryEntry, final Node fxNode) {
		node.getPropertyStore().stream().forEach(property -> {
			PropFxNodeBuilder propBuilder = registryEntry.getPropFxNodeBuilders().get(property.getKey());
			propBuilder.build(node, property, fxNode);
		});
	}




	/**
	 * Mutates the given original node tree to match the given target tree.
	 *
	 * @param original the original tree to mutate
	 * @param target   the target tree to match
	 * @return the mutated root node or the original tree or the complete root node of the target tree if a rebuild was required
	 */
	public static SuiSceneTree mutate(final SuiSceneTree original, final SuiSceneTree target) {
		final BaseNodeMutator mutator = new NodeMutator(MutationStrategyDecider.DEFAULT_STRATEGIES);
		final MutationResult result = mutator.mutateNode(original.getRoot(), target.getRoot());
		if (result == MutationResult.REQUIRES_REBUILD) {
			target.buildFxNodes();
			return target;
		} else {
			return original;
		}
	}

}
