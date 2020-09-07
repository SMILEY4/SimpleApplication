package de.ruegnerlukas.simpleapplication.simpleui.elements.basenode;

import de.ruegnerlukas.simpleapplication.simpleui.properties.IdProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PropertyStore {


	/**
	 * All  properties of a node.
	 */
	private final Map<Class<? extends Property>, Property> properties = new HashMap<>();


	/**
	 * The id property of a node or null.
	 */
	private IdProperty idProperty;




	/**
	 * @param properties the list of properties
	 */
	public PropertyStore(final List<Property> properties) {
		properties.forEach(property -> this.properties.put(property.getKey(), property));
		idProperty = get(IdProperty.class);
	}




	/**
	 * Get the property with the given type.
	 *
	 * @param type the type of the requested property
	 * @param <T>  the generic type of the property
	 * @return the requested property
	 */
	public <T extends Property> Optional<T> getSafe(final Class<T> type) {
		return Optional.ofNullable(get(type));
	}




	/**
	 * Get the property with the given type or null.
	 *
	 * @param type the type of the requested property
	 * @param <T>  the generic type of the property
	 * @return the requested property or null
	 */
	public <T extends Property> T get(final Class<T> type) {
		final Property property = properties.get(type);
		if (property != null) {
			@SuppressWarnings ("unchecked") final T prop = (T) property;
			return prop;
		} else {
			return null;
		}
	}




	/**
	 * Check whether this node has a property of the given type.
	 *
	 * @param type the type of the property
	 * @return whether this node has a property of the given type.
	 */
	public boolean has(final Class<? extends Property> type) {
		return properties.containsKey(type);
	}




	/**
	 * @return the id of this node defined by the {@link IdProperty}.
	 */
	public Optional<String> getId() {
		return Optional.ofNullable(getIdUnsafe());
	}




	/**
	 * @return the id of this node defined by an {@link IdProperty} or null.
	 */
	public String getIdUnsafe() {
		if (idProperty != null) {
			return idProperty.getId();
		} else {
			return null;
		}
	}


}
