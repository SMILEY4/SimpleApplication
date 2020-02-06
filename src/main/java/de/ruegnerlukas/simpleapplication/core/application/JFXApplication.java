package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.callbacks.Callback;
import de.ruegnerlukas.simpleapplication.common.callbacks.EmptyCallback;
import javafx.application.Application;
import javafx.stage.Stage;

public class JFXApplication extends Application {

	private static Callback<Stage> startCallback;
	private static EmptyCallback stopCallback;


	static void start(final Callback<Stage> startCallback, final EmptyCallback stopCallback) {
		JFXApplication.startCallback = startCallback;
		JFXApplication.stopCallback = stopCallback;
		launch();
	}




	@Override
	public void start(final Stage primaryStage) throws Exception {
		startCallback.execute(primaryStage);
	}




	@Override
	public void stop() {
		stopCallback.execute();
	}

}
