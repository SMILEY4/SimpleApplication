package de.ruegnerlukas.simpleapplication.common.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TriggerableEventSourceGroup implements EventSourceGroup<TriggerableEventSource> {


	/**
	 * The map of {@link TriggerableEventSource}s. Key is the name of the source in this group.
	 */
	private final Map<String, TriggerableEventSource> eventSources = new HashMap<>();




	/**
	 * Constructor for a group with no initial {@link TriggerableEventSource}s.
	 */
	public TriggerableEventSourceGroup() {
	}




	/**
	 * Constructor for a group with the given {@link TriggerableEventSource}s.
	 */
	public TriggerableEventSourceGroup(final Map<String, TriggerableEventSource> eventSources) {
		this.eventSources.putAll(eventSources);
	}




	@Override
	public void addEventSource(final String name, final TriggerableEventSource eventSource) {
		this.eventSources.put(name, eventSource);
	}




	@Override
	public TriggerableEventSource getEventSource(final String name) {
		return this.eventSources.get(name);
	}




	@Override
	public Optional<TriggerableEventSource> getEventSourceOptional(final String name) {
		return Optional.ofNullable(getEventSource(name));
	}

}
