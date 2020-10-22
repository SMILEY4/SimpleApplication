package de.ruegnerlukas.simpleapplication.simpleui.core.events;

import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;

import java.util.HashSet;
import java.util.Set;

public class SuiEmittingEventListener<T> implements SuiEventListener<T> {


	private final Tags tags;




	public SuiEmittingEventListener(final Tags tags) {
		this.tags = tags;
	}




	@Override
	public void onEvent(final T event) {
		SuiRegistry.get().getEventBus().publish(getEventTags(event, tags), event);
	}




	private Tags getEventTags(final T event, final Tags listenerTags) {
		Set<String> eventTags = new HashSet<>(listenerTags.getTags());
		eventTags.add(event.getClass().getSimpleName());
		return Tags.from(eventTags);
	}


}
