package de.ruegnerlukas.simpleapplication.core.presentation;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JFXApplication extends Application {


	/**
	 * The presentation configuration
	 */
	private static PresentationConfig presentationConfig;

	/**
	 * The primary javafx stage of the application
	 */
	private static Stage primaryStage = null;




	/**
	 * @param config the presentation configuration
	 */
	public static void setPresentationConfig(final PresentationConfig config) {
		JFXApplication.presentationConfig = config;
	}




	/**
	 * Starts the JavaFX-Application
	 */
	public static void start() {
		launch();
	}




	@Override
	public void start(final Stage primaryStage) throws Exception {
		JFXApplication.primaryStage = primaryStage;
		setScene(presentationConfig);
	}




	/**
	 * Sets the given {@link PresentationConfig} as the current scene of the primary stage
	 *
	 * @param config the presentation config
	 */
	public static void setScene(final PresentationConfig config) {
		Validations.PRESENCE.notNull(presentationConfig, "Presentation config for primary stage is not set (null).");
		final Scene scene = new Scene(config.getBaseModule().module(), config.getWidth(), config.getHeight());
		primaryStage.setScene(scene);
		primaryStage.setTitle(config.getTitle());
		primaryStage.show();
	}


}
