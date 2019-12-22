package de.ruegnerlukas.simpleapplication.common.listeners;

import de.ruegnerlukas.simpleapplication.SimpleApplication;
import de.ruegnerlukas.simpleapplication.common.listeners.annotations.AppListener;
import org.reflections.Reflections;

import java.util.Set;

public final class ApplicationListenerScanner {


	/**
	 * Utility class
	 */
	private ApplicationListenerScanner() {
	}




	/**
	 * Scans the given package for listener annotations and registers the listeners.
	 *
	 * @param rootPackage the root package of the project
	 */
	public static void process(final String rootPackage) {
		final Reflections reflections = new Reflections(rootPackage);
		final Set<Class<?>> listeners = reflections.getTypesAnnotatedWith(AppListener.class);
		for (Class<?> c : listeners) {
			final AnnotatedApplicationListener listener = new AnnotatedApplicationListener(c);
			SimpleApplication.registerApplicationListener(listener);
		}
	}


}
