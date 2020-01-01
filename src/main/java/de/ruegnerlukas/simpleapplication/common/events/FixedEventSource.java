package de.ruegnerlukas.simpleapplication.common.events;

public class FixedEventSource extends GenericFixedEventSource<Event> {


	/**
	 * @param event the event triggered by this event source
	 */
	public FixedEventSource(final Event event) {
		super(event);
	}

}
