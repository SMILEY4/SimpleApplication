package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MasterNodeMutator;
import de.ruegnerlukas.simpleapplication.simpleui.utils.SUIStateListener;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class SUISceneContextImpl implements SUISceneContext {


	/**
	 * Listeners listening to the state of this context.
	 */
	private List<SUIStateListener> stateListeners = new ArrayList<>();

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
	private final SUIState state;

	/**
	 * The root node of this context.
	 */
	private SUINode rootNode;




	/**
	 * Creates a new scene context with the given root node factory and a basic empty state.
	 *
	 * @param nodeFactory the node factory for the root node of this context.
	 */
	public SUISceneContextImpl(final NodeFactory nodeFactory) {
		this(new SUIStateImpl(), nodeFactory);
	}




	/**
	 * Creates a new scene context with the given state and root node factory.
	 *
	 * @param state       the state of this context
	 * @param nodeFactory the node factory for the root node of this context.
	 */
	public SUISceneContextImpl(final SUIState state, final NodeFactory nodeFactory) {
		Validations.INPUT.notNull(state).exception("The state can not be null.");
		Validations.INPUT.notNull(nodeFactory).exception("The node factory can not be null.");
		this.state = state;
		this.state.linkToContext(this);
		this.nodeFactory = nodeFactory;
		MasterFxNodeBuilder masterFxNodeBuilder = new MasterFxNodeBuilder(this);
		MasterNodeMutator masterNodeMutator = new MasterNodeMutator(masterFxNodeBuilder, this);
		this.masterNodeHandlers = new MasterNodeHandlers(masterFxNodeBuilder, masterNodeMutator);
	}




	@Override
	public SUINode getRootNode() {
		if (rootNode == null) {
			rootNode = nodeFactory.create(getState());
			masterNodeHandlers.getFxNodeBuilder().build(rootNode);
		}
		return this.rootNode;
	}




	@Override
	public Node getRootFxNode() {
		return getRootNode().getFxNode();
	}




	@Override
	public SUIState getState() {
		return this.state;
	}




	@Override
	public MasterNodeHandlers getMasterNodeHandlers() {
		return this.masterNodeHandlers;
	}




	@Override
	public void applyStateUpdate(final SUIStateUpdate update) {
		Validations.INPUT.notNull(update).exception("The state update must not be null.");
		stateListeners.forEach(listener -> listener.beforeUpdate(getState(), update));
		processStateUpdate(update);
		stateListeners.forEach(listener -> listener.stateUpdated(getState(), update));
	}




	/**
	 * Executes the given update and mutates/rebuilds the root node.
	 *
	 * @param update the state update to process
	 */
	private void processStateUpdate(final SUIStateUpdate update) {
		update.doUpdate(state);
		final SUINode prevRootNode = rootNode;
		final SUINode target = nodeFactory.create(getState());
		rootNode = masterNodeHandlers.getMutator().mutate(rootNode, target);
		if (prevRootNode != rootNode) {
			stateListeners.forEach(listener -> listener.createdNewRootNode(prevRootNode, rootNode));
		}
	}




	@Override
	public void addStateListener(final SUIStateListener listener) {
		Validations.INPUT.notNull(listener).exception("The listener must not be null.");
		stateListeners.add(listener);
	}




	@Override
	public void removeStateListener(final SUIStateListener listener) {
		stateListeners.remove(listener);
	}

}
