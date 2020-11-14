package de.ruegnerlukas.simpleapplication.core.simpleui.core.node;

import lombok.Getter;

import java.util.function.BiFunction;

public abstract class SuiProperty {


	/**
	 * The key or type of this property.
	 */
	@Getter
	private final Class<? extends SuiProperty> key;

	/**
	 * The function comparing two properties of the same type checking if they are considered equal.
	 */
	@Getter
	private final BiFunction<?, ?, Boolean> comparator;

	/**
	 * The id of this property or null. Two properties of the same type with the same id are automatically considered equal.
	 */
	@Getter
	private final String propertyId;




	/**
	 * @param key        the key or type of this property.
	 * @param comparator the function comparing two properties of the same type checking if they are considered equal.
	 * @param <T>        the generic type of this property
	 */
	public <T extends SuiProperty> SuiProperty(final Class<? extends SuiProperty> key, final BiFunction<T, T, Boolean> comparator) {
		this(key, comparator, null);
	}




	/**
	 * @param key        the key or type of this property.
	 * @param comparator the function comparing two properties of the same type checking if they are considered equal.
	 * @param propertyId the id of this property or null.
	 *                   Two properties of the same type with the same id are automatically considered equal.
	 * @param <T>        the generic type of this property
	 */
	public <T extends SuiProperty> SuiProperty(final Class<? extends SuiProperty> key,
											   final BiFunction<T, T, Boolean> comparator,
											   final String propertyId) {
		this.key = key;
		this.comparator = comparator;
		this.propertyId = propertyId;
	}




	/**
	 * @param other the other property
	 * @return whether this property and the given other property is equal
	 */
	public boolean isPropertyEqual(final SuiProperty other) {
		if (other == null || this.getKey() != other.getKey()) {
			return false;
		}
		if (this.getPropertyId() != null && other.getPropertyId() != null && this.getPropertyId().equals(other.getPropertyId())) {
			return true;
		}
		@SuppressWarnings ("unchecked") BiFunction<Object, Object, Boolean> genericComparator
				= (BiFunction<Object, Object, Boolean>) comparator;
		return genericComparator.apply(this, other);
	}


}
