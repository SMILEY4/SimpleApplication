package de.ruegnerlukas.simpleapplication.core.presentation.module;

import javafx.scene.layout.Pane;

import java.util.Collections;
import java.util.List;

public interface ModuleView {


	/**
	 * Initializes this view and its ui components.
	 *
	 * @param root the root-pane of the module. This pane may already contain ui elements if the module was loaded with an fxml.
	 */
	void initialize(Pane root);

	/**
	 * @return a list of events (each with a unique name a its {@link UIExtensionScope}) exposed by this view
	 */
	default List<ExposedEvent> getExposedEvents() {
		return Collections.emptyList();
	}

	/**
	 * @return a list of commands (each with a unique name a its {@link UIExtensionScope}) exposed by this view
	 */
	default List<ExposedCommand> getExposedCommands() {
		return Collections.emptyList();
	}


	/**
	 * A {@link ModuleView} that does nothing
	 */
	class EmptyView implements ModuleView {


		@Override
		public void initialize(final Pane root) {
		}

	}

}
