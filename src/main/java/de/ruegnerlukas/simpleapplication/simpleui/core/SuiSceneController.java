package de.ruegnerlukas.simpleapplication.simpleui.core;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiComponent;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiComponentRenderer;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.MasterFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MasterNodeMutator;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationStrategyDecider;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiStateListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiStateUpdate;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class SuiSceneController implements SuiStateListener {


	/**
	 * The state of this context.
	 */
	private final SuiState state;

	/**
	 * The node factory for the root node of this context.
	 */
	private final NodeFactory nodeFactory;

	/**
	 * The tree of nodes making up the current scene.
	 */
	private SuiSceneTree sceneTree;

	/**
	 * The listeners listening to this context.
	 */
	private List<SuiSceneControllerListener> listeners = new ArrayList<>();

	/**
	 * The primary node handlers (like {@link MasterFxNodeBuilder}, {@link MasterNodeMutator})
	 */
	private final MasterNodeHandlers masterNodeHandlers;




	/**
	 * Creates a new scene context with the given root node factory and a basic empty state.
	 *
	 * @param nodeFactory the node factory for the root node of this context.
	 */
	public SuiSceneController(final NodeFactory nodeFactory) {
		this(new SuiState(), nodeFactory);
	}




	/**
	 * Creates a new scene context with the given state and component with the given renderer as root node.
	 *
	 * @param state     the state of this context
	 * @param stateType the exact type of the state to use
	 * @param renderer  the node renderer used for a component as the root node
	 */
	public <T extends SuiState> SuiSceneController(final SuiState state, final Class<T> stateType,
												   final SuiComponentRenderer<T> renderer) {
		this(state, new SuiComponent<>(renderer));
	}




	/**
	 * Creates a new scene context with the given state and root node factory.
	 *
	 * @param state       the state of this context
	 * @param nodeFactory the node factory for the root node of this context.
	 */
	public SuiSceneController(final SuiState state, final NodeFactory nodeFactory) {
		Validations.INPUT.notNull(state).exception("The state can not be null.");
		Validations.INPUT.notNull(nodeFactory).exception("The node factory can not be null.");
		this.state = state;
		this.state.addStateListener(this);
		this.nodeFactory = nodeFactory;
		MasterFxNodeBuilder fxNodeBuilder = new MasterFxNodeBuilder(this);
		MasterNodeMutator nodeMutator = new MasterNodeMutator(fxNodeBuilder, this, MutationStrategyDecider.DEFAULT_STRATEGIES);
		this.masterNodeHandlers = new MasterNodeHandlers(new MasterFxNodeBuilder(this), nodeMutator);
	}




	/**
	 * @return the simpleui root node. Builds the root node with the node factory if necessary.
	 */
	public SuiBaseNode getRootNode() {
		if (sceneTree == null) {
			sceneTree = SuiSceneTree.build(nodeFactory, state);
			masterNodeHandlers.getFxNodeBuilder().build(rootNode);
		}
		return sceneTree.getRoot();
	}




	/**
	 * @return the javafx node of the simpleui root node. Builds the root node with the node factory if necessary.
	 */
	public Node getRootFxNode() {
		return getRootNode().getFxNodeStore().getUnsafe();
	}




	/**
	 * @return the current simpleui state managed by this context.
	 */
	public SuiState getState() {
		return this.state;
	}




	@Override
	public void stateUpdated(final SuiState state, final SuiStateUpdate<?> update) {
		final SuiSceneTree targetTree = SuiSceneTree.build(nodeFactory, state);
		sceneTree.mutate(targetTree);
//		rootNode = masterNodeHandlers.getMutator().mutate(rootNode, target);
		if (sceneTree.mutate(targetTree)) {
			listeners.forEach(listener -> listener.onNewSuiRootNode(sceneTree.getRoot()));
		}
	}




	/**
	 * Adds the given listener to this context. Any listener is only added once to this context.
	 *
	 * @param listener the listener to add
	 */
	public void addListener(final SuiSceneControllerListener listener) {
		Validations.INPUT.notNull(listener).exception("The context listener may not be null.");
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}




	/**
	 * Removes the given listener from this context.
	 *
	 * @param listener the listener to remove
	 */
	public void removeListener(final SuiSceneControllerListener listener) {
		listeners.remove(listener);
	}




	/**
	 * @return the primary node handlers
	 * (like {@link de.ruegnerlukas.simpleapplication.simpleui.core.builders.MasterFxNodeBuilder},
	 * {@link de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MasterNodeMutator})
	 */
	public MasterNodeHandlers getMasterNodeHandlers() {
		return this.masterNodeHandlers;
	}


}