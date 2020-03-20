package de.ruegnerlukas.simpleapplication.core.events;

import de.ruegnerlukas.simpleapplication.common.events.Channel;

/**
 * An implementation of a publishable with no additional data.
 */
public final class EmptyPublishable extends Publishable {


	/**
	 * Empty constructor. The {@link Channel} of this empty {@link Publishable} will be its type.
	 */
	public EmptyPublishable() {
		super((Channel) null);
	}




	/**
	 * @param typeChannel the type of the {@link Channel} of this empty publishable.
	 */
	public EmptyPublishable(final Class<?> typeChannel) {
		super(Channel.type(typeChannel));
	}




	/**
	 * @param nameChannel the name of the {@link Channel} of this empty publishable.
	 */
	public EmptyPublishable(final String nameChannel) {
		super(Channel.name(nameChannel));
	}




	/**
	 * @param channel the channel in which to publish this empty  publishable.
	 */
	public EmptyPublishable(final Channel channel) {
		super(channel);
	}


}
