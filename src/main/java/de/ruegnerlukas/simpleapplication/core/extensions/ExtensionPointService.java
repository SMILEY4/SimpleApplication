package de.ruegnerlukas.simpleapplication.core.extensions;

import java.util.Optional;

public interface ExtensionPointService {


	/**
	 * Registers the given {@link ExtensionPoint}.
	 *
	 * @param extensionPoint the extension point to register
	 */
	void register(ExtensionPoint extensionPoint);


	/**
	 * De-registers the {@link ExtensionPoint} with the given id.
	 *
	 * @param id the id of the extension point to deregister
	 */
	void deregister(String id);

	/**
	 * Searches for an {@link ExtensionPoint} with the given id.
	 *
	 * @param id the id of the extension point
	 * @return an {@link Optional} with the {@link ExtensionPoint}
	 */
	Optional<ExtensionPoint> find(String id);

	/**
	 * Searches for an {@link ExtensionPoint} with the given id.
	 * If no extension point with the given id exists, a dummy without functionality will be returned.
	 *
	 * @param id the id of the extension point
	 * @return the extension point with the given id or a dummy.
	 */
	ExtensionPoint findOrDummy(String id);


}
