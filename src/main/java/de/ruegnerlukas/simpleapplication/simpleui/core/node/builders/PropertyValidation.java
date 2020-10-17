package de.ruegnerlukas.simpleapplication.simpleui.core.node.builders;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public final class PropertyValidation {


	/**
	 * Hidden constructor of utility class.
	 */
	private PropertyValidation() {
		// do nothing
	}




	/**
	 * Checks whether the given properties are valid.
	 * Throws an exception if the list contains illegal properties
	 * (not registered ad the {@link de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry}).
	 *
	 * @param nodeType   the type of the node
	 * @param properties the given properties to check
	 */
	public static void validate(final Class<?> nodeType, final List<SuiProperty> properties) {
		validate(nodeType, SuiRegistry.get().getEntry(nodeType).getProperties(), properties);
	}




	/**
	 * Checks whether the given properties are valid.
	 * Throws an exception if the list contains illegal properties (not contained in allowed list)
	 *
	 * @param nodeType          the type of the node
	 * @param allowedProperties the list of allowed properties
	 * @param properties        the given properties to check
	 */
	public static void validate(final Class<?> nodeType,
								final Set<Class<? extends SuiProperty>> allowedProperties,
								final List<SuiProperty> properties) {
		final List<Class<? extends SuiProperty>> propKeyList = propertiesAsKeyList(properties);
		final Set<Class<? extends SuiProperty>> propKeySet = new HashSet<>(propKeyList);
		checkIllegal(nodeType, allowedProperties, propKeySet);
		checkDuplicates(propKeyList);
		checkConflicting(propKeySet);
	}




	/**
	 * @param properties the properties
	 * @return a list of keys of all the given properties.
	 */
	private static List<Class<? extends SuiProperty>> propertiesAsKeyList(final List<SuiProperty> properties) {
		return properties.stream()
				.map(SuiProperty::getKey)
				.collect(Collectors.toList());
	}




	/**
	 * Checks whether the given properties are allowed.
	 * Throws a validation exception if the list contains illegal properties (not contained in allowed list)
	 *
	 * @param nodeType          the type of the node
	 * @param allowedProperties the list of allowed properties
	 * @param properties        the given properties to check
	 */
	private static void checkIllegal(final Class<?> nodeType,
									 final Set<Class<? extends SuiProperty>> allowedProperties,
									 final Set<Class<? extends SuiProperty>> properties) {
		Set<Class<? extends SuiProperty>> illegal = findIllegal(allowedProperties, properties);
		if (!illegal.isEmpty()) {
			final String message =
					"Illegal properties for "
							+ nodeType.getSimpleName()
							+ " ["
							+ properties.stream().map(Class::getSimpleName).collect(Collectors.joining(", "))
							+ "]";
			Validations.STATE.fail().exception(message);
		}
	}




	/**
	 * Finds all illegal properties.
	 *
	 * @param allowedProperties the list of allowed properties
	 * @param properties        the list of properties to check
	 * @return the list of illegal properties.
	 */
	private static Set<Class<? extends SuiProperty>> findIllegal(final Set<Class<? extends SuiProperty>> allowedProperties,
																 final Set<Class<? extends SuiProperty>> properties) {
		final Set<Class<? extends SuiProperty>> illegal = new HashSet<>();
		properties.forEach(property -> {
			if (!allowedProperties.contains(property)) {
				illegal.add(property);
			}
		});
		return illegal;
	}




	/**
	 * Checks that no property type was added more than once.
	 * Throws a validation exception if the list contains duplicate properties.
	 *
	 * @param properties the given properties to check
	 */
	private static void checkDuplicates(final List<Class<? extends SuiProperty>> properties) {
		final Set<Class<? extends SuiProperty>> items = new HashSet<>();
		final List<Class<? extends SuiProperty>> duplicates = properties.stream()
				.filter(p -> !items.add(p))
				.collect(Collectors.toList());
		if (!duplicates.isEmpty()) {
			final String messageList = duplicates.stream().map(Object::toString).collect(Collectors.joining(", "));
			Validations.STATE.fail().exception("Duplicate properties: [" + messageList + "]");
		}
	}




	/**
	 * Check if any properties in the given list conflict with any other of the properties.
	 * Throws a validation exception if a conflict was found.
	 *
	 * @param properties the properties to check
	 */
	private static void checkConflicting(final Set<Class<? extends SuiProperty>> properties) {
		if (properties.contains(ItemProperty.class) && properties.contains(ItemListProperty.class)) {
			Validations.STATE.fail().exception("Conflicting Properties: \"ItemProperty\" and \"ItemListProperty\"");
		}
	}

}
