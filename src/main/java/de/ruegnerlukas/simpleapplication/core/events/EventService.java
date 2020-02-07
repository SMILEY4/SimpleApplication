package de.ruegnerlukas.simpleapplication.core.events;

import de.ruegnerlukas.simpleapplication.common.events.EventSource;
import de.ruegnerlukas.simpleapplication.common.events.ListenableEventSource;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class EventService {


	/**
	 * The map of all registered event sources.
	 */
	private final Map<String, ListenableEventSource<?>> eventSources = new HashMap<>();




	/**
	 * Adds the given event source with the given name. The id must be unique.
	 *
	 * @param id          the unique id of the event source
	 * @param eventSource the event source to add
	 */
	public void registerEvent(final String id, final ListenableEventSource<?> eventSource) {
		Validations.INPUT.notBlank(id).exception("The event id can not be null or empty.");
		Validations.INPUT.containsNotKey(eventSources, id).exception("The event id is already in use.");
		Validations.INPUT.notNull(eventSource).exception("The event source can not be null.");
		eventSources.put(id, eventSource);
	}




	/**
	 * @param id  the id of the event
	 * @param <T> the generic type
	 * @return the event source with the given id or a dummy event source.
	 */
	public <T> ListenableEventSource<T> getEvent(final String id) {
		ListenableEventSource<T> eventSource = (ListenableEventSource<T>) eventSources.get(id);
		if (eventSource == null) {
			log.warn("Could not find event with id {}. Returning dummy instead.", id);
			eventSource = new EventSource<T>();
		}
		return eventSource;
	}

}
