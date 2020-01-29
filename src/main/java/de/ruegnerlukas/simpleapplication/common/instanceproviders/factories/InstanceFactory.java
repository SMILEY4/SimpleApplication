package de.ruegnerlukas.simpleapplication.common.instanceproviders.factories;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.ObjectType;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.RequestType;

public abstract class InstanceFactory<T> extends GenericFactory<T, T> {


	/**
	 * @param providedType The type of the singleton object to be created.
	 */
	public InstanceFactory(final Class<T> providedType) {
		this(providedType, ObjectType.SINGLETON);
	}




	/**
	 * @param providedType The type of the object to be created
	 * @param objectType   The type of the object to be created.
	 */
	public InstanceFactory(final Class<T> providedType, final ObjectType objectType) {
		super(objectType, providedType, null, RequestType.BY_TYPE);
	}




	/**
	 * @param providedName The name of the singleton object to be created
	 */
	public InstanceFactory(final String providedName) {
		this(providedName, ObjectType.SINGLETON);
	}




	/**
	 * @param providedName The name of the object to be created
	 * @param objectType   The type of the object to be created.
	 */
	public InstanceFactory(final String providedName, final ObjectType objectType) {
		super(objectType, null, providedName, RequestType.BY_NAME);
	}


}
