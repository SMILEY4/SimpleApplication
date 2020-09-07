package de.ruegnerlukas.simpleapplication.simpleui.assets.properties;

import java.util.List;
import java.util.stream.Collectors;

public class DuplicatePropertiesException extends RuntimeException {


	/**
	 * Indicates that a type of property was added more than once to an element.
	 *
	 * @param properties the list of duplicate properties
	 */
	public DuplicatePropertiesException(final List<Property> properties) {
		super("Duplicate properties: ["
				+ properties.stream().map(prop -> prop.getKey().toString()).collect(Collectors.joining(", "))
				+ "]");
	}


}
