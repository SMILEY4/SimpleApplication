package de.ruegnerlukas.simpleapplication.common.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TriggerableEventSourceGroup {


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




	/**
	 * Adds the given event source with the given name to this group.
	 *
	 * @param name        the name of the source
	 * @param eventSource the {@link TriggerableEventSource}
	 */
	public void addEventSource(final String name, final TriggerableEventSource eventSource) {
		this.eventSources.put(name, eventSource);
	}




	/**
	 * @param name the name of the event source
	 * @return the {@link ListenableEventSource} with the given name or null.
	 */
	public TriggerableEventSource getEventSource(final String name) {
		return this.eventSources.get(name);
	}




	/**
	 * @param name the name of the event source
	 * @return an optional with a {@link TriggerableEventSource} with the given name.
	 */
	public Optional<TriggerableEventSource> getEventSourceOptional(final String name) {
		return Optional.ofNullable(getEventSource(name));
	}

}
