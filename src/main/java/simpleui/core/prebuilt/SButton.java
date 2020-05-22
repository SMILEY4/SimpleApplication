package simpleui.core.prebuilt;

import javafx.scene.Node;
import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Getter;
import simpleui.core.SElement;

@Getter
@AllArgsConstructor
public class SButton extends SElement {


	private final String text;

	private final Listener listener;




	public SButton(String text) {
		this(text, () -> {
		});
	}




	@Override
	public void buildTree() {
	}




	@Override
	public Node buildFXNode() {
		Button button = new Button(getText());
		button.setOnAction(e -> getListener().onAction());
		setLinkedFxNode(button);
		return button;
	}




	@Override
	public void print(final int level) {
		final StringBuilder builder = new StringBuilder();
		builder.append(" ".repeat(level * 3));
		builder.append(getClass().getSimpleName()).append(" ");
		builder.append(" ").append(Integer.toHexString(hashCode()));

		builder.append(" {");
		builder.append("text:").append(text);
		builder.append(", listener:");
		builder.append(listener.getClass().getSimpleName()).append("#").append(Integer.toHexString(listener.hashCode()));
		builder.append("}");

		if (getLinkedFxNode() != null) {
			builder.append("  linked");
		}

		System.out.println(builder.toString());
	}




	public interface Listener {


		void onAction();

	}

}
