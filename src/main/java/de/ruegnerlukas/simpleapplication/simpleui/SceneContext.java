package de.ruegnerlukas.simpleapplication.simpleui;


import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MasterNodeMutator;
import javafx.scene.Node;
import lombok.Getter;

@Getter
public class SceneContext {


	private final MasterFxNodeBuilder fxNodeBuilder;

	private final MasterNodeMutator mutator;

	private final NodeFactory rootFactory;

	private State state;

	private SNode rootNode;

	private FxNodeListener fxNodeListener;



	public SceneContext(State state, NodeFactory rootFactory, FxNodeListener fxNodeListener) {
		this.state = state;
		this.state.setListener(this::onStateUpdate);
		this.rootFactory = rootFactory;
		this.fxNodeBuilder = new MasterFxNodeBuilder(this);
		this.mutator = new MasterNodeMutator(fxNodeBuilder, this);
		this.fxNodeListener = fxNodeListener;
	}




	public SNode getRootNode() {
		if (rootNode == null) {
			rootNode = rootFactory.create(getState());
			fxNodeBuilder.build(rootNode);
		}
		return this.rootNode;
	}




	private void onStateUpdate(State.StateUpdate update) {
		SNode target = rootFactory.create(getState());
		rootNode = mutator.mutate(rootNode, target);
		fxNodeListener.onNewFxNode(rootNode.getFxNode());
	}




	public interface FxNodeListener {


		void onNewFxNode(Node node);

	}

}
