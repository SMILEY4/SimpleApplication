package de.ruegnerlukas.simpleapplication.common.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ListenableEventSourceGroup {


	/**
	 * The map of {@link ListenableEventSource}s. Key is the name of the source in this group.
	 */
	private final Map<String, ListenableEventSource> eventSources = new HashMap<>();




	/**
	 * Constructor for a group with no initial {@link ListenableEventSource}s.
	 */
	public ListenableEventSourceGroup() {
	}




	/**
	 * Constructor for a group with the given {@link ListenableEventSource}s.
	 */
	public ListenableEventSourceGroup(final Map<String, ListenableEventSource> eventSources) {
		this.eventSources.putAll(eventSources);
	}




	/**
	 * Adds the given event source with the given name to this group.
	 *
	 * @param name        the name of the source
	 * @param eventSource the {@link ListenableEventSource}
	 */
	public void addEventSource(final String name, final ListenableEventSource eventSource) {
		this.eventSources.put(name, eventSource);
	}




	/**
	 * @param name the name of the event source
	 * @return the {@link ListenableEventSource} with the given name or null.
	 */
	public ListenableEventSource getEventSource(final String name) {
		return this.eventSources.get(name);
	}




	/**
	 * @param name the name of the event source
	 * @return an optional with a {@link ListenableEventSource} with the given name.
	 */
	public Optional<ListenableEventSource> getEventSourceOptional(final String name) {
		return Optional.ofNullable(getEventSource(name));
	}

}
