package de.ruegnerlukas.simpleapplication.common.eventbus;

public enum ThreadMode {

	/**
	 * event is received on same thread as the thread that published it.
	 */
	POSTING,

	/**
	 * event is received on a "completely new" thread.
	 */
	ASYNC,

	/**
	 * event is received on javafx-thread.
	 */
	JFX
}
