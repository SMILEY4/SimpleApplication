package de.ruegnerlukas.simpleapplication.common.events;

import java.util.Optional;

public interface EventSourceGroup<T> {


	/**
	 * Adds the given event source with the given name to this group.
	 *
	 * @param name        the name of the source
	 * @param eventSource the event source
	 */
	void addEventSource(String name, T eventSource);

	/**
	 * @param name the name of the event source
	 * @return the event source with the given name or null.
	 */
	T getEventSource(String name);

	/**
	 * @param name the name of the event source
	 * @return an optional with an event source with the given name.
	 */
	Optional<T> getEventSourceOptional(String name);

}
