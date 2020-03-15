package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.Getter;

@Getter
public class EventPluginLoaded extends Publishable {


	/**
	 * The id of the loaded plugin.
	 */
	private final String pluginId;




	/**
	 * @param pluginId the id of the loaded plugin
	 */
	public EventPluginLoaded(final String pluginId) {
		super(ApplicationConstants.EVENT_PLUGIN_LOADED);
		this.pluginId = pluginId;
	}

}
