package de.ruegnerlukas.simpleapplication.core.presentation.module;

import de.ruegnerlukas.simpleapplication.common.events.ListenableEventSourceGroup;
import de.ruegnerlukas.simpleapplication.common.events.TriggerableEventSourceGroup;
import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.presentation.utils.Anchors;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
public class Module extends AnchorPane {


	/**
	 * The group for local events.
	 */
	private final ListenableEventSourceGroup localEvents;

	/**
	 * The group for local commands.
	 */
	private final TriggerableEventSourceGroup localCommands;




	/**
	 * @param view       the view of this module
	 * @param controller the controller of this module
	 */
	public Module(final View view, final Controller controller) {
		this(view, controller, null);
	}




	/**
	 * @param view       the view of this module
	 * @param controller the controller of this module
	 * @param fxmlFile   the fxml-file to use with this module (or null)
	 */
	public Module(final View view, final Controller controller, final Resource fxmlFile) {
		Validations.INPUT.notNull(view).exception("The view may not be null.");
		Validations.INPUT.notNull(controller).exception("The controller may not be null.");

		if (fxmlFile != null) {
			setupFXML(fxmlFile, view);
		}

		view.initialize(this);

		final ListenableEventSourceGroup internalEvents = buildInternalEventGroup(view);
		final TriggerableEventSourceGroup internalCommands = buildInternalCommandGroup(view);
		controller.initialize(internalEvents, internalCommands);

		localEvents = buildLocalEventGroup(view, controller);
		localCommands = buildLocalCommandGroup(view, controller);
	}




	/**
	 * Loads the fxml file, binds it to this module and sets up the fxml-controller.
	 *
	 * @param fxmlFile       the fxml file
	 * @param fxmlController the fxml-controller for the fxml-file
	 */
	private void setupFXML(final Resource fxmlFile, final Object fxmlController) {
		try {
			final Parent fxmlRoot = loadFXML(fxmlFile, fxmlController);
			Anchors.setAnchors(fxmlRoot, 0);
			this.getChildren().add(fxmlRoot);
		} catch (IOException e) {
			log.error("Failed to load fxml-file (" + fxmlFile.getPath() + ")", e);
		}
	}




	/**
	 * Loads the fxml-file from the given url.
	 *
	 * @param fxmlFile       the fxml-file as a {@link Resource}
	 * @param fxmlController the controller object
	 * @return the root of the fxml-file
	 * @throws IOException when something went wrong
	 */
	private Parent loadFXML(final Resource fxmlFile, final Object fxmlController) throws IOException {
		FXMLLoader loader = new javafx.fxml.FXMLLoader(fxmlFile.asURL());
		if (fxmlController != null) {
			loader.setController(fxmlController);
		}
		return loader.load();
	}




	/**
	 * Builds the {@link ListenableEventSourceGroup} for internal events.
	 *
	 * @param view the view providing the events
	 * @return the created group
	 */
	private ListenableEventSourceGroup buildInternalEventGroup(final View view) {
		final ListenableEventSourceGroup group = new ListenableEventSourceGroup();
		if (view.getExposedEvents() != null) {
			for (ExposedEvent event : view.getExposedEvents()) {
				final UIExtensionScope scope = event.getScope();
				if (List.of(UIExtensionScope.INTERNAL, UIExtensionScope.LOCAL, UIExtensionScope.GLOBAL).contains(scope)) {
					group.add(event.getName(), event.getEventSource());
				}
			}
		}
		return group;
	}




	/**
	 * Builds the {@link TriggerableEventSourceGroup} for internal commands.
	 *
	 * @param view the view providing the commands
	 * @return the created group
	 */
	private TriggerableEventSourceGroup buildInternalCommandGroup(final View view) {
		final TriggerableEventSourceGroup group = new TriggerableEventSourceGroup();
		if (view.getExposedCommands() != null) {
			for (ExposedCommand command : view.getExposedCommands()) {
				final UIExtensionScope scope = command.getScope();
				if (List.of(UIExtensionScope.INTERNAL, UIExtensionScope.LOCAL, UIExtensionScope.GLOBAL).contains(scope)) {
					group.add(command.getName(), command.getEventSource());
				}
			}
		}
		return group;
	}




	/**
	 * Builds the {@link ListenableEventSourceGroup} for local events.
	 *
	 * @param view       the view providing events
	 * @param controller the controller providing events
	 * @return the created group
	 */
	private ListenableEventSourceGroup buildLocalEventGroup(final View view, final Controller controller) {
		final ListenableEventSourceGroup group = new ListenableEventSourceGroup();
		if (view.getExposedEvents() != null) {
			for (ExposedEvent event : view.getExposedEvents()) {
				final UIExtensionScope scope = event.getScope();
				if (List.of(UIExtensionScope.LOCAL, UIExtensionScope.GLOBAL).contains(scope)) {
					group.add(event.getName(), event.getEventSource());
				}
			}
		}
		if (controller.getExposedEvents() != null) {
			for (ExposedEvent event : controller.getExposedEvents()) {
				final UIExtensionScope scope = event.getScope();
				if (List.of(UIExtensionScope.LOCAL, UIExtensionScope.GLOBAL).contains(scope)) {
					group.add(event.getName(), event.getEventSource());
				}
			}
		}
		return group;
	}




	/**
	 * Builds the {@link TriggerableEventSourceGroup} for local commands.
	 *
	 * @param view       the view providing commands
	 * @param controller the controller providing commands
	 * @return the created group
	 */
	private TriggerableEventSourceGroup buildLocalCommandGroup(final View view, final Controller controller) {
		final TriggerableEventSourceGroup group = new TriggerableEventSourceGroup();
		if (view.getExposedCommands() != null) {
			for (ExposedCommand command : view.getExposedCommands()) {
				final UIExtensionScope scope = command.getScope();
				if (List.of(UIExtensionScope.LOCAL, UIExtensionScope.GLOBAL).contains(scope)) {
					group.add(command.getName(), command.getEventSource());
				}
			}
		}
		if (controller.getExposedCommands() != null) {
			for (ExposedCommand command : controller.getExposedCommands()) {
				final UIExtensionScope scope = command.getScope();
				if (List.of(UIExtensionScope.LOCAL, UIExtensionScope.GLOBAL).contains(scope)) {
					group.add(command.getName(), command.getEventSource());
				}
			}
		}
		return group;
	}




	/**
	 * @return the local events exposed by this module
	 */
	public ListenableEventSourceGroup getEvents() {
		return localEvents;
	}




	/**
	 * @return the local commands exposed by this module
	 */
	public TriggerableEventSourceGroup getCommands() {
		return localCommands;
	}


}
