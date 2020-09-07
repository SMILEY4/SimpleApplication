package de.ruegnerlukas.simpleapplication.simpleui.properties;

import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;

import java.util.List;
import java.util.stream.Collectors;

public class IllegalPropertiesException extends RuntimeException {


	/**
	 * Indicates that a property/properties was/were added to an element that does not accept the given property/properties.
	 *
	 * @param nodeType   the type of the {@link SuiNode}
	 * @param properties the list of illegal properties
	 */
	public IllegalPropertiesException(final Class<?> nodeType, final List<Property> properties) {
		super("Illegal properties for " + nodeType.getSimpleName() + " ["
				+ properties.stream().map(prop -> prop.getKey().toString()).collect(Collectors.joining(", "))
				+ "]");
	}


}
