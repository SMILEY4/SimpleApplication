package de.ruegnerlukas.simpleapplication.simpleui.core.node;

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
	 * @param key        the key or type of this property.
	 * @param comparator the function comparing two properties of the same type checking if they are considered equal.
	 * @param <T>        the generic type of this property
	 */
	public <T extends SuiProperty> SuiProperty(final Class<? extends SuiProperty> key, final BiFunction<T, T, Boolean> comparator) {
		this.key = key;
		this.comparator = comparator;
	}




	/**
	 * @param other the other property
	 * @return whether this property and the given other property is equal
	 */
	public boolean isPropertyEqual(final SuiProperty other) {
		if (other == null || this.getKey() != other.getKey()) {
			return false;
		} else {
			@SuppressWarnings ("unchecked") BiFunction<Object, Object, Boolean> genericComparator
					= (BiFunction<Object, Object, Boolean>) comparator;
			return genericComparator.apply(this, other);
		}
	}


}
