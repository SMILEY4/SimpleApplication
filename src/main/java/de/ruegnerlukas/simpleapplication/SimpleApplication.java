package de.ruegnerlukas.simpleapplication;

import de.ruegnerlukas.simpleapplication.common.listeners.ApplicationListener;
import de.ruegnerlukas.simpleapplication.common.listeners.ApplicationListenerScanner;
import de.ruegnerlukas.simpleapplication.core.presentation.JFXApplication;

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
	 * @param rootPackage the root package containing the application.
	 */
	public static void setProjectRootPackage(final String rootPackage) {
		SimpleApplication.applicationRootPackage = rootPackage;
	}




	/**
	 * Registers the given {@link ApplicationListener}
	 *
	 * @param listener the {@link ApplicationListener}
	 */
	public static void registerApplicationListener(final ApplicationListener listener) {
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
		ApplicationListenerScanner.process(applicationRootPackage);
		listeners.forEach(ApplicationListener::onApplicationStartup);
		JFXApplication.start();
	}




	/**
	 * Stops and closes the application.
	 */
	public static void closeApplication() {
		listeners.forEach(ApplicationListener::onApplicationClose);
	}


}
