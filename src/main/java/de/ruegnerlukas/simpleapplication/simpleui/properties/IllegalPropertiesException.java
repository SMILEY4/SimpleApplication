package de.ruegnerlukas.simpleapplication.simpleui.properties;

import java.util.List;
import java.util.stream.Collectors;

public class IllegalPropertiesException extends RuntimeException {


	public IllegalPropertiesException(final Class<?> nodeType, final List<Property> properties) {
		super("Illegal properties for " + nodeType.getSimpleName() + " ["
				+ properties.stream().map(prop -> prop.getKey().toString()).collect(Collectors.joining(", "))
				+ "]");
	}


}
