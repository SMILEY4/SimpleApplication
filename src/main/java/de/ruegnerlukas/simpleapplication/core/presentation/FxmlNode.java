package de.ruegnerlukas.simpleapplication.core.presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

public class FxmlNode extends AnchorPane {


	/**
	 * Creates a new {@link FxmlNode} from the given fxml-file.
	 *
	 * @param fxmlPath the path to the fxml-file
	 */
	public FxmlNode(final URL fxmlPath) {
		this(fxmlPath, null);
	}




	/**
	 * Creates a new {@link FxmlNode} from the given fxml-file and and creates a new controller from the given class.
	 *
	 * @param fxmlPath        the path to the fxml-file
	 * @param controllerClass the class of the controller. The class must have an empty constructor.
	 */
	public FxmlNode(final URL fxmlPath, final Class<?> controllerClass) {
		this(fxmlPath, createController(controllerClass));
	}




	/**
	 * Creates a new {@link FxmlNode} from the given fxml-file with the given controller.
	 *
	 * @param fxmlPath   the path to the fxml-file
	 * @param controller the controller
	 */
	public FxmlNode(final URL fxmlPath, final Object controller) {
		try {
			final Parent fxmlRoot = loadFXML(fxmlPath, controller);
			AnchorPane.setTopAnchor(fxmlRoot, 0.0);
			AnchorPane.setBottomAnchor(fxmlRoot, 0.0);
			AnchorPane.setLeftAnchor(fxmlRoot, 0.0);
			AnchorPane.setRightAnchor(fxmlRoot, 0.0);
			this.getChildren().add(fxmlRoot);
			if (fxmlRoot instanceof Region) {
				final Region fxmlRegion = (Region) fxmlRoot;
				this.setMinSize(fxmlRegion.getMinWidth(), fxmlRegion.getMinHeight());
				this.setPrefSize(fxmlRegion.getPrefWidth(), fxmlRegion.getPrefHeight());
				this.setMaxSize(fxmlRegion.getMaxWidth(), fxmlRegion.getMaxHeight());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	/**
	 * Creates a instance of the given class.
	 *
	 * @param controllerClass the class
	 * @return the created object or null
	 */
	private static Object createController(final Class<?> controllerClass) {
		if (controllerClass == null) {
			return null;
		} else {
			try {
				return controllerClass.getDeclaredConstructor().newInstance();
			} catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
				System.err.println("Could not create controller object: No matching constructor found (empty contructor)."
						+ "Controller wil not be set.");
				return null;
			}
		}
	}




	/**
	 * Loads the given fxml-file and sets the controller (if not null).
	 *
	 * @param fxmlPath   the path to the fxml-file
	 * @param controller the controller or null
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
