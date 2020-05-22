package simpleui.core.prebuilt;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import lombok.Getter;
import simpleui.core.SElement;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@AllArgsConstructor
public class SBox extends SElement {


	private final List<SElement> elements;




	public SBox(SElement... element) {
		this(List.of(element));
	}




	@Override
	public void buildTree() {
		getElements().forEach(SElement::buildTree);
	}




	@Override
	public Node buildFXNode() {

		List<Node> nodes = getElements()
				.stream()
				.map(SElement::buildFXNode)
				.collect(Collectors.toList());

		VBox box = new VBox();
		box.getChildren().setAll(nodes);
		box.setPadding(new Insets(0, 0, 0, 20));
		setLinkedFxNode(box);

		return box;
	}




	@Override
	public void print(final int level) {
		final StringBuilder builder = new StringBuilder();
		builder.append(" ".repeat(level * 3));
		builder.append(getClass().getSimpleName()).append(" ");
		builder.append(" ").append(Integer.toHexString(hashCode()));

		builder.append(" {");
		builder.append("n:").append(elements.size());
		builder.append("}");

		if (getLinkedFxNode() != null) {
			builder.append("  linked");
		}

		System.out.println(builder.toString());

		getElements().forEach(e -> e.print(level + 1));

	}

}
