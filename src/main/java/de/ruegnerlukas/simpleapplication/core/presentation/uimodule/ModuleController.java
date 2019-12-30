package de.ruegnerlukas.simpleapplication.core.presentation.uimodule;

import de.ruegnerlukas.simpleapplication.common.events.ListenableEventGroup;
import de.ruegnerlukas.simpleapplication.common.events.TriggerableEventGroup;

public interface ModuleController {


	/**
	 * Initializes this controller
	 */
	void initialize(ListenableEventGroup events, TriggerableEventGroup functions);

}
