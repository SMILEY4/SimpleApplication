package de.ruegnerlukas.simpleapplication.common.dependencyinjection.factories;

public class StringFactory extends PrimitiveFactory<String> {


	/**
	 * @param providedName The name of the value to be provided
	 * @param value        the provided value
	 */
	public StringFactory(final String providedName, final String value) {
		super(providedName, value);
	}


}
