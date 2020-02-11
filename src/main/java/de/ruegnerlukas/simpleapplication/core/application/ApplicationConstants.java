package de.ruegnerlukas.simpleapplication.core.application;

public final class ApplicationConstants {


	/**
	 * Hidden constructor.
	 */
	private ApplicationConstants() {
	}




	/**
	 * The channel for the event triggered when javafx and the presentation system was initialized.
	 */
	public static final String EVENT_PRESENTATION_INITIALIZED = "app.presentation_initialized";

	/**
	 * The channel for the event triggered when the application is fully started.
	 */
	public static final String EVENT_APPLICATION_STARTED = "app.application_started";

	/**
	 * The channel for the event triggered when the application is about to stop and close.
	 */
	public static final String EVENT_APPLICATION_STOPPING = "app.application_stopping";

	/**
	 * The channel for the event triggered before showing a new view in the primary window.
	 * The event-data is an array with the id of the current view and the id of the next view.
	 */
	public static final String EVENT_SWITCH_VIEW_PRE = "app.show_view_primary.pre";

	/**
	 * The channel for the event triggered after switching to a new view in the primary window.
	 * The event-data is an array with the id of the last view and the id of the current view.
	 */
	public static final String EVENT_SWITCH_VIEW_POST = "app.show_view_primary.post";

}
