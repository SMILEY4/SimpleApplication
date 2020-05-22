package simpleui.core;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import simpleui.core.prebuilt.SComponent;

public class SComponentMaster {


	public static final SComponentMaster instance = new SComponentMaster();


	private static Scene scene;
	private static SElement rootElement;





	public void initialize(SElement rootElement, Scene scene) {
		SComponentMaster.scene = scene;
		SComponentMaster.rootElement = rootElement;
	}




	public void update() {
		rootElement.buildTree();
		Node root = rootElement.buildFXNode();
		scene.setRoot((Parent) root);
	}




	public void reRender(SComponent triggerComponent) {

		rootElement.print();

		SElement subtreeBefore = triggerComponent.getSubElement();

		SElement renderedElement = triggerComponent.render();
		triggerComponent.setSubElement(renderedElement);

		diff(subtreeBefore, renderedElement);

		Node root = rootElement.buildFXNode();
		scene.setRoot((Parent) root);
	}




	private void diff(SElement a, SElement b) {
		a.print();
		b.print();
	}

}
