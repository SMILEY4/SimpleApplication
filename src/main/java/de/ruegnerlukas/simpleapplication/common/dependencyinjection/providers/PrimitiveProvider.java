package de.ruegnerlukas.simpleapplication.common.dependencyinjection.providers;

import de.ruegnerlukas.simpleapplication.common.dependencyinjection.RequestType;

public class PrimitiveProvider<T> extends AbstractProvider<T, T> {


	/**
	 * @param name the name of the value this provider should provide.
	 */
	public PrimitiveProvider(final String name) {
		super(null, name, RequestType.BY_NAME);
	}

}
