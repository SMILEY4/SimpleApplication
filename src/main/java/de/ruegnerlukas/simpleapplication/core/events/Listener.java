package de.ruegnerlukas.simpleapplication.core.events;

import de.ruegnerlukas.simpleapplication.common.events.Channel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;

@Target (ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
public @interface Listener {


	/**
	 * The name of the channel when it is not set.
	 */
	String UNSET_NAME = "";

	/**
	 * The type of the channel when it is not set.
	 */
	Class<?> UNSET_TYPE = Void.class;

	/**
	 * @return the name of the channel (if used).
	 */
	String name() default "";

	/**
	 * @return the type of the channel (if used).
	 */
	Class<?> type() default Void.class;

	/**
	 * @return the priority of the listener. 0 by default.
	 */
	int priority() default 0;


	/**
	 * Utility class for the listener annotation
	 */
	interface Utils {


		/**
		 * Converts the given annotation into a {@link Channel}. Either the channel name or type must be set in the annotation.
		 *
		 * @param annotation the {@link Listener}-annotation
		 * @return the channel
		 */
		static Optional<Channel> toChannel(final Listener annotation) {
			Channel channel = null;
			if (!Listener.UNSET_TYPE.equals(annotation.type()) && Listener.UNSET_NAME.equals(annotation.name())) {
				channel = Channel.type(annotation.type());
			} else if (!Listener.UNSET_NAME.equals(annotation.name()) && Listener.UNSET_TYPE.equals(annotation.type())) {
				channel = Channel.name(annotation.name());
			}
			return Optional.ofNullable(channel);
		}


	}

}
