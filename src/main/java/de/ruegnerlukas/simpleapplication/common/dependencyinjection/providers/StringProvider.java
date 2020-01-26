package de.ruegnerlukas.simpleapplication.common.dependencyinjection.providers;

public class StringProvider extends PrimitiveProvider<String> {


	/**
	 * @param name the name of the value this provider should provide.
	 */
	public StringProvider(final String name) {
		super(name);
	}


}
