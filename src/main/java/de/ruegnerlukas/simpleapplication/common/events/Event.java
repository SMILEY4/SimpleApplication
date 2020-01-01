package de.ruegnerlukas.simpleapplication.common.events;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * The base class for an event.
 */
public class Event {


	/**
	 * The names of the channels this event was published in (If it was sent over an {@link EventBus}).
	 */
	@Getter
	@Setter (AccessLevel.PACKAGE)
	private String[] channels;

	/**
	 * The timestamp when this event was published.
	 */
	@Getter
	@Setter (AccessLevel.PACKAGE)
	private long timestamp;

	/**
	 * The number of expected receivers.
	 */
	@Getter
	@Setter (AccessLevel.PACKAGE)
	private int receivers;

}
