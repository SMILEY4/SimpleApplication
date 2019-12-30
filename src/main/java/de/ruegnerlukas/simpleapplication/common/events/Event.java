package de.ruegnerlukas.simpleapplication.common.events;

/**
 * The interface for an event
 * @param <T> the type of the event
 */
public interface Event<T> extends ListenableEvent<T>, TriggerableEvent<T> {

}
