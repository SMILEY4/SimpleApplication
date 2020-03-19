package de.ruegnerlukas.simpleapplication.common.events;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListenableEventSourceGroup {


	/**
	 * The map of {@link ListenableEventSource}s. Key is the channel of the source in this group.
	 */
	private final Map<Channel, ListenableEventSource<?>> eventSources = new HashMap<>();




	/**
	 * Default constructor.
	 */
	public ListenableEventSourceGroup() {
	}




	/**
	 * @param eventSources a map of event sources
	 */
	public ListenableEventSourceGroup(final Map<Channel, ListenableEventSource<?>> eventSources) {
		eventSources.forEach(this::add);
	}




	/**
	 * Add the event source with the given channel
	 *
	 * @param channel     the channel of the event source
	 * @param eventSource the event source
	 */
	public void add(final Channel channel, final ListenableEventSource<?> eventSource) {
		Validations.INPUT.notNull(channel).exception("The channel must not be null.");
		Validations.INPUT.notNull(eventSource).exception("The event source must not be null.");
		this.eventSources.put(channel, eventSource);
	}




	/**
	 * Finds the event source with the given channel.
	 *
	 * @param channel the channel of the requested event source
	 * @return the event source or null
	 */
	public <T> ListenableEventSource<T> find(final Channel channel) {
		return (ListenableEventSource<T>) eventSources.get(channel);
	}




	/**
	 * @return a list with the channels of all event sources of this group.
	 */
	public List<Channel> getChannels() {
		return new ArrayList<>(eventSources.keySet());
	}

}
