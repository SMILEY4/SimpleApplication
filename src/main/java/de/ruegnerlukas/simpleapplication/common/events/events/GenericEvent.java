package de.ruegnerlukas.simpleapplication.common.events.events;

import de.ruegnerlukas.simpleapplication.common.events.Event;
import lombok.Getter;

/**
 * An implementation of an event holding a generic object.
 *
 * @param <T> the type of the data
 */
public class GenericEvent<T> extends Event {


	/**
	 * The data of this event.
	 */
	@Getter
	private final T data;




	/**
	 * @param data the data
	 */
	public GenericEvent(final T data) {
		this.data = data;
	}


}
