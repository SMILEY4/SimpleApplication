package de.ruegnerlukas.simpleapplication.core.presentation.uimodule;

import de.ruegnerlukas.simpleapplication.common.events.ListenableEventSource;
import de.ruegnerlukas.simpleapplication.common.events.TriggerableEventSource;

import java.util.Map;

public interface ModuleView {


	/**
	 * Initializes this view.
	 */
	void initializeView();

	/**
	 * @return a map of all events that this view exposes to the controller of the same module.
	 */
	Map<String, ListenableEventSource> getEventEndpoints();

	/**
	 * @return a map of all functions that this view exposes to the controller of the same module.
	 */
	Map<String, TriggerableEventSource> getFunctionEndpoints();


}
