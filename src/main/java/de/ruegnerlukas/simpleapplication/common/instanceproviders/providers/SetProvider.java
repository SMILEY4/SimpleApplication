package de.ruegnerlukas.simpleapplication.common.instanceproviders.providers;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.RequestType;

import java.util.Set;

public class SetProvider<T> extends AbstractProvider<T, Set<T>> {


	/**
	 * @param type the type of the set this provider should provide.
	 */
	public SetProvider(final Class<T> type) {
		super(type, null, RequestType.BY_TYPE);
	}




	/**
	 * @param name the name of the set this provider should provide.
	 */
	public SetProvider(final String name) {
		super(null, name, RequestType.BY_NAME);
	}


}
