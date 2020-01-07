package de.ruegnerlukas.simpleapplication.common.extensions;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ExtensionHandler {


	/**
	 * All registered {@link ExtensionPoint}s.
	 */
	private final Map<String, ExtensionPoint> extensionPoints = new HashMap<>();




	/**
	 * Registers the given extension point. It will not be added if another e.p. with the same id is already registered.
	 *
	 * @param extensionPoint the {@link ExtensionPoint} to add
	 */
	public void register(final ExtensionPoint extensionPoint) {
		Validations.INPUT.notNull(extensionPoint, "The extension point must not be null.");
		Validations.INPUT.notNull(extensionPoint.getId(), "The id of the extension point must not be null.");
		Validations.INPUT.containsNotKey(extensionPoints, extensionPoint.getId(),
				"An extension point with the id {} is already registered.", extensionPoint.getId());
		extensionPoints.put(extensionPoint.getId(), extensionPoint);
	}




	/**
	 * @param id the id of the requested {@link ExtensionPoint}
	 * @return the {@link ExtensionPoint} with the given id or null
	 */
	public ExtensionPoint getExtensionPoint(final String id) {
		return extensionPoints.get(id);
	}




	/**
	 * @param id the id of the requested {@link ExtensionPoint}
	 * @return an {@link java.util.Optional} with the {@link ExtensionPoint} with the given id
	 */
	public Optional<ExtensionPoint> getExtensionPointOptional(final String id) {
		return Optional.ofNullable(getExtensionPoint(id));
	}




	/**
	 * @return a list of the ids of all currently registered {@link ExtensionPoint}s
	 */
	public List<String> getRegisteredExtensionPoints() {
		return new ArrayList<>(extensionPoints.keySet());
	}


}
