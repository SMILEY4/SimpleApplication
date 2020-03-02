package de.ruegnerlukas.simpleapplication.core.presentation;

import de.ruegnerlukas.simpleapplication.common.events.EventListener;
import de.ruegnerlukas.simpleapplication.common.events.EventPackage;
import de.ruegnerlukas.simpleapplication.common.events.EventSource;
import de.ruegnerlukas.simpleapplication.common.events.ListenableEventSourceGroup;
import de.ruegnerlukas.simpleapplication.common.events.TriggerableEventSourceGroup;
import de.ruegnerlukas.simpleapplication.common.events.specializedevents.EventBusListener;
import de.ruegnerlukas.simpleapplication.common.events.specializedevents.StringEvent;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ProviderService;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ExposedCommand;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ExposedEvent;
import de.ruegnerlukas.simpleapplication.core.presentation.module.Module;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ModuleController;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ModuleView;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.testfx.framework.junit.ApplicationTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ModuleTest extends ApplicationTest {


	private Module module;

	private ModuleController controller;

	private ModuleView view;

	private final String EVENT_VIEW_GLOBAL = "event.view.global";

	private final String EVENT_CONTROLLER_GLOBAL = "event.ctrl.global";

	private EventSource<String> eventSourceViewGlobal;

	private EventSource<String> eventSourceControllerGlobal;


	private final String COMMAND_VIEW_GLOBAL = "cmd.view.global";

	private final String COMMAND_CONTROLLER_GLOBAL = "cmd.ctrl.global";

	private EventSource<String> commandSourceViewGlobal;

	private EventSource<String> commandSourceControllerGlobal;




	@Override
	public void start(Stage stage) {

		ProviderService.cleanup();
		ProviderService.registerFactory(new InstanceFactory<>(EventService.class) {
			@Override
			public EventService buildObject() {
				return new EventService();
			}
		});

		commandSourceViewGlobal = new StringEvent.StringEventSource();
		final ExposedCommand commandViewInternal = ExposedCommand.internal("cmd.view.internal", new EventSource<>());
		final ExposedCommand commandViewLocal = ExposedCommand.local("cmd.view.local", new EventSource<>());
		final ExposedCommand commandViewGlobal = ExposedCommand.global(COMMAND_VIEW_GLOBAL, commandSourceViewGlobal);

		eventSourceViewGlobal = new StringEvent.StringEventSource();
		final ExposedEvent eventViewInternal = ExposedEvent.internal("event.view.internal", new EventSource<>());
		final ExposedEvent eventViewLocal = ExposedEvent.local("event.view.local", new EventSource<>());
		final ExposedEvent eventViewGlobal = ExposedEvent.global(EVENT_VIEW_GLOBAL, eventSourceViewGlobal);

		view = Mockito.mock(ModuleView.class);
		when(view.getExposedCommands()).thenReturn(List.of(commandViewInternal, commandViewLocal, commandViewGlobal));
		when(view.getExposedEvents()).thenReturn(List.of(eventViewInternal, eventViewLocal, eventViewGlobal));

		commandSourceControllerGlobal = new StringEvent.StringEventSource();
		final ExposedCommand commandCtrlInternal = ExposedCommand.internal("cmd.ctrl.internal", new EventSource<>());
		final ExposedCommand commandCtrlLocal = ExposedCommand.local("cmd.ctrl.local", new EventSource<>());
		final ExposedCommand commandCtrlGlobal = ExposedCommand.global(COMMAND_CONTROLLER_GLOBAL, commandSourceControllerGlobal);

		eventSourceControllerGlobal = new StringEvent.StringEventSource();
		final ExposedEvent eventCtrlInternal = ExposedEvent.internal("event.ctrl.internal", new EventSource<>());
		final ExposedEvent eventCtrlLocal = ExposedEvent.local("event.ctrl.local", new EventSource<>());
		final ExposedEvent eventCtrlGlobal = ExposedEvent.global(EVENT_CONTROLLER_GLOBAL, eventSourceControllerGlobal);

		controller = Mockito.mock(ModuleController.class);
		when(controller.getExposedCommands()).thenReturn(List.of(commandCtrlInternal, commandCtrlLocal, commandCtrlGlobal));
		when(controller.getExposedEvents()).thenReturn(List.of(eventCtrlInternal, eventCtrlLocal, eventCtrlGlobal));

		module = new Module(view, controller);
		stage.setScene(new Scene(module, 100, 100));
		stage.show();
	}




	@Test
	public void testInternalModuleStructure() {

		verify(view).initialize(any(Pane.class));

		final ArgumentCaptor<ListenableEventSourceGroup> eventCaptor = ArgumentCaptor.forClass(ListenableEventSourceGroup.class);
		final ArgumentCaptor<TriggerableEventSourceGroup> commandCaptor = ArgumentCaptor.forClass(TriggerableEventSourceGroup.class);
		verify(controller).initialize(eventCaptor.capture(), commandCaptor.capture());

		final ListenableEventSourceGroup eventGroup = eventCaptor.getValue();
		assertThat(eventGroup).isNotNull();
		assertThat(eventGroup.find("event.view.internal")).isNotNull();
		assertThat(eventGroup.find("event.view.local")).isNotNull();
		assertThat(eventGroup.find("event.view.global")).isNotNull();

		final TriggerableEventSourceGroup commandGroup = commandCaptor.getValue();
		assertThat(commandGroup).isNotNull();
		assertThat(commandGroup.find("cmd.view.internal")).isNotNull();
		assertThat(commandGroup.find("cmd.view.local")).isNotNull();
		assertThat(commandGroup.find("cmd.view.global")).isNotNull();

	}




	@Test
	public void testLocalModuleStructure() {

		final ListenableEventSourceGroup eventGroup = module.getEvents();
		assertThat(eventGroup).isNotNull();
		assertThat(eventGroup.find("event.view.local")).isNotNull();
		assertThat(eventGroup.find("event.view.global")).isNotNull();
		assertThat(eventGroup.find("event.ctrl.local")).isNotNull();
		assertThat(eventGroup.find("event.ctrl.global")).isNotNull();

		final TriggerableEventSourceGroup commandGroup = module.getCommands();
		assertThat(commandGroup).isNotNull();
		assertThat(commandGroup.find("cmd.view.local")).isNotNull();
		assertThat(commandGroup.find("cmd.view.global")).isNotNull();
		assertThat(commandGroup.find("cmd.ctrl.local")).isNotNull();
		assertThat(commandGroup.find("cmd.ctrl.global")).isNotNull();
	}




	@Test
	public void testGlobalEventsView() {

		final EventService eventService = new Provider<>(EventService.class).get();

		final EventBusListener<String> listener = Mockito.mock(EventBusListener.class);
		eventService.subscribe(EVENT_VIEW_GLOBAL, listener);

		eventSourceViewGlobal.trigger("Test String");
		ArgumentCaptor<EventPackage<String>> captor = ArgumentCaptor.forClass(EventPackage.class);
		verify(listener).onEvent(captor.capture());

		EventPackage<String> eventPackage = captor.getValue();
		assertThat(eventPackage).isNotNull();
		assertThat(eventPackage.getChannels()).containsExactlyInAnyOrder(EVENT_VIEW_GLOBAL);
		assertThat(eventPackage.getEvent()).isEqualTo("Test String");
	}




	@Test
	public void testGlobalEventsController() {

		final EventService eventService = new Provider<>(EventService.class).get();

		final EventBusListener<String> listener = Mockito.mock(EventBusListener.class);
		eventService.subscribe(EVENT_CONTROLLER_GLOBAL, listener);

		eventSourceControllerGlobal.trigger("Test String");
		ArgumentCaptor<EventPackage<String>> captor = ArgumentCaptor.forClass(EventPackage.class);
		verify(listener).onEvent(captor.capture());

		EventPackage<String> eventPackage = captor.getValue();
		assertThat(eventPackage).isNotNull();
		assertThat(eventPackage.getChannels()).containsExactlyInAnyOrder(EVENT_CONTROLLER_GLOBAL);
		assertThat(eventPackage.getEvent()).isEqualTo("Test String");
	}




	@Test
	public void testGlobalCommandsView() {

		final EventService eventService = new Provider<>(EventService.class).get();

		final EventListener<String> listener = Mockito.mock(EventListener.class);
		commandSourceViewGlobal.subscribe(listener);

		eventService.publish(COMMAND_VIEW_GLOBAL, new EventPackage<>("Test String"));
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(listener).onEvent(captor.capture());
		assertThat(captor.getValue()).isNotNull();
		assertThat(captor.getValue()).isEqualTo("Test String");
	}




	@Test
	public void testGlobalCommandsController() {

		final EventService eventService = new Provider<>(EventService.class).get();

		final EventListener<String> listener = Mockito.mock(EventListener.class);
		commandSourceControllerGlobal.subscribe(listener);

		eventService.publish(COMMAND_CONTROLLER_GLOBAL, new EventPackage<>("Test String"));
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(listener).onEvent(captor.capture());
		assertThat(captor.getValue()).isNotNull();
		assertThat(captor.getValue()).isEqualTo("Test String");
	}

}
