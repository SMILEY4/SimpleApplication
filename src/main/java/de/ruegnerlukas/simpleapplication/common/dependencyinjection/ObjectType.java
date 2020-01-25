package de.ruegnerlukas.simpleapplication.common.dependencyinjection;

public enum ObjectType {

	/**
	 * One instance for each provider.
	 */
	NON_SINGLETON,

	/**
	 * The same instance for every provider.
	 */
	SINGLETON;

}
