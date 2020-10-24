package de.ruegnerlukas.simpleapplication.simpleui.core.windows;

import javafx.stage.Stage;

public interface WindowManager {


	/**
	 * Opens a new window
	 *
	 * @param windowInformation information about the window and its behaviour
	 */
	void openNew(WindowInformation windowInformation);

	/**
	 * Closes the given open window/stage
	 * @param stage the window/stage to close
	 */
	void close(Stage stage);

}
