package simpleui.core;

import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;

public abstract class SElement {


	@Getter
	@Setter
	private Node linkedFxNode;




	public abstract void buildTree();


	public abstract Node buildFXNode();


	public void print() {
		print(0);
		System.out.println();
	}




	public void print(int level) {
		final StringBuilder builder = new StringBuilder();
		builder.append(" ".repeat(level * 3));
		builder.append(" ").append(getClass().getSimpleName());
		builder.append(" ").append(Integer.toHexString(hashCode()));

		if (getLinkedFxNode() != null) {
			builder.append("  -  ").append(getLinkedFxNode().toString());
		}
		System.out.println(builder.toString());
	}

}
