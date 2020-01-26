package de.ruegnerlukas.simpleapplication.common.dependencyinjection.providers;

import de.ruegnerlukas.simpleapplication.common.dependencyinjection.RequestType;

public class Provider<T> extends AbstractProvider<T, T> {


	/**
	 * @param type the type of the instance this provider should provide.
	 */
	public Provider(final Class<T> type) {
		super(type, null, RequestType.BY_TYPE);
	}




	/**
	 * @param name the name of the instance this provider should provide.
	 */
	public Provider(final String name) {
		super(null, name, RequestType.BY_NAME);
	}


}
