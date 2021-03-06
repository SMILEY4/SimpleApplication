package de.ruegnerlukas.simpleapplication.common.events;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TriggerableEventSourceGroup {


	/**
	 * The map of {@link TriggerableEventSource}s. Key is the channel of the source in this group.
	 */
	private final Map<Channel, TriggerableEventSource<?>> eventSources = new HashMap<>();




	/**
	 * Default constructor.
	 */
	public TriggerableEventSourceGroup() {
	}




	/**
	 * @param eventSources a map of event sources
	 */
	public TriggerableEventSourceGroup(final Map<Channel, TriggerableEventSource<?>> eventSources) {
		eventSources.forEach(this::add);
	}




	/**
	 * Add the event source with the given name
	 *
	 * @param channel     the channel of the event source
	 * @param eventSource the event source
	 */
	public void add(final Channel channel, final TriggerableEventSource<?> eventSource) {
		Validations.INPUT.notNull(channel).exception("The channel must not be null.");
		Validations.INPUT.notNull(eventSource).exception("The event source must not be null.");
		this.eventSources.put(channel, eventSource);
	}




	/**
	 * Finds the event source with the given name.
	 *
	 * @param channel the channel of the requested event source
	 * @param <T>     the generic type of the event
	 * @return the event source or null
	 */
	public <T> Optional<TriggerableEventSource<T>> findOptional(final Channel channel) {
		return Optional.ofNullable(find(channel));
	}




	/**
	 * Finds the event source with the given name.
	 *
	 * @param channel the channel of the requested event source
	 * @param <T>     the generic type of the event
	 * @return the event source or null
	 */
	public <T> TriggerableEventSource<T> find(final Channel channel) {
		return (TriggerableEventSource<T>) eventSources.get(channel);
	}




	/**
	 * @return a list with the channel of all event sources of this group.
	 */
	public List<Channel> getChannels() {
		return new ArrayList<>(eventSources.keySet());
	}

}
