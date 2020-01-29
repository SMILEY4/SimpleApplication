package de.ruegnerlukas.simpleapplication.core.presentation.module;

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
	GLOBAL

}
