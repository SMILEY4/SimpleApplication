package de.ruegnerlukas.simpleapplication.common.dependencyinjection.factories;

import de.ruegnerlukas.simpleapplication.common.dependencyinjection.ObjectType;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.RequestType;

import java.util.List;

public abstract class ListFactory<T> extends AbstractFactory<T, List<T>> {


	/**
	 * @param providedType The type of the singleton list to be created
	 */
	public ListFactory(final Class<T> providedType) {
		this(providedType, ObjectType.SINGLETON);
	}




	/**
	 * @param providedType The type of the list to be created
	 * @param objectType   The type of the list to be created.
	 */
	public ListFactory(final Class<T> providedType, final ObjectType objectType) {
		super(objectType, providedType, null, RequestType.BY_TYPE);
	}




	/**
	 * @param providedName The name of the singleton list to be created
	 */
	public ListFactory(final String providedName) {
		this(providedName, ObjectType.SINGLETON);
	}




	/**
	 * @param providedName The name of the list to be created
	 * @param objectType   The type of the list to be created.
	 */
	public ListFactory(final String providedName, final ObjectType objectType) {
		super(objectType, null, providedName, RequestType.BY_NAME);
	}


}
