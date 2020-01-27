package de.ruegnerlukas.simpleapplication.common.instanceproviders.factories;

public class LongFactory extends PrimitiveFactory<Long> {


	/**
	 * @param providedName The name of the value to be provided
	 * @param value        the provided value
	 */
	public LongFactory(final String providedName, final long value) {
		super(providedName, value);
	}


}
