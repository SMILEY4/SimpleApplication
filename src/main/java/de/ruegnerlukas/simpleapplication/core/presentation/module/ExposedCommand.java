package de.ruegnerlukas.simpleapplication.core.presentation.module;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.PublishableEvent.PublishableEventSource;
import lombok.Getter;

@Getter
public class ExposedCommand {


	/**
	 * The event source of this command.
	 */
	private final PublishableEventSource eventSource;

	/**
	 * The scope of this command.
	 */
	private final UIExtensionScope scope;




	/**
	 * @param eventSource the event source of this command
	 * @return a new {@link ExposedCommand} with the given name, source and {@link UIExtensionScope#INTERNAL} as its scope
	 */
	public static ExposedCommand internal(final PublishableEventSource eventSource) {
		return new ExposedCommand(eventSource, UIExtensionScope.INTERNAL);
	}




	/**
	 * @param eventSource the event source of this command
	 * @return a new {@link ExposedCommand} with the given name, source and {@link UIExtensionScope#LOCAL} as its scope
	 */
	public static ExposedCommand local(final PublishableEventSource eventSource) {
		return new ExposedCommand(eventSource, UIExtensionScope.LOCAL);
	}




	/**
	 * @param eventSource the event source of this command
	 * @return a new {@link ExposedCommand} with the given name, source and {@link UIExtensionScope#GLOBAL} as its scope
	 */
	public static ExposedCommand global(final PublishableEventSource eventSource) {
		return new ExposedCommand(eventSource, UIExtensionScope.GLOBAL);
	}




	/**
	 * @param eventSource the {@link PublishableEventSource} to expose
	 * @param scope       the scope of this exposed command
	 */
	public ExposedCommand(final PublishableEventSource eventSource, final UIExtensionScope scope) {
		this.eventSource = eventSource;
		this.scope = scope;
	}




	/**
	 * @return the {@link Channel} of this exposed command.
	 */
	public Channel getChannel() {
		return eventSource.getChannel();
	}


}
