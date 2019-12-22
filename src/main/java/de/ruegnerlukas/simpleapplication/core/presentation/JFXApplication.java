package de.ruegnerlukas.simpleapplication.core.presentation;

import javafx.application.Application;
import javafx.stage.Stage;

public class JFXApplication extends Application {


	/**
	 * Starts the JavaFX-Application
	 */
	public static void start() {
		launch();
	}




	@Override
	public void start(final Stage primaryStage) throws Exception {
		System.out.println("JFXApplication.start");
	}

}
