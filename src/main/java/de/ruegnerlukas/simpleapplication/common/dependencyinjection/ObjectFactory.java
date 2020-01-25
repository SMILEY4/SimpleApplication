package de.ruegnerlukas.simpleapplication.common.dependencyinjection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class ObjectFactory<T> {


	/**
	 * The type of the object to be created.
	 */
	@Getter
	private final ObjectType type;

	/**
	 * The type of the object to be provided. {@link Provider}s requesting this type will get an instance created by this factory.
	 */
	@Getter
	private final Class<T> providedType;




	/**
	 * Creates an instance of the object of this factory.
	 *
	 * @return the created object
	 */
	public abstract T buildObject();

}
