package de.ruegnerlukas.simpleapplication.simpleui.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Property {


	private final Class<? extends Property> key;




	public boolean isEqual(final Property other) {
		if (other == null || other.getKey() != this.getKey()) {
			return false;
		} else {
			return isPropertyEqual(other);
		}
	}




	protected abstract boolean isPropertyEqual(Property other);




	public <T> T getAs(Class<T> propKey) {
		return (T) this;
	}




	public <T> T getAs() {
		return (T) this;
	}




	public abstract String printValue();

}
