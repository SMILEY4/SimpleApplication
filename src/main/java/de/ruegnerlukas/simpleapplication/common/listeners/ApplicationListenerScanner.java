package de.ruegnerlukas.simpleapplication.common.listeners;

import de.ruegnerlukas.simpleapplication.SimpleApplication;
import de.ruegnerlukas.simpleapplication.common.listeners.annotations.AppListener;
import org.reflections.Reflections;

import java.util.Set;

public class ApplicationListenerScanner {


	public static void process(final String rootPackage) {
		final Reflections reflections = new Reflections(rootPackage);
		final Set<Class<?>> listeners = reflections.getTypesAnnotatedWith(AppListener.class);
		for (Class<?> c : listeners) {
			final AnnotatedApplicationListener listener = new AnnotatedApplicationListener(c);
			SimpleApplication.registerApplicationListener(listener);
		}
	}


}
