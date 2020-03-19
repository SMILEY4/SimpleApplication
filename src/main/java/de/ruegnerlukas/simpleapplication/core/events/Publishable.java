package de.ruegnerlukas.simpleapplication.core.events;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import lombok.Getter;

public abstract class Publishable {


	/**
	 * Metadata about this publishable.
	 */
	@Getter
	private final PublishableMeta metadata;




	/**
	 * Empty constructor. The channel of this {@link Publishable} will be null.
	 */
	public Publishable() {
		this(null);
	}




	/**
	 * @param channel the channel in which to publish instances.
	 */
	public Publishable(final Channel channel) {
		metadata = new PublishableMeta(this);
		metadata.setChannel(channel);
	}




	/**
	 * Sets the channel of this publishable.
	 *
	 * @param channel the (event-)channel
	 */
	public void setChannel(final Channel channel) {
		metadata.setChannel(channel);
	}




	/**
	 * @return the channel in which this publishable was published in.
	 */
	public Channel getChannel() {
		return metadata.getChannel();
	}




	/**
	 * Stops the propagation of this publishable.
	 */
	public void cancel() {
		metadata.setCancelled(true);
	}




	/**
	 * @return whether this publishable was cancelled.
	 */
	public boolean isCancelled() {
		return metadata.isCancelled();
	}

}
