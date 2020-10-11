package de.ruegnerlukas.simpleapplication.testapp;


import de.ruegnerlukas.simpleapplication.core.presentation.Anchors;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedComboBox;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;

@SuppressWarnings ("ALL")
public class JFXTestApp extends Application {


	public static void main(String[] args) {
		launch(args);
	}




	@Override
	public void start(final Stage stage) throws Exception {

		final ExtendedComboBox<String> comboBox = new ExtendedComboBox<>();
		comboBox.setItems(List.of("Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"), "Samstag");
		comboBox.setType(ExtendedComboBox.ComboBoxType.SEARCHABLE);
		comboBox.setListener((prev, next) -> {
			System.out.println("EVENT: " + prev + " -> " + next);
		});

		AnchorPane pane = new AnchorPane();
		pane.getChildren().add(comboBox);
		Anchors.setAnchors(comboBox, 50, null, 50, 100);

		Scene scene = new Scene(pane, 500, 500);
		stage.setScene(scene);
		stage.show();
	}




	private long lastTime = 0;




	private void measureFPS() {
		AnimationTimer frameRateMeter = new AnimationTimer() {

			@Override
			public void handle(long now) {
				System.out.println(((now - lastTime) / 1000000.0));
				lastTime = now;
			}
		};

		frameRateMeter.start();
	}


}
