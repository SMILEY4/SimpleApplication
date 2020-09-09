package de.ruegnerlukas.simpleapplication.simpleui.core.node;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.IdProperty;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class PropertyStore {


	/**
	 * All  properties of a node.
	 */
	private final Map<Class<? extends SuiProperty>, SuiProperty> properties = new HashMap<>();


	/**
	 * The id property of a node or null.
	 */
	private IdProperty idProperty;




	/**
	 * @param properties the list of properties
	 */
	public PropertyStore(final List<SuiProperty> properties) {
		properties.forEach(property -> this.properties.put(property.getKey(), property));
		idProperty = get(IdProperty.class);
	}




	/**
	 * Update the property of the same type with the given property or add the given property if none exists.
	 *
	 * @param property the property to update or insert
	 */
	public void upsert(final SuiProperty property) {
		Validations.INPUT.notNull(property).exception("The property to insert/update may not be null.");
		properties.put(property.getKey(), property);
		if (property.getKey() == IdProperty.class) {
			idProperty = (IdProperty) property;
		}
	}




	/**
	 * Removes the property of the given type.
	 *
	 * @param property the type of the property to remove
	 */
	public void remove(final Class<? extends SuiProperty> property) {
		Validations.INPUT.notNull(property).exception("The property type may not be null.");
		properties.remove(property);
		if (property == IdProperty.class) {
			idProperty = null;
		}
	}




	/**
	 * Get the property with the given type.
	 *
	 * @param type the type of the requested property
	 * @param <T>  the generic type of the property
	 * @return the requested property
	 */
	public <T extends SuiProperty> Optional<T> getSafe(final Class<T> type) {
		return Optional.ofNullable(get(type));
	}




	/**
	 * Get the property with the given type or null.
	 *
	 * @param type the type of the requested property
	 * @param <T>  the generic type of the property
	 * @return the requested property or null
	 */
	public <T extends SuiProperty> T get(final Class<T> type) {
		final SuiProperty property = properties.get(type);
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
	public boolean has(final Class<? extends SuiProperty> type) {
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




	/**
	 * @return an unmodifiable list of all properties
	 */
	public Collection<SuiProperty> getAll() {
		return this.properties.values();
	}




	/**
	 * @return an unmodifiable list of all properties
	 */
	public Set<Class<? extends SuiProperty>> getTypes() {
		return this.properties.keySet();
	}




	/**
	 * @return an stream of all properties
	 */
	public Stream<SuiProperty> stream() {
		return this.properties.values().stream();
	}


}
