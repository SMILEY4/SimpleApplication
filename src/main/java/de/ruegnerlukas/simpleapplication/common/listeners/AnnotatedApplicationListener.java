package de.ruegnerlukas.simpleapplication.common.listeners;

import de.ruegnerlukas.simpleapplication.common.listeners.annotations.OnAppClose;
import de.ruegnerlukas.simpleapplication.common.listeners.annotations.OnAppStartup;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotatedApplicationListener implements ApplicationListener {


	/**
	 * The object of the annotated listener
	 */
	private final Object listenerObject;

	/**
	 * All methods of the object annotated with {@link OnAppStartup}
	 */
	private final List<Method> methodsOnApplicationStartup;

	/**
	 * All methods of the object annotated with {@link OnAppClose}
	 */
	private final List<Method> methodsOonApplicationExit;




	/**
	 * @param listenerClass the class of the annotated listener
	 */
	AnnotatedApplicationListener(final Class<?> listenerClass) {
		Validations.INPUT.notNull(listenerClass, "Class of the listener is null.");
		this.listenerObject = loadObject(listenerClass);
		Validations.STATE.notNull(listenerObject, "Class of the listener could not be instantiated.");
		this.methodsOnApplicationStartup = loadMethods(listenerClass, OnAppStartup.class);
		this.methodsOonApplicationExit = loadMethods(listenerClass, OnAppClose.class);
	}




	/**
	 * Instantiates the given class and returns the object or null.
	 *
	 * @param listenerClass the class of the annotated listener
	 */
	private Object loadObject(final Class<?> listenerClass) {
		Object obj = null;
		try {
			obj = listenerClass.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}




	/**
	 * Searches the given class of the listener for methods annotated with the given annotation.
	 *
	 * @param listenerClass the class of the annotated listener
	 * @param annotation    the annotation to check
	 * @return a list of methods
	 */
	private List<Method> loadMethods(final Class<?> listenerClass, final Class<? extends Annotation> annotation) {
		List<Method> methods = new ArrayList<>();
		for (Method m : listenerClass.getDeclaredMethods()) {
			if (m.isAnnotationPresent(annotation)) {
				methods.add(m);
			}
		}
		return methods;
	}




	@Override
	public void onApplicationStartup() {
		methodsOnApplicationStartup.forEach(m -> {
			try {
				m.invoke(listenerObject);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		});
	}




	@Override
	public void onApplicationClose() {
		methodsOonApplicationExit.forEach(m -> {
			try {
				m.invoke(listenerObject);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		});
	}

}
