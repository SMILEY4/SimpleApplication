package de.ruegnerlukas.simpleapplication.common.dependencyinjection.providers;

import de.ruegnerlukas.simpleapplication.common.dependencyinjection.RequestType;

public class ArrayProvider<T> extends AbstractProvider<T, T[]> {


	/**
	 * @param type the type of the instance this provider should provide.
	 */
	public ArrayProvider(final Class<T> type) {
		super(type, null, RequestType.BY_TYPE);
	}




	/**
	 * @param name the name of the instance this provider should provide.
	 */
	public ArrayProvider(final String name) {
		super(null, name, RequestType.BY_NAME);
	}


}
