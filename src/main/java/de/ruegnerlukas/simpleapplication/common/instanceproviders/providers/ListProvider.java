package de.ruegnerlukas.simpleapplication.common.instanceproviders.providers;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.RequestType;

import java.util.List;

public class ListProvider<T> extends AbstractProvider<T, List<T>> {


	/**
	 * @param type the type of the list this provider should provide.
	 */
	public ListProvider(final Class<T> type) {
		super(type, null, RequestType.BY_TYPE);
	}




	/**
	 * @param name the name of the list this provider should provide.
	 */
	public ListProvider(final String name) {
		super(null, name, RequestType.BY_NAME);
	}


}
