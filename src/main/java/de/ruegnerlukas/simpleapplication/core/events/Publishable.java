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
	 * Empty constructor. The {@link Channel} of this {@link Publishable} will be its type.
	 */
	public Publishable() {
		this((Channel) null);
	}




	/**
	 * @param typeChannel the type of the {@link Channel} of this publishable.
	 */
	public Publishable(final Class<?> typeChannel) {
		this(Channel.type(typeChannel));
	}




	/**
	 * @param nameChannel the name of the {@link Channel} of this publishable.
	 */
	public Publishable(final String nameChannel) {
		this(Channel.name(nameChannel));
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
