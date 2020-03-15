package de.ruegnerlukas.simpleapplication.core.presentation;

import de.ruegnerlukas.simpleapplication.common.events.EventListener;
import de.ruegnerlukas.simpleapplication.common.events.EventSource;
import de.ruegnerlukas.simpleapplication.common.events.ListenableEventSourceGroup;
import de.ruegnerlukas.simpleapplication.common.events.TriggerableEventSourceGroup;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ProviderService;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.events.EventServiceImpl;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ExposedCommand;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ExposedEvent;
import de.ruegnerlukas.simpleapplication.core.presentation.module.Module;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ModuleController;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ModuleView;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
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

	private EventSource<Publishable> eventSourceViewGlobal;

	private EventSource<Publishable> eventSourceControllerGlobal;


	private final String COMMAND_VIEW_GLOBAL = "cmd.view.global";

	private final String COMMAND_CONTROLLER_GLOBAL = "cmd.ctrl.global";

	private EventSource<Publishable> commandSourceViewGlobal;

	private EventSource<Publishable> commandSourceControllerGlobal;




	@Override
	public void start(Stage stage) {

		ProviderService.cleanup();
		ProviderService.registerFactory(new InstanceFactory<>(EventService.class) {
			@Override
			public EventService buildObject() {
				return new EventServiceImpl();
			}
		});

		commandSourceViewGlobal = new EventSource<>();
		final ExposedCommand commandViewInternal = ExposedCommand.internal("cmd.view.internal", new EventSource<>());
		final ExposedCommand commandViewLocal = ExposedCommand.local("cmd.view.local", new EventSource<>());
		final ExposedCommand commandViewGlobal = ExposedCommand.global(COMMAND_VIEW_GLOBAL, commandSourceViewGlobal);

		eventSourceViewGlobal = new EventSource<>();
		final ExposedEvent eventViewInternal = ExposedEvent.internal("event.view.internal", new EventSource<>());
		final ExposedEvent eventViewLocal = ExposedEvent.local("event.view.local", new EventSource<>());
		final ExposedEvent eventViewGlobal = ExposedEvent.global(EVENT_VIEW_GLOBAL, eventSourceViewGlobal);

		view = Mockito.mock(ModuleView.class);
		when(view.getExposedCommands()).thenReturn(List.of(commandViewInternal, commandViewLocal, commandViewGlobal));
		when(view.getExposedEvents()).thenReturn(List.of(eventViewInternal, eventViewLocal, eventViewGlobal));

		commandSourceControllerGlobal = new EventSource<>();
		final ExposedCommand commandCtrlInternal = ExposedCommand.internal("cmd.ctrl.internal", new EventSource<>());
		final ExposedCommand commandCtrlLocal = ExposedCommand.local("cmd.ctrl.local", new EventSource<>());
		final ExposedCommand commandCtrlGlobal = ExposedCommand.global(COMMAND_CONTROLLER_GLOBAL, commandSourceControllerGlobal);

		eventSourceControllerGlobal = new EventSource<>();
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

		final PublishableListener listener = Mockito.mock(PublishableListener.class);
		eventService.subscribe(EVENT_VIEW_GLOBAL, listener);

		eventSourceViewGlobal.trigger(new StringEvent(EVENT_VIEW_GLOBAL, "Test String"));
		ArgumentCaptor<StringEvent> captor = ArgumentCaptor.forClass(StringEvent.class);
		verify(listener).onEvent(captor.capture());

		StringEvent publishable = captor.getValue();
		assertThat(publishable).isNotNull();
		assertThat(publishable.getChannel()).isEqualTo(EVENT_VIEW_GLOBAL);
		assertThat(publishable.getValue()).isEqualTo("Test String");
	}




	@Test
	public void testGlobalEventsController() {

		final EventService eventService = new Provider<>(EventService.class).get();

		final PublishableListener listener = Mockito.mock(PublishableListener.class);
		eventService.subscribe(EVENT_CONTROLLER_GLOBAL, listener);

		eventSourceControllerGlobal.trigger(new StringEvent(EVENT_CONTROLLER_GLOBAL, "Test String"));
		ArgumentCaptor<StringEvent> captor = ArgumentCaptor.forClass(StringEvent.class);
		verify(listener).onEvent(captor.capture());

		StringEvent eventPackage = captor.getValue();
		assertThat(eventPackage).isNotNull();
		assertThat(eventPackage.getChannel()).isEqualTo(EVENT_CONTROLLER_GLOBAL);
		assertThat(eventPackage.getValue()).isEqualTo("Test String");
	}




	@Test
	public void testGlobalCommandsView() {

		final EventService eventService = new Provider<>(EventService.class).get();

		final PublishableListener listener = Mockito.mock(PublishableListener.class);
		commandSourceViewGlobal.subscribe(listener);

		eventService.publish(new StringEvent(COMMAND_VIEW_GLOBAL, "Test String"));
		ArgumentCaptor<StringEvent> captor = ArgumentCaptor.forClass(StringEvent.class);
		verify(listener).onEvent(captor.capture());
		assertThat(captor.getValue()).isNotNull();
		assertThat(captor.getValue().getValue()).isEqualTo("Test String");
	}




	@Test
	public void testGlobalCommandsController() {

		final EventService eventService = new Provider<>(EventService.class).get();

		final PublishableListener listener = Mockito.mock(PublishableListener.class);
		commandSourceControllerGlobal.subscribe(listener);

		eventService.publish(new StringEvent(COMMAND_CONTROLLER_GLOBAL, "Test String"));
		ArgumentCaptor<StringEvent> captor = ArgumentCaptor.forClass(StringEvent.class);
		verify(listener).onEvent(captor.capture());
		assertThat(captor.getValue()).isNotNull();
		assertThat(captor.getValue().getValue()).isEqualTo("Test String");
	}




	static class StringEvent extends Publishable {


		@Getter
		private final String value;




		public StringEvent(final String channel, final String value) {
			super(channel);
			this.value = value;
		}


	}






	abstract static class PublishableListener implements EventListener<Publishable> {


	}

}
