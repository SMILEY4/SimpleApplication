package de.ruegnerlukas.simpleapplication.simpleui.core.windows;

import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;

public interface SuiWindows {


	/**
	 * Opens a new window with the given config using the {@link SuiWindows}-instance from the {@link SuiRegistry}.
	 * If a window with the window-id in the given config is already open, it will not be opened again.
	 *
	 * @param config the configuration of the new window
	 */
	static void open(final WindowConfig config) {
		SuiRegistry.get().getWindows().openWindow(config);
	}


	/**
	 * Closes and disposes of the window with the given window id  using the {@link SuiWindows}-instance from the {@link SuiRegistry}.
	 *
	 * @param windowId the id of the window (id was specified in the window-config when opening it)
	 */
	static void close(final String windowId) {
		SuiRegistry.get().getWindows().closeWindow(windowId);
	}


	/**
	 * Opens a new window with the given config.
	 * If a window with the window-id in the given config is already open, it will not be opened again.
	 *
	 * @param config the configuration of the new window
	 */
	void openWindow(WindowConfig config);


	/**
	 * Closes and disposes of the window with the given window id
	 *
	 * @param windowId the id of the window (id was specified in the window-config when opening it)
	 */
	void closeWindow(String windowId);


}
