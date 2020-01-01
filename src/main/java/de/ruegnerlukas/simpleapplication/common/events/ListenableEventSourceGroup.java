package de.ruegnerlukas.simpleapplication.common.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ListenableEventSourceGroup implements EventSourceGroup<ListenableEventSource> {


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




	@Override
	public void addEventSource(final String name, final ListenableEventSource eventSource) {
		this.eventSources.put(name, eventSource);
	}




	@Override
	public ListenableEventSource getEventSource(final String name) {
		return this.eventSources.get(name);
	}




	@Override
	public Optional<ListenableEventSource> getEventSourceOptional(final String name) {
		return Optional.ofNullable(getEventSource(name));
	}

}
