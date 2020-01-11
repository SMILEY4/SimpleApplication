package de.ruegnerlukas.simpleapplication.common.events;

import lombok.Getter;
import lombok.Setter;

public class FixedEventSource<T> extends EventSource<T> {


	/**
	 * The event to trigger.
	 */
	@Getter
	@Setter
	private T event;




	/**
	 * @param event the event to trigger
	 */
	public FixedEventSource(final T event) {
		setEvent(event);
	}




	/**
	 * Triggers the event of this fixed event source.
	 */
	public void trigger() {
		trigger(getEvent());
	}

}
