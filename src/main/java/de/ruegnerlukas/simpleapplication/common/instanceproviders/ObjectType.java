package de.ruegnerlukas.simpleapplication.common.instanceproviders;

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
