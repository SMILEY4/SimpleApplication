package de.ruegnerlukas.simpleapplication.core.presentation.uimodule;

import de.ruegnerlukas.simpleapplication.common.events.ListenableEventSource;
import de.ruegnerlukas.simpleapplication.common.events.TriggerableEventSource;
import javafx.scene.layout.Pane;

import java.util.Map;

public interface ModuleView {


	/**
	 * Initializes this view.
	 *
	 * @param moduleRoot the root pane of the module.
	 *                   All other ui elements including elements from fxml-files get added to this pane.
	 */
	void initializeView(Pane moduleRoot);

	/**
	 * @return a map of all events that this view exposes to the controller of the same module.
	 */
	Map<String, ListenableEventSource<?>> getEventEndpoints();

	/**
	 * @return a map of all functions that this view exposes to the controller of the same module.
	 */
	Map<String, TriggerableEventSource<?>> getFunctionEndpoints();

}
