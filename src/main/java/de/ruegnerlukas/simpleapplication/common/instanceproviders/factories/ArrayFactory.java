package de.ruegnerlukas.simpleapplication.common.instanceproviders.factories;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.ObjectType;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.RequestType;

public abstract class ArrayFactory<T> extends AbstractFactory<T, T[]> {


	/**
	 * @param providedType The type of the singleton array to be created
	 */
	public ArrayFactory(final Class<T> providedType) {
		this(providedType, ObjectType.SINGLETON);
	}




	/**
	 * @param providedType The type of the array to be created
	 * @param objectType   The type of the array to be created.
	 */
	public ArrayFactory(final Class<T> providedType, final ObjectType objectType) {
		super(objectType, providedType, null, RequestType.BY_TYPE);
	}




	/**
	 * @param providedName The name of the singleton array to be created
	 */
	public ArrayFactory(final String providedName) {
		this(providedName, ObjectType.SINGLETON);
	}




	/**
	 * @param providedName The name of the array to be created
	 * @param objectType   The type of the array to be created.
	 */
	public ArrayFactory(final String providedName, final ObjectType objectType) {
		super(objectType, null, providedName, RequestType.BY_NAME);
	}


}
