package de.ruegnerlukas.simpleapplication.common.eventbus;

enum ThreadMode {

	/**
	 * event is received on same thread as the thread that published it.
	 */
	POSTING,

	/**
	 * event is received on "completely new" thread.
	 */
	ASYNC,

	/**
	 * event is received on javafx-thread.
	 */
	JFX
}
