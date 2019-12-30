package de.ruegnerlukas.simpleapplication.common.applicationlisteners;

public interface ApplicationListener {


	/**
	 * Called when this listener was registered.
	 */
	default void onListenerRegistered() {
	}

	/**
	 * Called on application startup.
	 */
	void onApplicationStartup();

	/**
	 * Called when the application is closed.
	 */
	void onApplicationClose();

}
