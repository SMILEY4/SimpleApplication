package de.ruegnerlukas.simpleapplication;

import de.ruegnerlukas.simpleapplication.common.events.events.EmptyEvent;
import de.ruegnerlukas.simpleapplication.common.events.events.GenericEvent;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.presentation.PresentationConfig;
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
	static void setPresentationConfig(final PresentationConfig config) {
		JFXApplication.presentationConfig = config;
	}




	/**
	 * Starts the JavaFX-Application
	 */
	static void start() {
		launch();
	}




	@Override
	public void start(final Stage primaryStage) throws Exception {
		JFXApplication.primaryStage = primaryStage;
		setScene(presentationConfig);
		SimpleApplication.getEvents().publish(ApplicationConstants.EVENT_START, new EmptyEvent());
		SimpleApplication.getPluginManager().load(ApplicationConstants.SYSTEM_ID_JFXROOT);
	}




	/**
	 * Sets the given {@link PresentationConfig} as the current scene of the primary stage
	 *
	 * @param config the presentation config
	 */
	public static void setScene(final PresentationConfig config) {
		Validations.INPUT.notNull(config, "Presentation config must not be null.");
		SimpleApplication.getPluginManager().unload(ApplicationConstants.SYSTEM_ID_SCENE_PREFIX + presentationConfig.getId());
		setPresentationConfig(config);
		final Scene scene = new Scene(config.getBaseModule().module(), config.getWidth(), config.getHeight());
		primaryStage.setScene(scene);
		primaryStage.setTitle(config.getTitle());
		primaryStage.show();
		SimpleApplication.getEvents().publish(ApplicationConstants.EVENT_CHANGE_SCENE, new GenericEvent<>(config));
		SimpleApplication.getPluginManager().load(ApplicationConstants.SYSTEM_ID_SCENE_PREFIX + config.getId());
	}




	@Override
	public void stop() {
		SimpleApplication.onStopApplication();
	}

}
