package de.ruegnerlukas.simpleapplication.core.presentation.module;

import de.ruegnerlukas.simpleapplication.common.events.ListenableEventSourceGroup;
import de.ruegnerlukas.simpleapplication.common.events.TriggerableEventSourceGroup;

import java.util.Collections;
import java.util.List;

public interface ModuleController {


	/**
	 * Initializes this controller.
	 *
	 * @param events   the events
	 * @param commands the commands
	 */
	void initialize(ListenableEventSourceGroup events, TriggerableEventSourceGroup commands);

	/**
	 * @return a list of events (each with a unique name a its {@link UIExtensionScope}) exposed by this controller
	 */
	default List<ExposedEvent> getExposedEvents() {
		return Collections.emptyList();
	}

	/**
	 * @return a list of commands (each with a unique name a its {@link UIExtensionScope}) exposed by this controller
	 */
	default List<ExposedCommand> getExposedCommands() {
		return Collections.emptyList();
	}


	/**
	 * A {@link ModuleController} that does nothing
	 */
	class EmptyController implements ModuleController {


		@Override
		public void initialize(final ListenableEventSourceGroup events, final TriggerableEventSourceGroup commands) {
		}

	}

}
