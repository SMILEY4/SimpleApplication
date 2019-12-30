package de.ruegnerlukas.simpleapplication.core.presentation.uimodule;

import de.ruegnerlukas.simpleapplication.common.events.group.ListenableEventGroup;
import de.ruegnerlukas.simpleapplication.common.events.group.TriggerableEventGroup;

public interface ModuleController {


	/**
	 * Initializes this controller
	 */
	void initialize(ListenableEventGroup events, TriggerableEventGroup functions);

}
