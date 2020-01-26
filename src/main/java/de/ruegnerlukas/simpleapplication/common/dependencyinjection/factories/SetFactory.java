package de.ruegnerlukas.simpleapplication.common.dependencyinjection.factories;

import de.ruegnerlukas.simpleapplication.common.dependencyinjection.ObjectType;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.RequestType;

import java.util.Set;

public abstract class SetFactory<T> extends AbstractFactory<T, Set<T>> {


	/**
	 * @param providedType The type of the singleton set to be created
	 */
	public SetFactory(final Class<T> providedType) {
		this(providedType, ObjectType.SINGLETON);
	}




	/**
	 * @param providedType The type of the set to be created
	 * @param objectType   The type of the set to be created.
	 */
	public SetFactory(final Class<T> providedType, final ObjectType objectType) {
		super(objectType, providedType, null, RequestType.BY_TYPE);
	}




	/**
	 * @param providedName The name of the singleton set to be created
	 */
	public SetFactory(final String providedName) {
		this(providedName, ObjectType.SINGLETON);
	}




	/**
	 * @param providedName The name of the set to be created
	 * @param objectType   The type of the set to be created.
	 */
	public SetFactory(final String providedName, final ObjectType objectType) {
		super(objectType, null, providedName, RequestType.BY_NAME);
	}


}
