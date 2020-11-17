package de.ruegnerlukas.simpleapplication.core.plugins;

import lombok.Getter;

/**
 * The event when a plugin was unloaded.
 */
@Getter
public final class EventPluginUnloaded {


	/**
	 * The id of the unloaded plugin.
	 */
	private final String pluginId;




	/**
	 * @param pluginId the id of the unloaded plugin
	 */
	public EventPluginUnloaded(final String pluginId) {
		this.pluginId = pluginId;
	}

}
