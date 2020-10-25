package de.ruegnerlukas.simpleapplication.simpleui.core.windows;

import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;

public interface WindowManager {


	/**
	 * Opens a new window using the window manager of the {@link SuiRegistry}
	 *
	 * @param windowOpenData information about the window and its behaviour
	 */
	static void openWindow(final WindowOpenData windowOpenData) {
		SuiRegistry.get().getWindowManager().openNew(windowOpenData);
	}

	/**
	 * Closes a window with the window manager of the {@link SuiRegistry}
	 *
	 * @param windowCloseData the data about the window/stage to close
	 */
	static void closeWindow(final WindowCloseData windowCloseData) {
		SuiRegistry.get().getWindowManager().close(windowCloseData);
	}


	/**
	 * Opens a new window
	 *
	 * @param windowOpenData information about the window and its behaviour
	 */
	void openNew(WindowOpenData windowOpenData);

	/**
	 * Closes the given open window/stage
	 *
	 * @param windowCloseData the data about the window/stage to close
	 */
	void close(WindowCloseData windowCloseData);

}
