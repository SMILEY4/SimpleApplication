package de.ruegnerlukas.simpleapplication.core.presentation.module;

import de.ruegnerlukas.simpleapplication.common.events.TriggerableEventSource;
import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExposedCommand {


	/**
	 * The channel of this command.
	 */
	private final Channel channel;

	/**
	 * The event source of this command.
	 */
	private final TriggerableEventSource<Publishable> eventSource;

	/**
	 * The scope of this command.
	 */
	private final UIExtensionScope scope;




	/**
	 * @param channel     the channel of this command
	 * @param eventSource the event source of this command
	 * @return a new {@link ExposedCommand} with the given name, source and {@link UIExtensionScope#INTERNAL} as its scope
	 */
	public static ExposedCommand internal(final Channel channel, final TriggerableEventSource<Publishable> eventSource) {
		return new ExposedCommand(channel, eventSource, UIExtensionScope.INTERNAL);
	}




	/**
	 * @param channel     the channel of this command
	 * @param eventSource the event source of this command
	 * @return a new {@link ExposedCommand} with the given name, source and {@link UIExtensionScope#LOCAL} as its scope
	 */
	public static ExposedCommand local(final Channel channel, final TriggerableEventSource<Publishable> eventSource) {
		return new ExposedCommand(channel, eventSource, UIExtensionScope.LOCAL);
	}




	/**
	 * @param channel     the channel of this command
	 * @param eventSource the event source of this command
	 * @return a new {@link ExposedCommand} with the given name, source and {@link UIExtensionScope#GLOBAL} as its scope
	 */
	public static ExposedCommand global(final Channel channel, final TriggerableEventSource<Publishable> eventSource) {
		return new ExposedCommand(channel, eventSource, UIExtensionScope.GLOBAL);
	}


}
