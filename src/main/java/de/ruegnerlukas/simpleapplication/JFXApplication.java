package de.ruegnerlukas.simpleapplication;

import de.ruegnerlukas.simpleapplication.common.events.specializedevents.EmptyEventPackage;
import javafx.application.Application;
import javafx.stage.Stage;

public class JFXApplication extends Application {



	/**
	 * The primary javafx stage of the application
	 */
	private static Stage primaryStage = null;






	/**
	 * Starts the JavaFX-Application
	 */
	static void start() {
		launch();
	}




	@Override
	public void start(final Stage primaryStage) throws Exception {
		JFXApplication.primaryStage = primaryStage;
		SimpleApplication.getEvents().publish(ApplicationConstants.EVENT_START, new EmptyEventPackage());
		SimpleApplication.getPluginManager().load(ApplicationConstants.SYSTEM_ID_JFXROOT);
	}




//	/**
//	 * Sets the given {@link PresentationConfig} as the current scene of the primary stage
//	 *
//	 * @param config the presentation config
//	 */
//	public static void setScene(final PresentationConfig config) {
//		Validations.INPUT.notNull(config).exception("Presentation config must not be null.");
//		SimpleApplication.getPluginManager().unload(ApplicationConstants.SYSTEM_ID_SCENE_PREFIX + presentationConfig.getId());
//		setPresentationConfig(config);
//		final Scene scene = new Scene(config.getBaseModule().module(), config.getWidth(), config.getHeight());
//		primaryStage.setScene(scene);
//		primaryStage.setTitle(config.getTitle());
//		primaryStage.show();
//		SimpleApplication.getEvents().publish(ApplicationConstants.EVENT_CHANGE_SCENE, new EventPackage<>(config));
//		SimpleApplication.getPluginManager().load(ApplicationConstants.SYSTEM_ID_SCENE_PREFIX + config.getId());
//	}
//



	@Override
	public void stop() {
		SimpleApplication.onStopApplication();
	}

}
