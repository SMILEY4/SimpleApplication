package de.ruegnerlukas.simpleapplication.core.plugins;

import lombok.Getter;

/**
 * The event when a new plugin was deregistered.
 */
@Getter
public final class EventPluginDeregistered {


	/**
	 * The id of the de-registered plugin.
	 */
	private final String pluginId;




	/**
	 * @param pluginId the id of the de-registered plugin
	 */
	public EventPluginDeregistered(final String pluginId) {
		this.pluginId = pluginId;
	}

}
