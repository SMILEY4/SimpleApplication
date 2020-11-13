package de.ruegnerlukas.simpleapplication.core.application;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.function.Consumer;

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
	private static void start(final Consumer<Stage> startAction, final Runnable stopAction) {
		JFXApplication.startAction = startAction;
		JFXApplication.stopAction = stopAction;
		launch();
	}




	@Override
	public void start(final Stage primaryStage) {
		startAction.accept(primaryStage);
	}




	@Override
	public void stop() {
		stopAction.run();
	}




	/**
	 * The starter for a javafx-application.
	 */
	static class JFXStarter {


		/**
		 * Starts the javafx application.
		 *
		 * @param startAction the action when the javafx application was started
		 * @param stopAction  the action when the javafx application was stopped
		 */
		public void start(final Consumer<Stage> startAction, final Runnable stopAction) {
			JFXApplication.start(startAction, stopAction);
		}

	}

}
