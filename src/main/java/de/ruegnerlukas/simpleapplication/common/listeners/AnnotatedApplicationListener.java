package de.ruegnerlukas.simpleapplication.common.listeners;

import de.ruegnerlukas.simpleapplication.common.listeners.annotations.OnAppClose;
import de.ruegnerlukas.simpleapplication.common.listeners.annotations.OnAppStartup;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotatedApplicationListener implements ApplicationListener {


	private final Class<?> clazz;
	private final Object object;
	private final List<Method> methodsOnApplicationStartup;
	private final List<Method> methodsOonApplicationExit;




	public AnnotatedApplicationListener(Class<?> clazz) {
		this.clazz = clazz;
		this.object = loadObject(clazz);
		this.methodsOnApplicationStartup = loadMethods(clazz, OnAppStartup.class);
		this.methodsOonApplicationExit = loadMethods(clazz, OnAppClose.class);
	}




	private Object loadObject(Class<?> clazz) {
		Object obj = null;
		try {
			obj = clazz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}




	private List<Method> loadMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
		List<Method> methods = new ArrayList<>();
		for (Method m : clazz.getDeclaredMethods()) {
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
				m.invoke(object);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		});
	}




	@Override
	public void onApplicationClose() {
		methodsOonApplicationExit.forEach(m -> {
			try {
				m.invoke(object);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		});
	}

}
