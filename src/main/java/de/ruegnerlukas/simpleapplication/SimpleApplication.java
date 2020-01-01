package de.ruegnerlukas.simpleapplication;

import de.ruegnerlukas.simpleapplication.common.plugins.PluginManager;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.presentation.JFXApplication;
import de.ruegnerlukas.simpleapplication.core.presentation.PresentationConfig;

public final class SimpleApplication {


	/**
	 * Whether the application was started.
	 */
	private static boolean applicationStated = false;


	/**
	 * The manager for plugins.
	 */
	private static PluginManager pluginManager = new PluginManager();




	/**
	 * Utility class
	 */
	private SimpleApplication() {
	}




	/**
	 * @return the plugin manager
	 */
	public static PluginManager getPluginManager() {
		return SimpleApplication.pluginManager;
	}




	/**
	 * @param config the presentation configuration
	 */
	public static void setPresentationConfig(final PresentationConfig config) {
		JFXApplication.setPresentationConfig(config);
	}




	/**
	 * Starts the application.
	 */
	public static void startApplication() {
		Validations.STATE.isFalse(SimpleApplication.applicationStated, "The application was already started.");
		JFXApplication.start();
	}




	/**
	 * Stops and closes the application.
	 */
	public static void closeApplication() {
		Validations.STATE.isTrue(SimpleApplication.applicationStated, "The application is not running.");
	}


}
