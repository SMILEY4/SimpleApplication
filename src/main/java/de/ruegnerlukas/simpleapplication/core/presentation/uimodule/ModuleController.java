package de.ruegnerlukas.simpleapplication.core.presentation.uimodule;

import de.ruegnerlukas.simpleapplication.common.events.ListenableEventSourceGroup;
import de.ruegnerlukas.simpleapplication.common.events.TriggerableEventSourceGroup;

public interface ModuleController {


	/**
	 * Initializes this controller
	 */
	void initialize(ListenableEventSourceGroup events, TriggerableEventSourceGroup functions);

}
