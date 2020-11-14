package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.tags.Tags;

public final class ApplicationConstants {


	/**
	 * The name for the provider of the primary javafx stage.
	 */
	public static final String PROVIDED_PRIMARY_STAGE = "core.provider.primaryStage";

	/**
	 * The identifying type of the application started event
	 */
	public static final String EVENT_APPLICATION_STARTED_TYPE = "event.type.application.started";

	/**
	 * The tags of the application started event
	 */
	public static final Tags EVENT_APPLICATION_STARTED_TAGS = Tags.from(EVENT_APPLICATION_STARTED_TYPE);


	/**
	 * The identifying type of the application stopping event
	 */
	public static final String EVENT_APPLICATION_STOPPING_TYPE = "event.type.application.stopping";

	/**
	 * The tags of the application stopping event
	 */
	public static final Tags EVENT_APPLICATION_STOPPING_TAGS = Tags.from(EVENT_APPLICATION_STOPPING_TYPE);


	/**
	 * The identifying type of the component loaded event
	 */
	public static final String EVENT_COMPONENT_LOADED_TYPE = "event.type.plugins.component.loaded";

	/**
	 * The tags of the component loaded event
	 */
	public static final Tags EVENT_COMPONENT_LOADED_TAGS = Tags.from(EVENT_COMPONENT_LOADED_TYPE);


	/**
	 * The identifying type of the component unloaded event
	 */
	public static final String EVENT_COMPONENT_UNLOADED_TYPE = "event.type.plugins.component.unloaded";

	/**
	 * The tags of the component unloaded event
	 */
	public static final Tags EVENT_COMPONENT_UNLOADED_TAGS = Tags.from(EVENT_COMPONENT_UNLOADED_TYPE);


	/**
	 * The identifying type of the plugin registered event
	 */
	public static final String EVENT_PLUGIN_REGISTERED_TYPE = "event.type.plugins.plugin.registered";

	/**
	 * The tags of the plugin registered event
	 */
	public static final Tags EVENT_PLUGIN_REGISTERED_TAGS = Tags.from(EVENT_PLUGIN_REGISTERED_TYPE);


	/**
	 * The identifying type of the plugin de-registered event
	 */
	public static final String EVENT_PLUGIN_DEREGISTERED_TYPE = "event.type.plugins.plugin.deregistered";

	/**
	 * The tags of the plugin de-registered event
	 */
	public static final Tags EVENT_PLUGIN_DEREGISTERED_TAGS = Tags.from(EVENT_PLUGIN_DEREGISTERED_TYPE);


	/**
	 * The identifying type of the plugin loaded event
	 */
	public static final String EVENT_PLUGIN_LOADED_TYPE = "event.type.plugins.plugin.loaded";

	/**
	 * The tags of the plugin loaded event
	 */
	public static final Tags EVENT_PLUGIN_LOADED_TAGS = Tags.from(EVENT_PLUGIN_LOADED_TYPE);


	/**
	 * The identifying type of the plugin unloaded event
	 */
	public static final String EVENT_PLUGIN_UNLOADED_TYPE = "event.type.plugins.plugin.unloaded";

	/**
	 * The tags of the plugin unloaded event
	 */
	public static final Tags EVENT_PLUGIN_UNLOADED_TAGS = Tags.from(EVENT_PLUGIN_UNLOADED_TYPE);




	/**
	 * Hidden constructor.
	 */
	private ApplicationConstants() {
	}

}
