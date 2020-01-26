package de.ruegnerlukas.simpleapplication.common.dependencyinjection.factories;

public class DoubleFactory extends PrimitiveFactory<Double> {


	/**
	 * @param providedName The name of the value to be provided
	 * @param value        the provided value
	 */
	public DoubleFactory(final String providedName, final double value) {
		super(providedName, value);
	}


}
