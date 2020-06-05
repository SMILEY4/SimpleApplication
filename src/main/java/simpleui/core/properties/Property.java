package simpleui.core.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Property {


	private final Class<? extends Property> key;




	public boolean compare(final Property other) {
		if (other == null || other.getKey() != this.getKey()) {
			return false;
		} else {
			return compareProperty(other);
		}
	}




	protected abstract boolean compareProperty(Property other);

}
