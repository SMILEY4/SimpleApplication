package simpleui.core.prebuild;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import simpleui.core.SElement;

import java.util.List;
import java.util.stream.Collectors;

public class SContainer extends SElement {


	VBox box = new VBox();




	public SContainer(SElement... elements) {
		super(elements);
	}




	public SContainer(List<SElement> elements) {
		super(elements);
	}




	@Override
	public void onChildRerenderRequest() {
		box.getChildren().setAll(
				getChildren().stream().map(SElement::getFxNode).collect(Collectors.toList()));
	}




	@Override
	public Node getFxNode() {
		box.setPadding(new Insets(0, 0, 0, 20));
		box.getChildren().setAll(
				getChildren().stream().map(SElement::getFxNode).collect(Collectors.toList()));
		return box;
	}


}
