package de.ruegnerlukas.simpleapplication.core.presentation.uimodule;

import de.ruegnerlukas.simpleapplication.common.events.ListenableEvent;
import de.ruegnerlukas.simpleapplication.common.events.OnEvent;
import de.ruegnerlukas.simpleapplication.common.events.ListenableEventGroup;
import de.ruegnerlukas.simpleapplication.common.events.TriggerableEventGroup;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.presentation.utils.Anchors;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * A UIModule is a larger chunk of a ui.
 * A module can be composed of modules itself.
 */
@Slf4j
public class UIModule extends AnchorPane {


	/**
	 * The view of this module.
	 */
	private final ModuleView view;

	/**
	 * The controller of this module.
	 */
	private final ModuleController controller;




	/**
	 * @param view       the {@link ModuleView}
	 * @param controller the {@link ModuleController}
	 */
	public UIModule(final ModuleView view, final ModuleController controller) {
		Validations.INPUT.notNull(view, "The view must not be null.");
		Validations.INPUT.notNull(controller, "The controller must not be null.");
		this.view = view;
		this.controller = controller;
	}




	/**
	 * @param view       the {@link ModuleView}
	 * @param controller the {@link ModuleController}
	 * @param fxmlURL    the url of the fxml-file for this module
	 */
	public UIModule(final ModuleView view, final ModuleController controller, final URL fxmlURL) {
		Validations.INPUT.notNull(view, "The view must not be null.");
		Validations.INPUT.notNull(controller, "The controller must not be null.");
		Validations.INPUT.notNull(fxmlURL, "The url of the fxml-file must not be null.");

		this.view = view;
		this.controller = controller;

		try {
			final Parent fxmlRoot = loadFXML(fxmlURL, view);
			view.initializeView();
			Anchors.setAnchors(fxmlRoot, 0);
			this.getChildren().add(fxmlRoot);
			if (fxmlRoot instanceof Region) {
				final Region fxmlRegion = (Region) fxmlRoot;
				this.setMinSize(fxmlRegion.getMinWidth(), fxmlRegion.getMinHeight());
				this.setPrefSize(fxmlRegion.getPrefWidth(), fxmlRegion.getPrefHeight());
				this.setMaxSize(fxmlRegion.getMaxWidth(), fxmlRegion.getMaxHeight());
			}
		} catch (IOException e) {
			log.error("Could not load fxml file.", e);
		}

		ListenableEventGroup listenableEventGroup = new ListenableEventGroup(view.getEventEndpoints());
		controller.initialize(
				listenableEventGroup,
				new TriggerableEventGroup(view.getFunctionEndpoints())
		);

		attachAnnotatedEventListeners(controller, listenableEventGroup);
	}




	/**
	 * Finds methods with {@link OnEvent}-annotation and adds a listener the event in the given group.
	 *
	 * @param controller the controller to with annotated methods
	 * @param events     the group containing the events
	 */
	private void attachAnnotatedEventListeners(final ModuleController controller, final ListenableEventGroup events) {
		final Class<?> controllerClass = controller.getClass();
		for (Method method : controllerClass.getDeclaredMethods()) {
			if (method.isAnnotationPresent(OnEvent.class)) {
				if (method.getParameterCount() > 1) {
					log.warn("The annotated method {} has an invalid amount of parameters: {}.", method.getName(), method.getParameterCount());
					continue;
				}
				final OnEvent onEvent = method.getAnnotation(OnEvent.class);
				final String eventName = onEvent.eventName();
				final ListenableEvent<Object> event = events.getEvent(eventName);
				if (event != null) {
					event.addListener(data -> {
						try {
							if (method.getParameterCount() == 0) {
								method.invoke(controller);
							} else {
								method.invoke(controller, data);
							}
						} catch (IllegalAccessException | InvocationTargetException e) {
							e.printStackTrace();
						}
					});
				} else {
					log.warn("The event {} for the annotated method {} was not found.", eventName, method.getName());
				}
			}
		}
	}




	/**
	 * Loads the fxml-file from the given url.
	 *
	 * @param fxmlPath   the path to the fxml-file
	 * @param controller the controller object
	 * @return the root of the fxml-file
	 * @throws IOException when something went wrong
	 */
	private static Parent loadFXML(final URL fxmlPath, final Object controller) throws IOException {
		FXMLLoader loader = new javafx.fxml.FXMLLoader(fxmlPath);
		if (controller != null) {
			loader.setController(controller);
		}
		return loader.load();
	}


}
