package de.ruegnerlukas.test;

import de.ruegnerlukas.simpleapplication.SimpleApplication;
import de.ruegnerlukas.simpleapplication.core.presentation.JFXApplication;
import de.ruegnerlukas.simpleapplication.core.presentation.PresentationConfig;
import de.ruegnerlukas.simpleapplication.core.presentation.uimodule.ModuleFactory;
import de.ruegnerlukas.simpleapplication.core.presentation.uimodule.UIModule;

import java.net.MalformedURLException;


public final class TestApplication {


	/**
	 *
	 */
	private static final int WIDTH = 600;

	/**
	 *
	 */
	private static final int HEIGHT = 450;

	/**
	 *
	 */
	private static final String TITLE = "Test App";




	/**
	 * Utility class
	 */
	private TestApplication() {
	}




	/**
	 * The main.
	 */
	public static void main(final String[] args) throws MalformedURLException {

		// Set project root package for automatic scanning of annotations
		SimpleApplication.setProjectRootPackage("de.ruegnerlukas");

		// Set base presentation config
		SimpleApplication.setPresentationConfig(PresentationConfig.builder()
				.width(WIDTH)
				.height(HEIGHT)
				.title(TITLE)
				.baseModule(new ModuleFactory() {
					@Override
					public UIModule module() {
						return new UIModule(
								new ModuleViewImpl(),
								new ModuleControllerImpl(),
								JFXApplication.class.getResource("/base.fxml"));
					}
				})
				.build());

		// start app
		SimpleApplication.startApplication();

		// stop app
		SimpleApplication.closeApplication();
	}


}
