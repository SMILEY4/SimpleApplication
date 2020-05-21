package simpleui.core.prebuild;

import javafx.scene.Node;
import javafx.scene.control.Button;
import simpleui.core.SElement;

public class SButton extends SElement {


	private final String text;
	private final Listener listener;




	public SButton(final String text) {
		this.text = text;
		this.listener = () -> {
		};
	}




	public SButton(final String text, final Listener listener) {
		this.text = text;
		this.listener = listener;
	}




	@Override
	public Node getFxNode() {
		Button button = new Button(text);
		button.setOnAction(e -> listener.onAction());
		return button;
	}




	@Override
	public void onChildRerenderRequest() {
	}




	public interface Listener {


		void onAction();

	}

}
