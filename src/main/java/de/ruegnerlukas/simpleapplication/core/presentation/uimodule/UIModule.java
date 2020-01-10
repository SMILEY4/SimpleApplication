package de.ruegnerlukas.simpleapplication.core.presentation.uimodule;

import de.ruegnerlukas.simpleapplication.SimpleApplication;
import de.ruegnerlukas.simpleapplication.common.events.ListenableEventSourceGroup;
import de.ruegnerlukas.simpleapplication.common.events.TriggerableEventSourceGroup;
import de.ruegnerlukas.simpleapplication.common.extensions.ExtensionPoint;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.presentation.utils.Anchors;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

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
		initView(view);
		initController(controller, view);
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
			initView(view);
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

		initController(controller, view);
	}




	/**
	 * Initializes the view of this module
	 */
	private void initView(final ModuleView view) {
		view.initializeView(this);
	}




	/**
	 * Initializes the controller of this module
	 *
	 * @param controller the {@link ModuleController}
	 * @param view       the {@link ModuleView}
	 */
	private void initController(final ModuleController controller, final ModuleView view) {
		final ListenableEventSourceGroup listenableEventGroup = new ListenableEventSourceGroup(view.getEventEndpoints());
		controller.initialize(
				listenableEventGroup,
				new TriggerableEventSourceGroup(view.getFunctionEndpoints())
		);
		Optional.ofNullable(controller.getExtensionPoints()).ifPresent(extensionPoints -> {
			for (ExtensionPoint extensionPoint : extensionPoints) {
				SimpleApplication.getExtensionHandler().register(extensionPoint);
			}
		});
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
