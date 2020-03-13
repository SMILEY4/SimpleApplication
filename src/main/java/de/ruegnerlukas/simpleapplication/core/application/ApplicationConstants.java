package de.ruegnerlukas.simpleapplication.core.application;

public final class ApplicationConstants {


	/**
	 * The channel for the event triggered when the application is fully started.
	 */
	public static final String EVENT_APPLICATION_STARTED = "core.application_started";

	/**
	 * The channel for the event triggered when the application is about to stop and close.
	 */
	public static final String EVENT_APPLICATION_STOPPING = "core.application_stopping";

	/**
	 * The channel for the event triggered when javafx and the presentation system was initialized.
	 */
	public static final String EVENT_PRESENTATION_INITIALIZED = "core.presentation_initialized";

	/**
	 * The id of the channel for events when showing a new view in existing windows.
	 */
	public static final String EVENT_SHOW_VIEW = "core.view.showview";

	/**
	 * The id of the channel for events when opening a new popup window.
	 */
	public static final String EVENT_OPEN_POPUP = "core.view.openpopup";

	/**
	 * The id of the channel for events when closing a popup window.
	 */
	public static final String EVENT_CLOSE_POPUP = "core.view.closepopup";


	/**
	 * The id of the channel for events when a new plugin was registered.
	 */
	public static final String EVENT_PLUGIN_REGISTERED = "core.plugin.registered";

	/**
	 * The id of the channel for events when a new plugin was deregistered.
	 */
	public static final String EVENT_PLUGIN_DEREGISTERED = "core.plugin.deregistered";

	/**
	 * The id of the channel for events when a plugin was loaded.
	 */
	public static final String EVENT_PLUGIN_LOADED = "core.plugin.plugin_loaded";

	/**
	 * The id of the channel for events when a component (in the plugin-service) was loaded.
	 */
	public static final String EVENT_COMPONENT_LOADED = "core.plugin.component_loaded";

	/**
	 * The id of the channel for events when a plugin was unloaded.
	 */
	public static final String EVENT_PLUGIN_UNLOADED = "core.plugin.plugin_unloaded";

	/**
	 * The id of the channel for events when a component (in the plugin-service) was unloaded.
	 */
	public static final String EVENT_COMPONENT_UNLOADED = "core.plugin.component_unloaded";

	/**
	 * The id of the view-system-component. While this component is loaded, the view service is initialized and running.
	 */
	public static final String COMPONENT_VIEW_SYSTEM = "core.component.viewsystem";


	/**
	 * The name for the provider of the primary javafx stage.
	 */
	public static final String PROVIDED_PRIMARY_STAGE = "code.provider.primaryStage";




	/**
	 * Hidden constructor.
	 */
	private ApplicationConstants() {
	}

}
