package de.ruegnerlukas.simpleapplication;

import de.ruegnerlukas.simpleapplication.common.applicationlisteners.ApplicationListener;
import de.ruegnerlukas.simpleapplication.common.applicationlisteners.ApplicationListenerScanner;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.presentation.JFXApplication;
import de.ruegnerlukas.simpleapplication.core.presentation.PresentationConfig;

import java.util.ArrayList;
import java.util.List;

public final class SimpleApplication {


	/**
	 * Utility class
	 */
	private SimpleApplication() {
	}




	/**
	 * The root package containing the application.
	 */
	private static String applicationRootPackage = null;

	/**
	 * The registerd {@link ApplicationListener}s.
	 */
	private static List<ApplicationListener> listeners = new ArrayList<>();

	/**
	 * Whether the application was started.
	 */
	private static boolean applicationStated = false;




	/**
	 * @param rootPackage the root package containing the application.
	 */
	public static void setProjectRootPackage(final String rootPackage) {
		Validations.INPUT.notBlank(rootPackage, "The rootPackage may not be null or empty.");
		SimpleApplication.applicationRootPackage = rootPackage;
	}




	/**
	 * @param config the presentation configuration
	 */
	public static void setPresentationConfig(final PresentationConfig config) {
		JFXApplication.setPresentationConfig(config);
	}




	/**
	 * Registers the given {@link ApplicationListener}
	 *
	 * @param listener the {@link ApplicationListener}
	 */
	public static void registerApplicationListener(final ApplicationListener listener) {
		Validations.INPUT.notNull(listener, "The listener may not be null.");
		listeners.add(listener);
		listener.onListenerRegistered();
	}




	/**
	 * Deregisters the given {@link ApplicationListener}
	 *
	 * @param listener the {@link ApplicationListener}
	 */
	public static void deregisterApplicationListener(final ApplicationListener listener) {
		listeners.remove(listener);
	}




	/**
	 * Starts the application.
	 */
	public static void startApplication() {
		Validations.STATE.isFalse(applicationStated, "The application was already started.");
		if (applicationRootPackage != null) {
			ApplicationListenerScanner.process(applicationRootPackage);
		}
		listeners.forEach(ApplicationListener::onApplicationStartup);
		JFXApplication.start();
	}




	/**
	 * Stops and closes the application.
	 */
	public static void closeApplication() {
		Validations.STATE.isTrue(applicationStated, "The application is not running.");
		listeners.forEach(ApplicationListener::onApplicationClose);
	}


}
