package de.ruegnerlukas.simpleapplication.core.events;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublishableMeta {


	/**
	 * The {@link Publishable} this metadata belongs to.
	 */
	private final Publishable publishable;

	/**
	 * Whether the {@link Publishable} was published (by the event system).
	 */
	private boolean published;

	/**
	 * The channel in which the {@link Publishable} is published in.
	 */
	private Channel channel;

	/**
	 * The timestamp when the {@link Publishable} was published
	 */
	private long timestamp;

	/**
	 * The number of listeners subscribed / the number of potential receivers.
	 */
	private long numListeners;

	/**
	 * The number of listeners which received the {@link Publishable} up until now.
	 * The complete count is only available after the last listener received the publishable.
	 */
	private long numReceivers;

	/**
	 * Whether the {@link Publishable} was cancelled.
	 */
	private boolean cancelled;



	/**
	 * @param publishable the {@link Publishable} this metadata refers to.
	 */
	public PublishableMeta(final Publishable publishable) {
		this.publishable = publishable;
	}

}
