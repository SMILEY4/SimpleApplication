package de.ruegnerlukas.simpleapplication.common.events2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class EventPackage<T> {


	/**
	 * The names of the channels this event was published in.
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


	/**
	 * The event/data of this package.
	 */
	@Getter
	private final T event;




	/**
	 * @param event the event/data of this package.
	 */
	public EventPackage(final T event) {
		this.event = event;
	}

}
