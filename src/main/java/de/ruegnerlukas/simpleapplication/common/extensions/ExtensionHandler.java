package de.ruegnerlukas.simpleapplication.common.extensions;

import java.util.HashMap;
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
	}




	/**
	 * @param id the id of the requested {@link ExtensionPoint}
	 * @return the {@link ExtensionPoint} with the given id or null
	 */
	public ExtensionPoint getExtensionPoint(final String id) {
		return null;
	}




	/**
	 * @param id the id of the requested {@link ExtensionPoint}
	 * @return an {@link java.util.Optional} with the {@link ExtensionPoint} with the given id
	 */
	public Optional<ExtensionPoint> getExtensionPointOptional(final String id) {
		return null;
	}


}
