package de.ruegnerlukas.simpleapplication.core.presentation.module;

import de.ruegnerlukas.simpleapplication.common.events.ListenableEventSource;
import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExposedEvent {


	/**
	 * The channel of this event.
	 */
	private final Channel channel;

	/**
	 * The event source of this event.
	 */
	private final ListenableEventSource<Publishable> eventSource;

	/**
	 * The scope of this event.
	 */
	private final UIExtensionScope scope;




	/**
	 * @param channel     the channel of this event
	 * @param eventSource the event source of this event
	 * @return a new {@link ExposedEvent} with the given name, source and {@link UIExtensionScope#INTERNAL} as its scope
	 */
	public static <T> ExposedEvent internal(final Channel channel, final ListenableEventSource<Publishable> eventSource) {
		return new ExposedEvent(channel, eventSource, UIExtensionScope.INTERNAL);
	}




	/**
	 * @param channel     the channel of this event
	 * @param eventSource the event source of this event
	 * @return a new {@link ExposedEvent} with the given name, source and {@link UIExtensionScope#LOCAL} as its scope
	 */
	public static <T> ExposedEvent local(final Channel channel, final ListenableEventSource<Publishable> eventSource) {
		return new ExposedEvent(channel, eventSource, UIExtensionScope.LOCAL);
	}




	/**
	 * @param channel     the channel of this event
	 * @param eventSource the event source of this event
	 * @return a new {@link ExposedEvent} with the given name, source and {@link UIExtensionScope#GLOBAL} as its scope
	 */
	public static <T> ExposedEvent global(final Channel channel, final ListenableEventSource<Publishable> eventSource) {
		return new ExposedEvent(channel, eventSource, UIExtensionScope.GLOBAL);
	}


}
