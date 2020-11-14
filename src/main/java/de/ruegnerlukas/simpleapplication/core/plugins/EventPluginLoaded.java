package de.ruegnerlukas.simpleapplication.core.plugins;

import lombok.Getter;

/**
 * The event when a plugin was loaded.
 */
@Getter
public final class EventPluginLoaded {


	/**
	 * The id of the loaded plugin.
	 */
	private final String pluginId;




	/**
	 * @param pluginId the id of the loaded plugin
	 */
	public EventPluginLoaded(final String pluginId) {
		this.pluginId = pluginId;
	}

}
