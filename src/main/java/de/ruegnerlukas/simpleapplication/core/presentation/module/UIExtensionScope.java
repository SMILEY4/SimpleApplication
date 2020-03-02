package de.ruegnerlukas.simpleapplication.core.presentation.module;

import java.util.List;

public enum UIExtensionScope {

	/**
	 * The events and commands are only available inside the same module.
	 */
	INTERNAL,

	/**
	 * The events and commands are available inside the same module and to other objects knowing the model (i.e. parent)
	 */
	LOCAL,

	/**
	 * The events and commands are available to the whole application
	 */
	GLOBAL;




	/**
	 * Checks if this scope is at least the given scope / is relevant in the given scope.
	 *
	 * @param scope the given scope
	 * @return whether this scope is at least the given scope
	 */
	public boolean isAtLeast(final UIExtensionScope scope) {
		switch (scope) {
			case INTERNAL:
				return List.of(UIExtensionScope.INTERNAL, UIExtensionScope.LOCAL, UIExtensionScope.GLOBAL).contains(this);
			case LOCAL:
				return List.of(UIExtensionScope.LOCAL, UIExtensionScope.GLOBAL).contains(this);
			case GLOBAL:
				return this == UIExtensionScope.GLOBAL;
			default:
				return false;
		}
	}

}
