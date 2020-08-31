package de.ruegnerlukas.simpleapplication.simpleui.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SuiEvent<T> {


	/**
	 * A string identifying the type of this event
	 */
	@Getter
	private final String eventType;

	/**
	 * The data attached to this event
	 */
	@Getter
	private final T data;




	/**
	 * @param eventType the event type to check for
	 * @return whether this event is of the given type
	 */
	public boolean isEventType(final String eventType) {
		return this.eventType.equalsIgnoreCase(eventType);
	}


}
