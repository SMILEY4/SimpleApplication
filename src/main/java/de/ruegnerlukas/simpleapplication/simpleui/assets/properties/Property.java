package de.ruegnerlukas.simpleapplication.simpleui.assets.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Property {


	/**
	 * The key or type of this property.
	 */
	private final Class<? extends Property> key;




	/**
	 * Check if this property is equal to the given property based in its type and
	 * the {@link Property#isPropertyEqual(Property)} implementation.
	 *
	 * @param other the other property
	 * @return whether the properties are equal
	 */
	public boolean isEqual(final Property other) {
		if (other == null || other.getKey() != this.getKey()) {
			return false;
		} else {
			return isPropertyEqual(other);
		}
	}




	/**
	 * Check whether the given property of the same type is equal to this property.
	 *
	 * @param other the other property. Never null and always of the same type.
	 * @return whether the properties are equal
	 */
	protected abstract boolean isPropertyEqual(Property other);


	/**
	 * @return the value(s) of this property as a readable string.
	 */
	public abstract String printValue();

}
