package de.ruegnerlukas.simpleapplication;

import de.ruegnerlukas.simpleapplication.common.listeners.ApplicationListener;
import de.ruegnerlukas.simpleapplication.common.listeners.ApplicationListenerScanner;

import java.util.ArrayList;
import java.util.List;

public class SimpleApplication {


	private static String rootPackage = null;
	private static List<ApplicationListener> listeners = new ArrayList<>();




	public static void setProjectRootPackage(String rootPackage) {
		SimpleApplication.rootPackage = rootPackage;
	}




	public static void registerApplicationListener(ApplicationListener listener) {
		listeners.add(listener);
		listener.onListenerRegistered();
	}




	public static void deregisterApplicationListener(ApplicationListener listener) {
		listeners.remove(listener);
	}




	public static void startApplication() {
		ApplicationListenerScanner.process(rootPackage);
		listeners.forEach(ApplicationListener::onApplicationStartup);
	}




	public static void closeApplication() {
		listeners.forEach(ApplicationListener::onApplicationClose);
	}


}
