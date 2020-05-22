package simpleui.core.nodes;

import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;

public abstract class SNode {


	@Getter
	@Setter
	private Node linkedFxNode;




	public Node createLinkedFxNode() {
		Node fxNode = buildFxNode();
		setLinkedFxNode(fxNode);
		return fxNode;
	}




	protected abstract Node buildFxNode();


	public abstract boolean mutate(SNode other);


	public abstract void print(int level);

}
