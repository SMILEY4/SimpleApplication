package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.Getter;

@Getter
public class EventPluginDeregistered extends Publishable {


	/**
	 * The id of the de-registered plugin.
	 */
	private final String pluginId;




	/**
	 * @param pluginId the id of the de-registered plugin
	 */
	public EventPluginDeregistered(final String pluginId) {
		super(ApplicationConstants.EVENT_PLUGIN_DEREGISTERED);
		this.pluginId = pluginId;
	}

}
