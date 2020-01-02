package de.ruegnerlukas.simpleapplication;

public final class ApplicationEvents {


	/**
	 * Utility class
	 */
	private ApplicationEvents() {

	}




	/**
	 * After the application was first created and basic systems are initialized.
	 */
	public static final String INITIALIZE = "application_init";

	/**
	 * The application is first visible to the user.
	 */
	public static final String START = "application_start";

	/**
	 * A new stage/{@link de.ruegnerlukas.simpleapplication.core.presentation.PresentationConfig} is loaded and visible to the user.
	 */
	public static final String CHANGE_SCENE = "application_change_scene";

	/**
	 * Final event right before the application is closed.
	 */
	public static final String STOP = "application_destroy";

}
