package de.ruegnerlukas.simpleapplication.common.instanceproviders.factories;

public class IntegerFactory extends PrimitiveFactory<Integer> {


	/**
	 * @param providedName The name of the value to be provided
	 * @param value        the provided value
	 */
	public IntegerFactory(final String providedName, final int value) {
		super(providedName, value);
	}


}
