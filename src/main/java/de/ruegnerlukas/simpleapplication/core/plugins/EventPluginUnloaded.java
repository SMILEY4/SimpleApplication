package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.Getter;

@Getter
public class EventPluginUnloaded extends Publishable {


	/**
	 * The id of the unloaded plugin.
	 */
	private final String pluginId;




	/**
	 * @param pluginId the id of the unloaded plugin
	 */
	public EventPluginUnloaded(final String pluginId) {
		super(ApplicationConstants.EVENT_PLUGIN_UNLOADED);
		this.pluginId = pluginId;
	}

}
