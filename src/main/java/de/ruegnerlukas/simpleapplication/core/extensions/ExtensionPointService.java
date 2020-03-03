package de.ruegnerlukas.simpleapplication.core.extensions;


import java.util.Optional;

/**
 * The extension point service manages {@link ExtensionPoint}s.
 * <p>
 * Any {@link de.ruegnerlukas.simpleapplication.core.plugins.Plugin} or system can provide extension points
 * by registering them in this service.
 * Registered extension points can then be requested by their specified id.
 */
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
	 * Pass the given data to the extension point with the given id.
	 *
	 * @param id   the id of the extension point
	 * @param type the type of the provided data
	 * @param data the data to provide to the extension point
	 */
	void provide(String id, Class<?> type, Object data);

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