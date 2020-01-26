package de.ruegnerlukas.simpleapplication.common.dependencyinjection.factories;

import de.ruegnerlukas.simpleapplication.common.dependencyinjection.ObjectType;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.RequestType;
import lombok.Getter;

public abstract class AbstractFactory<R, P> {


	/**
	 * The type of the object to be created.
	 */
	@Getter
	private final ObjectType objectType;

	/**
	 * The type of the object to be provided. Providers requesting this type will get an instance created by this factory.
	 */
	@Getter
	private final Class<R> providedType;


	/**
	 * The name of the object to be provided. Providers requesting this name will get an instance created by this factory.
	 */
	@Getter
	private final String providedName;


	/**
	 * The type of the provider / whether to use the {@link AbstractFactory#providedType} or {@link AbstractFactory#providedName}.
	 */
	@Getter
	private final RequestType requestType;




	/**
	 * @param objectType   The type of the object to be created.
	 * @param providedType The type of the object to be provided
	 * @param providedName The name of the object to be provided
	 * @param requestType  The type of the provider / whether to use the {@link AbstractFactory#providedType}
	 *                     or {@link AbstractFactory#providedName}
	 */
	protected AbstractFactory(final ObjectType objectType, final Class<R> providedType, final String providedName,
							  final RequestType requestType) {
		this.objectType = objectType;
		this.providedType = providedType;
		this.providedName = providedName;
		this.requestType = requestType;
	}




	/**
	 * Creates a new object.
	 *
	 * @return the created object
	 */
	public abstract P buildObject();

}
