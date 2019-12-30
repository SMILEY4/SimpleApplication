package de.ruegnerlukas.simpleapplication.common.events;

import de.ruegnerlukas.simpleapplication.common.events.ListenableEvent;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ListenableEventGroup {


	/**
	 * The list of {@link ListenableEvent}s of this group.
	 */
	private final Map<String, ListenableEvent> events = new HashMap<>();




	/**
	 * Constructor for an event group with no initial events.
	 */
	public ListenableEventGroup() {
	}




	/**
	 * @param events events for this group
	 */
	public ListenableEventGroup(final Map<String, ListenableEvent> events) {
		Validations.INPUT.notNull(events, "The events must not be null.");
		this.events.putAll(events);
	}




	/**
	 * Adds the given {@link ListenableEvent} with the given name to this group.
	 *
	 * @param name  the name of this group. Must be unique for this group or else the last event will be overwritten.
	 * @param event the event itself
	 */
	public void addEvent(final String name, final ListenableEvent event) {
		Validations.INPUT.notBlank(name, "The name must not be null or blank.");
		Validations.INPUT.notNull(event, "The event must not be null.");
		events.put(name, event);
	}




	/**
	 * Finds the event with the given name.
	 *
	 * @param name the name of the event in this group.
	 * @param <T>  the generic type.
	 * @return the event with the given name or null
	 */
	public <T> ListenableEvent<T> getEvent(final String name) {
		return (ListenableEvent<T>) events.get(name);
	}




	/**
	 * @return an unmodifiable map with all events of this group
	 */
	public Map<String, ListenableEvent> getEvents() {
		return Collections.unmodifiableMap(events);
	}

}
