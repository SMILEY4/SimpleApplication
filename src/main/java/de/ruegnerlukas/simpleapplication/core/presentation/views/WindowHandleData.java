package de.ruegnerlukas.simpleapplication.core.presentation.views;

import javafx.scene.Parent;

public interface WindowHandleData {


	/**
	 * @return the javafx root node for the window handle.
	 */
	Parent getNode();

	/**
	 * Dispose this data.
	 * Either when the data is replaced or when the window is closed and the data no longer needed.
	 */
	default void dispose() {
		// do nothing by default
	}

}
