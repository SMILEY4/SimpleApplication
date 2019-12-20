package de.ruegnerlukas.simpleapplication.common.listeners;

public interface ApplicationListener {


	default void onListenerRegistered() {
	}

	void onApplicationStartup();

	void onApplicationClose();

}
