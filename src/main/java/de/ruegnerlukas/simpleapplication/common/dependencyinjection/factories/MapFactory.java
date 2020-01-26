package de.ruegnerlukas.simpleapplication.common.dependencyinjection.factories;

import de.ruegnerlukas.simpleapplication.common.dependencyinjection.ObjectType;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.RequestType;

import java.util.Map;

public abstract class MapFactory<K, V> extends AbstractFactory<Map.Entry<K, V>, Map<K, V>> {


	/**
	 * @param providedType The type of the singleton map to be created
	 */
	public MapFactory(final Class<Map.Entry<K, V>> providedType) {
		this(providedType, ObjectType.SINGLETON);
	}




	/**
	 * @param providedType The type of the map to be created
	 * @param objectType   The type of the map to be created.
	 */
	public MapFactory(final Class<Map.Entry<K, V>> providedType, final ObjectType objectType) {
		super(objectType, providedType, null, RequestType.BY_TYPE);
	}




	/**
	 * @param providedName The name of the singleton map to be created
	 */
	public MapFactory(final String providedName) {
		this(providedName, ObjectType.SINGLETON);
	}




	/**
	 * @param providedName The name of the map to be created
	 * @param objectType   The type of the map to be created.
	 */
	public MapFactory(final String providedName, final ObjectType objectType) {
		super(objectType, null, providedName, RequestType.BY_NAME);
	}


}
