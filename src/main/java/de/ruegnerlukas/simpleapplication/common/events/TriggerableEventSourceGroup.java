package de.ruegnerlukas.simpleapplication.common.events;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TriggerableEventSourceGroup {


	/**
	 * The map of {@link TriggerableEventSource}s. Key is the name of the source in this group.
	 */
	private final Map<String, TriggerableEventSource<?>> eventSources = new HashMap<>();




	/**
	 * Default constructor.
	 */
	public TriggerableEventSourceGroup() {
	}




	/**
	 * @param eventSources a map of event sources
	 */
	public TriggerableEventSourceGroup(final Map<String, TriggerableEventSource<?>> eventSources) {
		eventSources.forEach(this::add);
	}




	/**
	 * Add the event source with the given name
	 *
	 * @param name        the name of the event source
	 * @param eventSource the event source
	 */
	public void add(final String name, final TriggerableEventSource<?> eventSource) {
		Validations.INPUT.notBlank(name).exception("The name must not be null.");
		Validations.INPUT.notNull(eventSource).exception("The event source must not be null.");
		this.eventSources.put(name, eventSource);
	}




	/**
	 * Finds the event source with the given name.
	 *
	 * @param name the name of the requested event source
	 * @param <T>  the generic type
	 * @return the event source or null
	 */
	public <T> TriggerableEventSource<T> find(final String name) {
		return (TriggerableEventSource<T>) eventSources.get(name);
	}




	/**
	 * @return a list with ids of all event sources of this group.
	 */
	public List<String> getIds() {
		return new ArrayList<>(eventSources.keySet());
	}

}
