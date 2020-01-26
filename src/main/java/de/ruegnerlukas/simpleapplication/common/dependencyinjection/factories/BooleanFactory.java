package de.ruegnerlukas.simpleapplication.common.dependencyinjection.factories;

public class BooleanFactory extends PrimitiveFactory<Boolean> {


	/**
	 * @param providedName The name of the value to be provided
	 * @param value        the provided value
	 */
	public BooleanFactory(final String providedName, final boolean value) {
		super(providedName, value);
	}


}
