package de.ruegnerlukas.simpleapplication.common.dependencyinjection.factories;

public class FloatFactory extends PrimitiveFactory<Float> {


	/**
	 * @param providedName The name of the value to be provided
	 * @param value        the provided value
	 */
	public FloatFactory(final String providedName, final float value) {
		super(providedName, value);
	}


}
