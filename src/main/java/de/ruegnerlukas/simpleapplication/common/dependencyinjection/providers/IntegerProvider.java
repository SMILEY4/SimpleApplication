package de.ruegnerlukas.simpleapplication.common.dependencyinjection.providers;

public class IntegerProvider extends PrimitiveProvider<Integer> {


	/**
	 * @param name the name of the value this provider should provide.
	 */
	public IntegerProvider(final String name) {
		super(name);
	}


}
