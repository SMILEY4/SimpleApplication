package de.ruegnerlukas.simpleapplication.core.plugins;

import lombok.Getter;

/**
 * The event when a new plugin was registered.
 */
@Getter
public final class EventPluginRegistered {


	/**
	 * The id of the de-registered plugin.
	 */
	private final String pluginId;




	/**
	 * @param pluginId the id of the registered plugin
	 */
	public EventPluginRegistered(final String pluginId) {
		this.pluginId = pluginId;
	}

}
