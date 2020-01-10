package de.ruegnerlukas.simpleapplication.core.presentation.uimodule;

import de.ruegnerlukas.simpleapplication.common.events.ListenableEventSourceGroup;
import de.ruegnerlukas.simpleapplication.common.events.TriggerableEventSourceGroup;
import de.ruegnerlukas.simpleapplication.common.extensions.ExtensionPoint;

import java.util.List;

public interface ModuleController {


	/**
	 * Initializes this controller
	 */
	void initialize(ListenableEventSourceGroup events, TriggerableEventSourceGroup functions);

	/**
	 * @return a list of all extension points this controller exposes to the outside.
	 */
	List<ExtensionPoint> getExtensionPoints();

}
