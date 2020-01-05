package de.ruegnerlukas.simpleapplication;

public final class ApplicationConstants {


	/**
	 * Utility class
	 */
	private ApplicationConstants() {

	}




	/**
	 * The id of the root application. All plugins automatically depend on this id.
	 */
	public static final String SYSTEM_ID_ROOT = "application_root";

	/**
	 * The id of the javafx application. Plugins can depend on this and will be loaded after the ui was first visible.
	 */
	public static final String SYSTEM_ID_JFXROOT = "application_jfxroot";

	/**
	 * After the application was first created and basic systems are initialized.
	 */
	public static final String EVENT_INITIALIZE = "application_init";

	/**
	 * A new stage/{@link de.ruegnerlukas.simpleapplication.core.presentation.PresentationConfig} is loaded and visible to the user.
	 * Called before {@link ApplicationConstants#EVENT_START}.
	 */
	public static final String EVENT_CHANGE_SCENE = "application_change_scene";

	/**
	 * The application is first visible to the user. Called after {@link ApplicationConstants#EVENT_CHANGE_SCENE}.
	 */
	public static final String EVENT_START = "application_start";


	/**
	 * Final event right before the application is closed.
	 */
	public static final String EVENT_STOP = "application_destroy";

	/**
	 * A plugin was loaded.
	 */
	public static final String EVENT_PLUGIN_LOADED = "plugin_loaded";

	/**
	 * A plugin was unloaded.
	 */
	public static final String EVENT_PLUGIN_UNLOADED = "plugin_unloaded";


	/**
	 * A system was loaded.
	 */
	public static final String EVENT_SYSTEM_LOADED = "system_loaded";

	/**
	 * A system was unloaded.
	 */
	public static final String EVENT_SYSTEM_UNLOADED = "system_unloaded";
}
