package de.ruegnerlukas.simpleapplication.core.simpleui.core;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationStrategyDecider;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.NodeMutator;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.NodeMutatorImpl;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.profiler.SuiProfiler;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.RegistryEntry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import javafx.scene.Node;

public class SuiServices {


	/**
	 * The current instance.
	 */
	private static SuiServices instance = null;




	/**
	 * Get the instance of the simpleui services. If none was initialized yet, it will be initialized with the default implementation.
	 *
	 * @return an instance of the simpleui services
	 */
	public static SuiServices get() {
		if (instance == null) {
			initializeDefault();
		}
		return instance;
	}




	/**
	 * Initialize the instance of the simpleui services with the default implementation.
	 */
	public static void initializeDefault() {
		initialize(new SuiServices(new NodeMutatorImpl(MutationStrategyDecider.DEFAULT_STRATEGIES)));
	}




	/**
	 * Initialize the instance of the simpleui services with the given implementation.
	 */
	public static void initialize(final SuiServices instance) {
		SuiServices.instance = instance;
	}




	/**
	 * The node mutator to use.
	 */
	private final NodeMutator mutator;




	/**
	 * @param mutator the node mutator to use.
	 */
	public SuiServices(final NodeMutator mutator) {
		this.mutator = mutator;
	}




	/**
	 * Builds the javafx node for the given node and appends it to the node.
	 *
	 * @param node the node to builds the javafx-node for
	 */
	public void enrichWithFxNodes(final SuiNode node) {
		node.getChildNodeStore().stream().forEach(this::enrichWithFxNodes);
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
	private Node buildBaseFxNode(final SuiNode node, final RegistryEntry registryEntry) {
		SuiProfiler.get().countFxNodeBuild();
		return registryEntry.getBaseFxNodeBuilder().build(node);
	}




	/**
	 * Applies all properties of the given simpleui node to the given javafx node.
	 *
	 * @param node          the simpleui node
	 * @param registryEntry the registry entry of the node
	 * @param fxNode        the javafx node
	 */
	@SuppressWarnings ("unchecked")
	private void applyProperties(final SuiNode node, final RegistryEntry registryEntry, final Node fxNode) {
		node.getPropertyStore().stream().forEach(property -> {
			@SuppressWarnings ("rawtypes") PropFxNodeBuilder builder = registryEntry.getPropFxNodeBuilders().get(property.getKey());
			SuiProfiler.get().countPropertyBuild();
			builder.build(node, property, fxNode);
		});
	}




	/**
	 * Mutates the given original node tree to match the given target tree.
	 *
	 * @param original the original tree to mutate
	 * @param target   the target tree to match
	 * @param tags     tags associated with the state update triggering this mutation
	 * @return the mutated root node or the original tree or the complete root node of the target tree if a rebuild was required
	 */
	public SuiSceneTree mutateTree(final SuiSceneTree original, final SuiSceneTree target, final Tags tags) {
		final long timeStart = System.currentTimeMillis();
		if (mutate(original.getRoot(), target.getRoot(), tags) == MutationResult.REQUIRES_REBUILD) {
			SuiProfiler.get().getMutationDuration().count(System.currentTimeMillis() - timeStart);
			target.buildFxNodes();
			return target;
		} else {
			return original;
		}
	}




	/**
	 * Mutates the given original node to match the given target node.
	 *
	 * @param original the original node to mutate
	 * @param target   the target node to match
	 * @param tags     an optional list of tags associated with the state update triggering this mutation
	 * @return the mutated original node or the target node.
	 */
	public SuiNode mutateNode(final SuiNode original, final SuiNode target, final Tags tags) {
		if (mutate(original, target, tags) == MutationResult.REQUIRES_REBUILD) {
			enrichWithFxNodes(target);
			return target;
		} else {
			return original;
		}
	}




	/**
	 * Mutates the given original node to match the given target node.
	 *
	 * @param original the original node to mutate
	 * @param target   the target node to match
	 * @param tags     an optional list of tags associated with the state update triggering this mutation
	 * @return the result status of the mutation / whether the original node has to be rebuild.
	 */
	public MutationResult mutate(final SuiNode original, final SuiNode target, final Tags tags) {
		return mutator.mutateNode(original, target, tags);
	}


}
