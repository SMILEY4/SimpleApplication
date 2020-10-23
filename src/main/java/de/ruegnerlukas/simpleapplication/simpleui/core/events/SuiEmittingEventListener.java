package de.ruegnerlukas.simpleapplication.simpleui.core.events;

import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;

import java.util.HashSet;
import java.util.Set;

public class SuiEmittingEventListener<T> implements SuiEventListener<T> {


	/**
	 * The prefix added to the event-type-tag.
	 */
	public static final String TAG_PREFIX_EVENT_TYPE = "type.";

	/**
	 * The additional tags to emit with the events
	 */
	private final Tags tags;




	/**
	 * @param tags tags to add to the emitted events
	 */
	public SuiEmittingEventListener(final Tags tags) {
		this.tags = tags;
	}




	@Override
	public void onEvent(final T event) {
		SuiRegistry.get().getEventBus().publish(getEventTags(event, tags), event);
	}




	/**
	 * Get the complete list of tags for the given event
	 *
	 * @param event        the event
	 * @param listenerTags the additional tags of this listener
	 * @return the complete list of tags for the given event
	 */
	private Tags getEventTags(final T event, final Tags listenerTags) {
		Set<String> eventTags = new HashSet<>(listenerTags.getTags());
		eventTags.add(TAG_PREFIX_EVENT_TYPE + event.getClass().getSimpleName());
		return Tags.from(eventTags);
	}


}
