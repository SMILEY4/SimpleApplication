package de.ruegnerlukas.simpleapplication.core.presentation.uimodule;

import de.ruegnerlukas.simpleapplication.common.events.ListenableEvent;
import de.ruegnerlukas.simpleapplication.common.events.TriggerableEvent;

import java.util.Map;

public interface ModuleView {


	/**
	 * Initializes this view.
	 */
	void initializeView();

	/**
	 * @return a map of all events that this view exposes to the controller of the same module.
	 */
	Map<String, ListenableEvent> getEventEndpoints();

	/**
	 * @return a map of all functions that this view exposes to the controller of the same module.
	 */
	Map<String, TriggerableEvent> getFunctionEndpoints();

//	default <T> TriggerableEvent<T> getFunctionEndpoint(String name) {
//		return (TriggerableEvent<T>) getFunctionEndpoints().get(name);
//	}
//
//	default <T> ListenableEvent<T> getEventEndpoint(String name) {
//		return (ListenableEvent<T>) getEventEndpoints().get(name);
//	}

}
