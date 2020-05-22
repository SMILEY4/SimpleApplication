package simpleui.core.nodes;

import javafx.scene.Node;
import javafx.scene.control.Button;

public class SButton extends SNode {


	private String text;


	private ButtonListener listener;




	public SButton(String text) {
		this.text = text;
	}




	public SButton(String text, ButtonListener listener) {
		this.text = text;
		this.listener = listener;
	}




	@Override
	public Node buildFxNode() {
		Button button = new Button(text);
		if (listener != null) {
			button.setOnAction(e -> listener.onAction());
		}
		return button;
	}




	@Override
	public boolean mutate(final SNode other) {
		if (other instanceof SButton) {
			final SButton btnOther = (SButton) other;
			if (!this.text.equals(btnOther.text)) {
				System.out.println("mutate button - text: " + this.text + " -> " + btnOther.text);
				patchText(btnOther.text);
			}
			return false;
		} else {
			System.out.println("mutate button - rebuild");
			return true;
		}
	}




	private void patchText(String newText) {
		this.text = newText;
		if (getLinkedFxNode() != null) {
			((Button) getLinkedFxNode()).setText(newText);
		}
	}




	@Override
	public void print(final int level) {
		System.out.println(" ".repeat(level * 3)
				+ getClass().getSimpleName()
				+ " #" + Integer.toHexString(hashCode())
				+ " {"
				+ "text:" + text
				+ "}"
				+ (getLinkedFxNode() != null ? " (fx)" : "")
		);
	}




	public interface ButtonListener {


		void onAction();

	}

}
