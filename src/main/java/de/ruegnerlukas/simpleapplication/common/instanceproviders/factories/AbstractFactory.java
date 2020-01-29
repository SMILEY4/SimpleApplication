package de.ruegnerlukas.simpleapplication.common.instanceproviders.factories;

public interface AbstractFactory<T> {


	/**
	 * Creates a new object.
	 *
	 * @return the created object
	 */
	T buildObject();


}
