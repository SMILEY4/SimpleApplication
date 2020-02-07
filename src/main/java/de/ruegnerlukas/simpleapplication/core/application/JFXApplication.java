package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.callbacks.Callback;
import de.ruegnerlukas.simpleapplication.common.callbacks.EmptyCallback;
import javafx.application.Application;
import javafx.stage.Stage;

public class JFXApplication extends Application {


	/**
	 * Callback when the javafx application was started.
	 */
	private static Callback<Stage> startCallback;

	/**
	 * Callback when the javafx application was stopped.
	 */
	private static EmptyCallback stopCallback;




	/**
	 * Starts the javafx application.
	 *
	 * @param startCallback callback when the javafx application was started
	 * @param stopCallback  callback when the javafx application was stopped
	 */
	static void start(final Callback<Stage> startCallback, final EmptyCallback stopCallback) {
		JFXApplication.startCallback = startCallback;
		JFXApplication.stopCallback = stopCallback;
		launch();
	}




	@Override
	public void start(final Stage primaryStage) {
		startCallback.execute(primaryStage);
	}




	@Override
	public void stop() {
		stopCallback.execute();
	}

}
