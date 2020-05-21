package simpleui.core;

import javafx.scene.Node;

public abstract class SComponent extends SElement {


	@Override
	public Node getFxNode() {
		build();
		return getChildren().get(0).getFxNode();
	}




	private void build() {
		this.getChildren().add(render());
		this.getChildren().forEach(child -> child.setParent(this));
	}




	public void triggerReRender() {
		getParent().onChildRerenderRequest();
	}




	public void onChildRerenderRequest() {
		// TODO ?
	}




	public abstract SElement render();

}
