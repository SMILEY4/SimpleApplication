package de.ruegnerlukas.simpleapplication.common.dependencyinjection.providers;

import de.ruegnerlukas.simpleapplication.common.dependencyinjection.RequestType;

import java.util.Map;

public class MapProvider<K, V> extends AbstractProvider<Map.Entry<K, V>, Map<K, V>> {


	/**
	 * @param type the type of the map this provider should provide.
	 */
	public MapProvider(final Class<Map.Entry<K, V>> type) {
		super(type, null, RequestType.BY_TYPE);
	}




	/**
	 * @param name the name of the map this provider should provide.
	 */
	public MapProvider(final String name) {
		super(null, name, RequestType.BY_NAME);
	}


}
