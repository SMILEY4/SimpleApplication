package de.ruegnerlukas.simpleapplication.common.dependencyinjection.factories;

import de.ruegnerlukas.simpleapplication.common.dependencyinjection.ObjectType;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.RequestType;

public class PrimitiveFactory<T> extends AbstractFactory<T, T> {


	/**
	 * The value this factory "creates".
	 */
	private final T value;




	/**
	 * @param providedName The name of the value to be provided
	 * @param value        the provided value
	 */
	public PrimitiveFactory(final String providedName, final T value) {
		super(ObjectType.NON_SINGLETON, null, providedName, RequestType.BY_NAME);
		this.value = value;
	}




	/**
	 * @return the value
	 */
	@Override
	public T buildObject() {
		return value;
	}

}
