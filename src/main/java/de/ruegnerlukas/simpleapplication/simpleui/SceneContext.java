package de.ruegnerlukas.simpleapplication.simpleui;


import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MasterNodeMutator;
import javafx.scene.Node;
import lombok.Getter;

@Getter
public class SceneContext {


	/**
	 * The primary fx node builder.
	 */
	private final MasterFxNodeBuilder fxNodeBuilder;

	/**
	 * The primary node mutator.
	 */
	private final MasterNodeMutator mutator;

	/**
	 * The current state.
	 */
	private State state;

	/**
	 * The root node factory.
	 */
	private final NodeFactory rootFactory;

	/**
	 * The listener to changes to the root fx-node.
	 */
	private FxNodeListener fxNodeListener;


	/**
	 * The current root node
	 */
	private SNode rootNode;




	/**
	 * @param state          the state
	 * @param rootFactory    the root node factory
	 * @param fxNodeListener the listener to changes to the root fx-node.
	 */
	public SceneContext(final State state, final NodeFactory rootFactory, final FxNodeListener fxNodeListener) {
		this.state = state;
		this.state.setListener(this::onStateUpdate);
		this.rootFactory = rootFactory;
		this.fxNodeBuilder = new MasterFxNodeBuilder(this);
		this.mutator = new MasterNodeMutator(fxNodeBuilder, this);
		this.fxNodeListener = fxNodeListener;
	}




	/**
	 * Return the root node. Builds the node with the root node factory if required.
	 *
	 * @return the root node
	 */
	public SNode getRootNode() {
		if (rootNode == null) {
			rootNode = rootFactory.create(getState());
			fxNodeBuilder.build(rootNode);
		}
		return this.rootNode;
	}




	/**
	 * Modifies the state with the given update and updates the node tree.
	 *
	 * @param update the update
	 */
	private void onStateUpdate(final State.StateUpdate update) {
		SNode target = rootFactory.create(getState());
		rootNode = mutator.mutate(rootNode, target);
		fxNodeListener.onNewFxNode(rootNode.getFxNode());
	}




	public interface FxNodeListener {


		/**
		 * Triggered when the fx-node of the the given node changes.
		 *
		 * @param node the node holding the changed fx-node
		 */
		void onNewFxNode(Node node);

	}

}
