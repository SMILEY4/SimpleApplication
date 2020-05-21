package simpleui.core.systems;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import simpleui.core.SComponent;
import simpleui.core.SElement;
import simpleui.core.prebuilt.SBox;
import simpleui.core.prebuilt.SButton;

import java.util.List;
import java.util.stream.Collectors;

public class FxNodeTreeBuilder {


	public Node build(SElement element) {

		SElement rootElement = element;
		if (element instanceof SComponent) {
			rootElement = ((SComponent) element).getSubElement();
		}

		Node rootNode = null;
		if (rootElement instanceof SButton) {
			rootNode = sButton((SButton) rootElement);
		}
		if (rootElement instanceof SBox) {
			rootNode = sBox((SBox) rootElement);
		}

		return rootNode;
	}




	private Node sBox(SBox element) {

		List<Node> nodes = element.getElements()
				.stream()
				.map(this::build)
				.collect(Collectors.toList());

		VBox box = new VBox();
		box.getChildren().setAll(nodes);
		box.setPadding(new Insets(0, 0, 0, 20));
		return box;
	}




	private Node sButton(SButton element) {
		Button button = new Button(element.getText());
		button.setOnAction(e -> element.getListener().onAction());
		return button;
	}


}
