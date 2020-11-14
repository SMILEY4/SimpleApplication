package de.ruegnerlukas.simpleapplication.core.application.jfx;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class JFXApplication extends Application {


	/**
	 * The action when the javafx application was started.
	 */
	private static Consumer<Stage> startAction;

	/**
	 * The action when the javafx application was stopped.
	 */
	private static Runnable stopAction;




	/**
	 * Starts the javafx application.
	 *
	 * @param startAction the action when the javafx application was started
	 * @param stopAction  the action when the javafx application was stopped
	 */
	public static void start(final Consumer<Stage> startAction, final Runnable stopAction) {
		JFXApplication.startAction = startAction;
		JFXApplication.stopAction = stopAction;
		launch();
	}




	@Override
	public void start(final Stage primaryStage) {
		log.debug("Started JFX-Application");
		startAction.accept(primaryStage);
	}




	@Override
	public void stop() {
		log.debug("Stopping JFX-Application");
		stopAction.run();
	}


}
