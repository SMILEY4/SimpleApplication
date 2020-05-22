package simpleui.core.systems;

public class FxNodeTreeBuilder {


//	public Node build(SElement element) {
//
//		SElement rootElement = element;
//		if (element instanceof SComponent) {
//			rootElement = ((SComponent) element).getSubElement();
//		}
//
//		Node rootNode = null;
//		if (rootElement instanceof SButton) {
//			rootNode = sButton((SButton) rootElement);
//		}
//		if (rootElement instanceof SBox) {
//			rootNode = sBox((SBox) rootElement);
//		}
//
//		return rootNode;
//	}
//
//
//
//
//	private Node sBox(SBox element) {
//
//		List<Node> nodes = element.getElements()
//				.stream()
//				.map(this::build)
//				.collect(Collectors.toList());
//
//		VBox box = new VBox();
//		box.getChildren().setAll(nodes);
//		box.setPadding(new Insets(0, 0, 0, 20));
//		element.setLinkedFxNode(box);
//
//		return box;
//	}
//
//
//
//
//	private Node sButton(SButton element) {
//		Button button = new Button(element.getText());
//		button.setOnAction(e -> element.getListener().onAction());
//		element.setLinkedFxNode(button);
//
//		return button;
//	}


}
