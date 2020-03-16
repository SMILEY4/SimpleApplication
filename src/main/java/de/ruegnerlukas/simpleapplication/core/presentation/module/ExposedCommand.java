package de.ruegnerlukas.simpleapplication.core.presentation.module;

import de.ruegnerlukas.simpleapplication.common.events.TriggerableEventSource;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExposedCommand {


	/**
	 * The unique name of this command.
	 */
	private final String name;

	/**
	 * The event source of this command.
	 */
	private final TriggerableEventSource<Publishable> eventSource;

	/**
	 * The scope of this command.
	 */
	private final UIExtensionScope scope;




	/**
	 * @param name        the unique name of this command
	 * @param eventSource the event source of this command
	 * @return a new {@link ExposedCommand} with the given name, source and {@link UIExtensionScope#INTERNAL} as its scope
	 */
	public static ExposedCommand internal(final String name, final TriggerableEventSource<Publishable> eventSource) {
		return new ExposedCommand(name, eventSource, UIExtensionScope.INTERNAL);
	}




	/**
	 * @param name        the unique name of this command
	 * @param eventSource the event source of this command
	 * @return a new {@link ExposedCommand} with the given name, source and {@link UIExtensionScope#LOCAL} as its scope
	 */
	public static ExposedCommand local(final String name, final TriggerableEventSource<Publishable> eventSource) {
		return new ExposedCommand(name, eventSource, UIExtensionScope.LOCAL);
	}




	/**
	 * @param name        the unique name of this command
	 * @param eventSource the event source of this command
	 * @return a new {@link ExposedCommand} with the given name, source and {@link UIExtensionScope#GLOBAL} as its scope
	 */
	public static ExposedCommand global(final String name, final TriggerableEventSource<Publishable> eventSource) {
		return new ExposedCommand(name, eventSource, UIExtensionScope.GLOBAL);
	}


}
