package simpleui.core.prebuild;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import simpleui.core.SElement;

import java.util.List;
import java.util.stream.Collectors;

public class SContainer extends SElement {


	public SContainer(SElement... elements) {
		super(elements);
	}




	public SContainer(List<SElement> elements) {
		super(elements);
	}




	@Override
	public Node getFxNode() {
		VBox box = new VBox();
		box.setPadding(new Insets(0, 0, 0, 20));
		box.getChildren().addAll(
				getChildren().stream().map(SElement::getFxNode).collect(Collectors.toList()));
		return box;
	}


}
