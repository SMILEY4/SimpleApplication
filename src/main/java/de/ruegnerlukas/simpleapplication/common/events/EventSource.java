package de.ruegnerlukas.simpleapplication.common.events;

public interface EventSource<T extends Event> extends ListenableEventSource, TriggerableEventSource<T> {


}
