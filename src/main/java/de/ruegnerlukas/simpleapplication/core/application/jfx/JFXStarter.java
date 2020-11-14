package de.ruegnerlukas.simpleapplication.core.application.jfx;

import javafx.stage.Stage;

import java.util.function.Consumer;

/**
 * The starter for a javafx-application.
 */
public class JFXStarter {


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
