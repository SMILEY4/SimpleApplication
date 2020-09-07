package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiComponent;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiComponentRenderer;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MasterNodeMutator;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationStrategyDecider;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class SuiSceneContext implements SuiStateListener {


	/**
	 * The node factory for the root node of this context.
	 */
	private final NodeFactory nodeFactory;

	/**
	 * The primary node handlers (like {@link MasterFxNodeBuilder}, {@link MasterNodeMutator})
	 */
	private final MasterNodeHandlers masterNodeHandlers;

	/**
	 * The state of this context.
	 */
	private final SuiState state;

	/**
	 * The root node of this context.
	 */
	private SuiNode rootNode;

	/**
	 * The listeners listening to this context.
	 */
	private List<SuiSceneContextListener> listeners = new ArrayList<>();




	/**
	 * Creates a new scene context with the given root node factory and a basic empty state.
	 *
	 * @param nodeFactory the node factory for the root node of this context.
	 */
	public SuiSceneContext(final NodeFactory nodeFactory) {
		this(new SuiState(), nodeFactory);
	}




	/**
	 * Creates a new scene context with the given state and component with the given renderer as root node.
	 *
	 * @param state     the state of this context
	 * @param stateType the exact type of the state to use
	 * @param renderer  the node renderer used for a component as the root node
	 */
	public <T extends SuiState> SuiSceneContext(final SuiState state, final Class<T> stateType,
												final SuiComponentRenderer<T> renderer) {
		this(state, new SuiComponent<>(renderer));
	}




	/**
	 * Creates a new scene context with the given state and root node factory.
	 *
	 * @param state       the state of this context
	 * @param nodeFactory the node factory for the root node of this context.
	 */
	public SuiSceneContext(final SuiState state, final NodeFactory nodeFactory) {
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
	public SuiNode getRootNode() {
		if (rootNode == null) {
			rootNode = nodeFactory.create(getState());
			masterNodeHandlers.getFxNodeBuilder().build(rootNode);
		}
		return this.rootNode;
	}




	/**
	 * @return the javafx node of the simpleui root node. Builds the root node with the node factory if necessary.
	 */
	public Node getRootFxNode() {
		return getRootNode().getFxNode();
	}




	/**
	 * @return the current simpleui state managed by this context.
	 */
	public SuiState getState() {
		return this.state;
	}




	/**
	 * @return the primary node handlers
	 * (like {@link de.ruegnerlukas.simpleapplication.simpleui.builders.MasterFxNodeBuilder},
	 * {@link de.ruegnerlukas.simpleapplication.simpleui.mutation.MasterNodeMutator})
	 */
	public MasterNodeHandlers getMasterNodeHandlers() {
		return this.masterNodeHandlers;
	}




	@Override
	public void stateUpdated(final SuiState state, final SuiStateUpdate update) {
		final SuiNode prevRootNode = rootNode;
		final SuiNode target = nodeFactory.create(getState());
		rootNode = masterNodeHandlers.getMutator().mutate(rootNode, target);
		if (prevRootNode != rootNode) {
			listeners.forEach(listener -> listener.onNewSUIRootNode(prevRootNode, rootNode));
		}
	}




	/**
	 * Adds the given listener to this context. Any listener is only added once to this context.
	 *
	 * @param listener the listener to add
	 */
	public void addListener(final SuiSceneContextListener listener) {
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
	public void removeListener(final SuiSceneContextListener listener) {
		listeners.remove(listener);
	}

}
