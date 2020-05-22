package simpleui.core.state;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import simpleui.core.factories.SNodeFactory;
import simpleui.core.nodes.SNode;

public class StateManager {


	private static State state;
	private static SNodeFactory factory;
	private static Scene scene;

	private static SNode currentTree;




	public static void init(final SNodeFactory factory, final State state, final Scene scene) {
		StateManager.factory = factory;
		StateManager.state = state;
		StateManager.scene = scene;

		currentTree = factory.create(StateManager.getState());
		final Node rootFxNode = currentTree.createLinkedFxNode();
		scene.setRoot((Parent) rootFxNode);
	}




	public static State getState() {
		return state;
	}




	public static void modifyState(StateModification mod) {

		State newState = state.copyState();
		mod.modify(newState);

		final SNode newTree = factory.create(newState);
		if (currentTree.mutate(newTree)) {
			final Node newRootFxNode = newTree.createLinkedFxNode();
			scene.setRoot((Parent) newRootFxNode);
			currentTree = newTree;
		}

		state = newState;
	}


}
