package de.ruegnerlukas.simpleapplication;

import de.ruegnerlukas.simpleapplication.common.events.EventBus;
import de.ruegnerlukas.simpleapplication.common.events.EventBusImpl;
import de.ruegnerlukas.simpleapplication.common.events.specializedevents.EmptyEventPackage;
import de.ruegnerlukas.simpleapplication.common.plugins.PluginManager;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import javafx.application.Platform;

/*
digraph G {
    "EVENT_INIT"
    -> "load SYSTEM_ID_ROOT"
    -> "EVENT_SYSTEM_LOADED"
    -> "load base ui module"
    -> "EVENT_CHANGE_SCENE"
    -> "EVENT_START"
    -> "load SYSTEM_ID_JFXROOT"
    -> "EVENT_SYSTEM_LOADED (2)"
    -> "...running..."
    -> "unload SYSTEM_ID_JFXROOT"
    -> "EVENT_SYSTEM_UNLOADED"
    -> "unload SYSTEM_ID_ROOT"
    -> "EVENT_SYSTEM_UNLOADED (2)"
    -> "EVENT_STOP"

    "EVENT_SYSTEM_LOADED"
    -> "EVENT_PLUGIN_LOADED"
    -> "EVENT_PLUGIN_LOADED"
    -> "load base ui module"

    "EVENT_SYSTEM_LOADED (2)"
    -> "EVENT_PLUGIN_LOADED (2)"
    -> "EVENT_PLUGIN_LOADED (2)"
    -> "...running..."

    "EVENT_SYSTEM_UNLOADED"
    -> "EVENT_PLUGIN_UNLOADED"
    -> "EVENT_PLUGIN_UNLOADED"
    -> "unload SYSTEM_ID_ROOT"

    "EVENT_SYSTEM_UNLOADED (2)"
    -> "EVENT_PLUGIN_UNLOADED (2)"
    -> "EVENT_PLUGIN_UNLOADED (2)"
    -> "EVENT_STOP"
}
 */






/**
 * Application Lifecycle:
 * <p>
 * SimpleApplication.startApplication()
 * - event EVENT_INITIALIZE
 * - load SYSTEM_ID_ROOT
 * - event EVENT_SYSTEM_LOADED
 * - loading plugins (if possible/necessary)
 * -> event EVENT_PLUGIN_LOADED
 * - load base module
 * - event EVENT_CHANGE_SCENE
 * - event EVENT_START
 * - load SYSTEM_ID_JFXROOT
 * - event EVENT_SYSTEM_LOADED
 * - loading plugins (if possible/necessary)
 * -> event EVENT_PLUGIN_LOADED
 * <p>
 * SimpleApplication.stopApplication() or close-command from javafx
 * - unload SYSTEM_ID_JFXROOT
 * - event EVENT_SYSTEM_UNLOADED
 * - unloading plugins (if possible/necessary)
 * -> event EVENT_PLUGIN_UNLOADED
 * - unload SYSTEM_ID_ROOT
 * - event EVENT_SYSTEM_UNLOADED
 * - unloading plugins (if possible/necessary)
 * -> event EVENT_PLUGIN_UNLOADED
 * - event EVENT_STOP
 */
public final class SimpleApplication {


	/**
	 * Utility class
	 */
	private SimpleApplication() {
	}




	/**
	 * Whether the application was started.
	 */
	private static boolean applicationStated = false;


	/**
	 * The manager for plugins.
	 */
	private static PluginManager pluginManager = new PluginManager();



	/**
	 * The global application event bus.
	 */
	private static EventBus applicationEventBus = new EventBusImpl();




	/**
	 * @return the global application event bus.
	 */
	public static EventBus getEvents() {
		return applicationEventBus;
	}




	/**
	 * @return the plugin manager
	 */
	public static PluginManager getPluginManager() {
		return SimpleApplication.pluginManager;
	}










	/**
	 * Starts the application.
	 */
	public static void startApplication() {
		Validations.STATE.isFalse(SimpleApplication.applicationStated).exception("The application was already started.");
		SimpleApplication.applicationStated = true;
		// init internal systems here
		getEvents().publish(ApplicationConstants.EVENT_INITIALIZE, new EmptyEventPackage());
		getPluginManager().load(ApplicationConstants.SYSTEM_ID_ROOT);
		JFXApplication.start();
	}




	/**
	 * Stops and closes the application.
	 */
	public static void stopApplication() {
		Validations.STATE.isTrue(SimpleApplication.applicationStated).exception("The application is not running.");
		SimpleApplication.applicationStated = false;
		Platform.exit();
	}




	/**
	 * Called when the application is stopped. Usually called from javafx
	 */
	static void onStopApplication() {
		// clean up internal systems here
		getPluginManager().unload(ApplicationConstants.SYSTEM_ID_JFXROOT);
		getPluginManager().unload(ApplicationConstants.SYSTEM_ID_ROOT);
		getEvents().publish(ApplicationConstants.EVENT_STOP, new EmptyEventPackage());
		SimpleApplication.applicationStated = false;
	}


}
