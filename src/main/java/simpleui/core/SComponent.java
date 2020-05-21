package simpleui.core;

import javafx.scene.Node;

public abstract class SComponent extends SElement {


	public SComponent() {
		build();
	}




	private void build() {
		this.getChildren().add(render());
		this.getChildren().forEach(child -> child.setParent(this));
	}




	@Override
	public Node getFxNode() {
		return getChildren().get(0).getFxNode();
	}




	public abstract SElement render();

}
