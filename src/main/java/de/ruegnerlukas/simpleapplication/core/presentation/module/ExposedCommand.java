package de.ruegnerlukas.simpleapplication.core.presentation.module;

import de.ruegnerlukas.simpleapplication.common.events.TriggerableEventSource;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

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
	private final TriggerableEventSource<?> eventSource;

	/**
	 * The scope of this command.
	 */
	private final UIExtensionScope scope;




	/**
	 * @param name        the unique name of this command
	 * @param eventSource the event source of this command
	 * @return a new {@link ExposedCommand} with the given name, source and {@link UIExtensionScope#INTERNAL} as its scope
	 */
	public static <T> ExposedCommand internal(final String name, final TriggerableEventSource<T> eventSource) {
		return new ExposedCommand(name, eventSource, UIExtensionScope.INTERNAL);
	}




	/**
	 * @param name        the unique name of this command
	 * @param eventSource the event source of this command
	 * @return a new {@link ExposedCommand} with the given name, source and {@link UIExtensionScope#LOCAL} as its scope
	 */
	public static <T> ExposedCommand local(final String name, final TriggerableEventSource<T> eventSource) {
		return new ExposedCommand(name, eventSource, UIExtensionScope.LOCAL);
	}




	/**
	 * @param name        the unique name of this command
	 * @param eventSource the event source of this command
	 * @return a new {@link ExposedCommand} with the given name, source and {@link UIExtensionScope#GLOBAL} as its scope
	 */
	public static <T> ExposedCommand global(final String name, final TriggerableEventSource<T> eventSource) {
		return new ExposedCommand(name, eventSource, UIExtensionScope.GLOBAL);
	}




	/**
	 * Checks if the scope of this exposed command is at least the given scope / is relevant in the given scope.
	 *
	 * @param scope the given scope
	 * @return whether the scope of this exposed command is at least the given scope
	 */
	public boolean isAtLeast(final UIExtensionScope scope) {
		switch (scope) {
			case INTERNAL:
				return List.of(UIExtensionScope.INTERNAL, UIExtensionScope.LOCAL, UIExtensionScope.GLOBAL)
						.contains(getScope());
			case LOCAL:
				return List.of(UIExtensionScope.LOCAL, UIExtensionScope.GLOBAL).contains(getScope());
			case GLOBAL:
				return getScope() == UIExtensionScope.GLOBAL;
			default:
				return false;
		}
	}


}
