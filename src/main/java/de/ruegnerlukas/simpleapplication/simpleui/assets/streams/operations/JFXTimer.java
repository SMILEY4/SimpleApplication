package de.ruegnerlukas.simpleapplication.simpleui.assets.streams.operations;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class JFXTimer {


	/**
	 * The actual timer.
	 */
	private Timeline timeline;




	/**
	 * @param duration The duration of the timer.
	 * @param action   The action to trigger when the timer runs out.
	 */
	public JFXTimer(final Duration duration, final Runnable action) {
		timeline = new Timeline(new KeyFrame(duration, e -> action.run()));
	}




	/**
	 * Starts the timer. Has no effect when the timer is already running.
	 */
	public void start() {
		timeline.play();
	}




	/**
	 * Stops the timer. Has no effect when the timer is not running.
	 */
	public void stop() {
		timeline.stop();
	}




	/**
	 * @return whether the timer is currently active and running.
	 */
	public boolean isRunning() {
		return timeline.getStatus() == Animation.Status.RUNNING;
	}


}
