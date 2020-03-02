package de.ruegnerlukas.simpleapplication.core.presentation.module;

import de.ruegnerlukas.simpleapplication.common.events.ListenableEventSource;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ExposedEvent {


	/**
	 * The unique name of this event.
	 */
	private final String name;

	/**
	 * The event source of this event.
	 */
	private final ListenableEventSource<?> eventSource;

	/**
	 * The scope of this event.
	 */
	private final UIExtensionScope scope;




	/**
	 * @param name        the unique name of this event
	 * @param eventSource the event source of this event
	 * @return a new {@link ExposedEvent} with the given name, source and {@link UIExtensionScope#INTERNAL} as its scope
	 */
	public static <T> ExposedEvent internal(final String name, final ListenableEventSource<T> eventSource) {
		return new ExposedEvent(name, eventSource, UIExtensionScope.INTERNAL);
	}




	/**
	 * @param name        the unique name of this event
	 * @param eventSource the event source of this event
	 * @return a new {@link ExposedEvent} with the given name, source and {@link UIExtensionScope#LOCAL} as its scope
	 */
	public static <T> ExposedEvent local(final String name, final ListenableEventSource<T> eventSource) {
		return new ExposedEvent(name, eventSource, UIExtensionScope.LOCAL);
	}




	/**
	 * @param name        the unique name of this event
	 * @param eventSource the event source of this event
	 * @return a new {@link ExposedEvent} with the given name, source and {@link UIExtensionScope#GLOBAL} as its scope
	 */
	public static <T> ExposedEvent global(final String name, final ListenableEventSource<T> eventSource) {
		return new ExposedEvent(name, eventSource, UIExtensionScope.GLOBAL);
	}




	/**
	 * Checks if the scope of this exposed event is at least the given scope / is relevant in the given scope.
	 *
	 * @param scope the given scope
	 * @return whether the scope of this exposed event is at least the given scope
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
