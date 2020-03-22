package de.ruegnerlukas.simpleapplication.core.presentation.module;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.PublishableEvent.PublishableEventSource;
import lombok.Getter;

@Getter
public class ExposedEvent {


	/**
	 * The event source of this event.
	 */
	private final PublishableEventSource eventSource;

	/**
	 * The scope of this event.
	 */
	private final UIExtensionScope scope;




	/**
	 * @param eventSource the event source of this event
	 * @return a new {@link ExposedEvent} with the given name, source and {@link UIExtensionScope#INTERNAL} as its scope
	 */
	public static ExposedEvent internal(final PublishableEventSource eventSource) {
		return new ExposedEvent(eventSource, UIExtensionScope.INTERNAL);
	}




	/**
	 * @param eventSource the event source of this event
	 * @return a new {@link ExposedEvent} with the given name, source and {@link UIExtensionScope#LOCAL} as its scope
	 */
	public static ExposedEvent local(final PublishableEventSource eventSource) {
		return new ExposedEvent(eventSource, UIExtensionScope.LOCAL);
	}




	/**
	 * @param eventSource the event source of this event
	 * @return a new {@link ExposedEvent} with the given name, source and {@link UIExtensionScope#GLOBAL} as its scope
	 */
	public static ExposedEvent global(final PublishableEventSource eventSource) {
		return new ExposedEvent(eventSource, UIExtensionScope.GLOBAL);
	}




	/**
	 * @param eventSource the {@link PublishableEventSource} to expose
	 * @param scope       the scope of this exposed event
	 */
	public ExposedEvent(final PublishableEventSource eventSource, final UIExtensionScope scope) {
		this.eventSource = eventSource;
		this.scope = scope;
	}




	/**
	 * @return the {@link Channel} of this exposed event.
	 */
	public Channel getChannel() {
		return eventSource.getChannel();
	}

}
