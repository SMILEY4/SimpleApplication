package de.ruegnerlukas.simpleapplication.common.instanceproviders.providers;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.RequestType;

public class PrimitiveProvider<T> extends AbstractProvider<T, T> {


	/**
	 * @param name the name of the value this provider should provide.
	 */
	public PrimitiveProvider(final String name) {
		super(null, name, RequestType.BY_NAME);
	}

}
