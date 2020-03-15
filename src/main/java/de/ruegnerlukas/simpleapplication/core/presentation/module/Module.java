package de.ruegnerlukas.simpleapplication.core.presentation.module;

import de.ruegnerlukas.simpleapplication.common.events.ListenableEventSourceGroup;
import de.ruegnerlukas.simpleapplication.common.events.TriggerableEventSource;
import de.ruegnerlukas.simpleapplication.common.events.TriggerableEventSourceGroup;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.presentation.utils.Anchors;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class Module extends AnchorPane {


	/**
	 * The provider for the event service.
	 */
	private final Provider<EventService> eventServiceProvider = new Provider<>(EventService.class);

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
	public Module(final ModuleView view, final ModuleController controller) {
		this(view, controller, null);
	}




	/**
	 * @param view       the view of this module
	 * @param controller the controller of this module
	 * @param fxmlFile   the fxml-file to use with this module (or null)
	 */
	public Module(final ModuleView view, final ModuleController controller, final Resource fxmlFile) {
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

		publishGlobalEvents(view, controller);
		publishGlobalCommands(view, controller);
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
		FXMLLoader loader = new FXMLLoader(fxmlFile.asURL());
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
	private ListenableEventSourceGroup buildInternalEventGroup(final ModuleView view) {
		final ListenableEventSourceGroup group = new ListenableEventSourceGroup();
		Optional.ofNullable(view.getExposedEvents()).orElseGet(List::of)
				.forEach(exposedEvent -> group.add(exposedEvent.getName(), exposedEvent.getEventSource()));
		return group;
	}




	/**
	 * Builds the {@link TriggerableEventSourceGroup} for internal commands.
	 *
	 * @param view the view providing the commands
	 * @return the created group
	 */
	private TriggerableEventSourceGroup buildInternalCommandGroup(final ModuleView view) {
		final TriggerableEventSourceGroup group = new TriggerableEventSourceGroup();
		Optional.ofNullable(view.getExposedCommands()).orElseGet(List::of)
				.forEach(exposedCommand -> group.add(exposedCommand.getName(), exposedCommand.getEventSource()));
		return group;
	}




	/**
	 * Builds the {@link ListenableEventSourceGroup} for local events.
	 *
	 * @param view       the view providing events
	 * @param controller the controller providing events
	 * @return the created group
	 */
	private ListenableEventSourceGroup buildLocalEventGroup(final ModuleView view, final ModuleController controller) {
		final ListenableEventSourceGroup group = new ListenableEventSourceGroup();
		final List<ExposedEvent> exposedEvents = new ArrayList<>();
		exposedEvents.addAll(Optional.ofNullable(view.getExposedEvents()).orElseGet(List::of));
		exposedEvents.addAll(Optional.ofNullable(controller.getExposedEvents()).orElseGet(List::of));
		exposedEvents.stream()
				.filter(exposedEvent -> exposedEvent.getScope().isAtLeast(UIExtensionScope.LOCAL))
				.forEach(exposedEvent -> group.add(exposedEvent.getName(), exposedEvent.getEventSource()));
		return group;
	}




	/**
	 * Builds the {@link TriggerableEventSourceGroup} for local commands.
	 *
	 * @param view       the view providing commands
	 * @param controller the controller providing commands
	 * @return the created group
	 */
	private TriggerableEventSourceGroup buildLocalCommandGroup(final ModuleView view, final ModuleController controller) {
		final TriggerableEventSourceGroup group = new TriggerableEventSourceGroup();
		final List<ExposedCommand> exposedCommands = new ArrayList<>();
		exposedCommands.addAll(Optional.ofNullable(view.getExposedCommands()).orElseGet(List::of));
		exposedCommands.addAll(Optional.ofNullable(controller.getExposedCommands()).orElseGet(List::of));
		exposedCommands.stream()
				.filter(exposedCommand -> exposedCommand.getScope().isAtLeast(UIExtensionScope.LOCAL))
				.forEach(exposedCommand -> group.add(exposedCommand.getName(), exposedCommand.getEventSource()));
		return group;
	}




	/**
	 * Makes the global events available through the {@link EventService}.
	 *
	 * @param view       the view providing events
	 * @param controller the controller providing events
	 */
	private void publishGlobalEvents(final ModuleView view, final ModuleController controller) {
		final EventService eventService = eventServiceProvider.get();
		final List<ExposedEvent> exposedEvents = new ArrayList<>();
		exposedEvents.addAll(Optional.ofNullable(view.getExposedEvents()).orElseGet(List::of));
		exposedEvents.addAll(Optional.ofNullable(controller.getExposedEvents()).orElseGet(List::of));
		exposedEvents.stream()
				.filter(exposedEvent -> exposedEvent.getScope().isAtLeast(UIExtensionScope.GLOBAL))
				.forEach(exposedEvent -> exposedEvent.getEventSource().subscribe(eventService::publish));
	}




	/**
	 * Makes the global commands available through the {@link EventService}.
	 *
	 * @param view       the view providing commands
	 * @param controller the controller providing commands
	 */
	private void publishGlobalCommands(final ModuleView view, final ModuleController controller) {
		final EventService eventService = eventServiceProvider.get();
		final List<ExposedCommand> exposedCommands = new ArrayList<>();
		exposedCommands.addAll(Optional.ofNullable(view.getExposedCommands()).orElseGet(List::of));
		exposedCommands.addAll(Optional.ofNullable(controller.getExposedCommands()).orElseGet(List::of));
		exposedCommands.stream()
				.filter(exposedCommand -> exposedCommand.getScope().isAtLeast(UIExtensionScope.GLOBAL))
				.forEach(exposedCommand -> eventService.subscribe(exposedCommand.getName(), publishable -> {
					final TriggerableEventSource eventSource = exposedCommand.getEventSource();
					eventSource.trigger(publishable);
				}));
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
