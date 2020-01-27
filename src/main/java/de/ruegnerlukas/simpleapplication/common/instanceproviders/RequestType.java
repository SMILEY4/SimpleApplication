package de.ruegnerlukas.simpleapplication.common.instanceproviders;

/**
 * The type of an instance request. Can either request instances by the class type or by a name (as string).
 */
public enum RequestType {

	/**
	 * By class type.
	 */
	BY_TYPE,

	/**
	 * By a name as string.
	 */
	BY_NAME
}
