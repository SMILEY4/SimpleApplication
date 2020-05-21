package simpleui.core;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import simpleui.core.systems.ElementTreeBuilder;
import simpleui.core.systems.ElementTreePrinter;
import simpleui.core.systems.FxNodeTreeBuilder;

public class SComponentMaster {


	public static final SComponentMaster instance = new SComponentMaster();


	private static Scene scene;
	private static SElement rootElement;

	private static ElementTreeBuilder treeBuilder = new ElementTreeBuilder();
	private static FxNodeTreeBuilder fxNodeTreeBuilder = new FxNodeTreeBuilder();
	private static ElementTreePrinter treePrinter = new ElementTreePrinter();




	public void initialize(SElement rootElement, Scene scene) {
		SComponentMaster.scene = scene;
		SComponentMaster.rootElement = rootElement;
	}




	public void update() {
		treeBuilder.build(rootElement);
		Node root = fxNodeTreeBuilder.build(rootElement);
		scene.setRoot((Parent) root);
	}




	public void reRender(SComponent triggerComponent) {

		SElement renderedElement = triggerComponent.render();
		triggerComponent.setSubElement(renderedElement);

		// TODO: diff current and rendered subtree and only rerender whats really necessary

		Node root = fxNodeTreeBuilder.build(rootElement);
		scene.setRoot((Parent) root);
	}


}
