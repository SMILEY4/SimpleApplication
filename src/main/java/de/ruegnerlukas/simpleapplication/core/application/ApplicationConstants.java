package de.ruegnerlukas.simpleapplication.core.application;

public final class ApplicationConstants {


	/**
	 * Hidden constructor.
	 */
	private ApplicationConstants() {
	}





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

}
