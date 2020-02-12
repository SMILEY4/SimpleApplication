package de.ruegnerlukas.simpleapplication.core.presentation.views;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

public class Popup {


	/**
	 * The stage of this popup.
	 */
	@Getter
	private final Stage stage;

	/**
	 * The scene of this popup.
	 */
	@Getter
	private final Scene scene;

	/**
	 * Whether to show or showAndWait
	 */
	private final boolean wait;




	/**
	 * @param owner the owner of this popup
	 * @param view  the view shown in this popup
	 */
	public Popup(final Stage owner, final View view, final boolean wait) {
		this.wait = wait;
		scene = new Scene(view.getNode(), view.getWidth(), view.getHeight());
		scene.setRoot(view.getNode());
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(owner);
		stage.setTitle(view.getTitle());
		stage.setScene(this.scene);
	}




	/**
	 * Shows this popup.
	 */
	public void show() {
		if (wait) {
			stage.showAndWait();
		} else {
			stage.show();
		}
	}




	/**
	 * Closes this popup.
	 */
	public void close() {
		stage.close();
	}


}
