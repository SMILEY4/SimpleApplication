package de.ruegnerlukas.simpleapplication.common.events;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;

import java.util.HashMap;
import java.util.Map;

public class ListenableEventSourceGroup {


	/**
	 * The map of {@link ListenableEventSource}s. Key is the name of the source in this group.
	 */
	private final Map<String, ListenableEventSource<?>> eventSources = new HashMap<>();




	/**
	 * Default constructor.
	 */
	public ListenableEventSourceGroup() {
	}




	/**
	 * @param eventSources a map of event sources
	 */
	public ListenableEventSourceGroup(final Map<String, ListenableEventSource<?>> eventSources) {
		eventSources.forEach(this::add);
	}




	/**
	 * Add the event source with the given name
	 *
	 * @param name        the name of the event source
	 * @param eventSource the event source
	 */
	public void add(final String name, final ListenableEventSource<?> eventSource) {
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
	public <T> ListenableEventSource<T> find(final String name) {
		return (ListenableEventSource<T>) eventSources.get(name);
	}

}
