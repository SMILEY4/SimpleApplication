package de.ruegnerlukas.simpleapplication.common.dependencyinjection;

import lombok.AccessLevel;
import lombok.Getter;

public abstract class InstanceFactory<T> {


	/**
	 * The type of the object to be created.
	 */
	@Getter
	private final ObjectType objectType;

	/**
	 * The type of the object to be provided. {@link Provider}s requesting this type will get an instance created by this factory.
	 */
	@Getter
	private final Class<T> providedType;


	/**
	 * The name of the object to be provided. {@link Provider}s requesting this name will get an instance created by this factory.
	 */
	@Getter
	private final String providedName;


	/**
	 * The type of the provider / whether to use the {@link InstanceFactory#providedType} or {@link InstanceFactory#providedName}.
	 */
	@Getter (AccessLevel.PACKAGE)
	private final RequestType requestType;




	/**
	 * @param objectType   The type of the object to be created.
	 * @param providedType The type of the object to be provided
	 */
	public InstanceFactory(final ObjectType objectType, final Class<T> providedType) {
		this(objectType, providedType, null, RequestType.BY_TYPE);
	}




	/**
	 * @param objectType   The type of the object to be created.
	 * @param providedName The name of the object to be provided
	 */
	public InstanceFactory(final ObjectType objectType, final String providedName) {
		this(objectType, null, providedName, RequestType.BY_NAME);
	}




	/**
	 * @param objectType   The type of the object to be created.
	 * @param providedType The type of the object to be provided
	 * @param providedName The name of the object to be provided
	 * @param requestType  The type of the provider / whether to use the {@link InstanceFactory#providedType} or {@link InstanceFactory#providedName}
	 */
	private InstanceFactory(final ObjectType objectType, final Class<T> providedType, final String providedName,
							final RequestType requestType) {
		this.objectType = objectType;
		this.providedType = providedType;
		this.providedName = providedName;
		this.requestType = requestType;
	}




	/**
	 * Creates an instance of the object of this factory.
	 *
	 * @return the created object
	 */
	public abstract T buildObject();

}
