package de.ruegnerlukas.simpleapplication.core.presentation;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
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
import de.ruegnerlukas.simpleapplication.core.presentation.style.StyleService;
import de.ruegnerlukas.simpleapplication.core.presentation.style.StyleServiceImpl;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.testfx.framework.junit.ApplicationTest;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.core.events.PublishableEvent.PublishableEventListener;
import static de.ruegnerlukas.simpleapplication.core.events.PublishableEvent.PublishableEventSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ModuleTest extends ApplicationTest {


	private Stage stage;

	private Module module;

	private ModuleController controller;

	private ModuleView view;

	private final String EVENT_VIEW_GLOBAL = "event.view.global";

	private final String EVENT_CONTROLLER_GLOBAL = "event.ctrl.global";

	private PublishableEventSource eventSourceViewGlobal;

	private PublishableEventSource eventSourceControllerGlobal;


	private final String COMMAND_VIEW_GLOBAL = "cmd.view.global";

	private final String COMMAND_CONTROLLER_GLOBAL = "cmd.ctrl.global";

	private PublishableEventSource commandSourceViewGlobal;

	private PublishableEventSource commandSourceControllerGlobal;




	@Override
	public void start(Stage stage) {
		this.stage = stage;

		ProviderService.cleanup();
		ProviderService.registerFactory(new InstanceFactory<>(EventService.class) {
			@Override
			public EventService buildObject() {
				return new EventServiceImpl();
			}
		});
		ProviderService.registerFactory(new InstanceFactory<>(StyleService.class) {
			@Override
			public StyleService buildObject() {
				return spy(new StyleServiceImpl());
			}
		});

		commandSourceViewGlobal = new PublishableEventSource(COMMAND_VIEW_GLOBAL);
		final ExposedCommand commandViewInternal = ExposedCommand.internal(new PublishableEventSource("cmd.view.internal"));
		final ExposedCommand commandViewLocal = ExposedCommand.local(new PublishableEventSource("cmd.view.local"));
		final ExposedCommand commandViewGlobal = ExposedCommand.global(commandSourceViewGlobal);

		eventSourceViewGlobal = new PublishableEventSource(EVENT_VIEW_GLOBAL);
		final ExposedEvent eventViewInternal = ExposedEvent.internal(new PublishableEventSource("event.view.internal"));
		final ExposedEvent eventViewLocal = ExposedEvent.local(new PublishableEventSource("event.view.local"));
		final ExposedEvent eventViewGlobal = ExposedEvent.global(eventSourceViewGlobal);

		view = mock(ModuleView.class);
		when(view.getExposedCommands()).thenReturn(List.of(commandViewInternal, commandViewLocal, commandViewGlobal));
		when(view.getExposedEvents()).thenReturn(List.of(eventViewInternal, eventViewLocal, eventViewGlobal));

		commandSourceControllerGlobal = new PublishableEventSource(COMMAND_CONTROLLER_GLOBAL);
		final ExposedCommand commandCtrlInternal = ExposedCommand.internal(new PublishableEventSource("cmd.ctrl.internal"));
		final ExposedCommand commandCtrlLocal = ExposedCommand.local(new PublishableEventSource("cmd.ctrl.local"));
		final ExposedCommand commandCtrlGlobal = ExposedCommand.global(commandSourceControllerGlobal);

		eventSourceControllerGlobal = new PublishableEventSource(EVENT_CONTROLLER_GLOBAL);
		final ExposedEvent eventCtrlInternal = ExposedEvent.internal(new PublishableEventSource("event.ctrl.internal"));
		final ExposedEvent eventCtrlLocal = ExposedEvent.local(new PublishableEventSource("event.ctrl.local"));
		final ExposedEvent eventCtrlGlobal = ExposedEvent.global(eventSourceControllerGlobal);

		controller = mock(ModuleController.class);
		when(controller.getExposedCommands()).thenReturn(List.of(commandCtrlInternal, commandCtrlLocal, commandCtrlGlobal));
		when(controller.getExposedEvents()).thenReturn(List.of(eventCtrlInternal, eventCtrlLocal, eventCtrlGlobal));

		module = new Module(view, controller);
		stage.setScene(new Scene(module, 100, 100));
		stage.show();
	}




	@Test
	public void testInternalModuleStructure() {

		verify(view).initialize(any(AnchorPane.class));

		final ArgumentCaptor<ListenableEventSourceGroup> eventCaptor = ArgumentCaptor.forClass(ListenableEventSourceGroup.class);
		final ArgumentCaptor<TriggerableEventSourceGroup> commandCaptor = ArgumentCaptor.forClass(TriggerableEventSourceGroup.class);
		verify(controller).initialize(eventCaptor.capture(), commandCaptor.capture());

		final ListenableEventSourceGroup eventGroup = eventCaptor.getValue();
		assertThat(eventGroup).isNotNull();
		assertThat(eventGroup.find(Channel.name("event.view.internal"))).isNotNull();
		assertThat(eventGroup.find(Channel.name("event.view.local"))).isNotNull();
		assertThat(eventGroup.find(Channel.name("event.view.global"))).isNotNull();

		final TriggerableEventSourceGroup commandGroup = commandCaptor.getValue();
		assertThat(commandGroup).isNotNull();
		assertThat(commandGroup.find(Channel.name("cmd.view.internal"))).isNotNull();
		assertThat(commandGroup.find(Channel.name("cmd.view.local"))).isNotNull();
		assertThat(commandGroup.find(Channel.name("cmd.view.global"))).isNotNull();

	}




	@Test
	public void testLocalModuleStructure() {

		final ListenableEventSourceGroup eventGroup = module.getEvents();
		assertThat(eventGroup).isNotNull();
		assertThat(eventGroup.find(Channel.name("event.view.local"))).isNotNull();
		assertThat(eventGroup.find(Channel.name("event.view.global"))).isNotNull();
		assertThat(eventGroup.find(Channel.name("event.ctrl.local"))).isNotNull();
		assertThat(eventGroup.find(Channel.name("event.ctrl.global"))).isNotNull();

		final TriggerableEventSourceGroup commandGroup = module.getCommands();
		assertThat(commandGroup).isNotNull();
		assertThat(commandGroup.find(Channel.name("cmd.view.local"))).isNotNull();
		assertThat(commandGroup.find(Channel.name("cmd.view.global"))).isNotNull();
		assertThat(commandGroup.find(Channel.name("cmd.ctrl.local"))).isNotNull();
		assertThat(commandGroup.find(Channel.name("cmd.ctrl.global"))).isNotNull();
	}




	@Test
	public void testGlobalEventsView() {

		final EventService eventService = new Provider<>(EventService.class).get();

		final PublishableEventListener listener = mock(PublishableEventListener.class);
		eventService.subscribe(Channel.name(EVENT_VIEW_GLOBAL), listener);

		eventSourceViewGlobal.trigger(new StringEvent("Test String"));
		ArgumentCaptor<StringEvent> captor = ArgumentCaptor.forClass(StringEvent.class);
		verify(listener).onEvent(captor.capture());

		StringEvent publishable = captor.getValue();
		assertThat(publishable).isNotNull();
		assertThat(publishable.getChannel()).isEqualTo(Channel.name(EVENT_VIEW_GLOBAL));
		assertThat(publishable.getValue()).isEqualTo("Test String");
	}




	@Test
	public void testGlobalEventsController() {

		final EventService eventService = new Provider<>(EventService.class).get();

		final PublishableEventListener listener = mock(PublishableEventListener.class);
		eventService.subscribe(Channel.name(EVENT_CONTROLLER_GLOBAL), listener);

		eventSourceControllerGlobal.trigger(new StringEvent("Test String"));
		ArgumentCaptor<StringEvent> captor = ArgumentCaptor.forClass(StringEvent.class);
		verify(listener).onEvent(captor.capture());

		StringEvent eventPackage = captor.getValue();
		assertThat(eventPackage).isNotNull();
		assertThat(eventPackage.getChannel()).isEqualTo(Channel.name(EVENT_CONTROLLER_GLOBAL));
		assertThat(eventPackage.getValue()).isEqualTo("Test String");
	}




	@Test
	public void testGlobalCommandsView() {

		final EventService eventService = new Provider<>(EventService.class).get();

		final PublishableEventListener listener = mock(PublishableEventListener.class);
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

		final PublishableEventListener listener = mock(PublishableEventListener.class);
		commandSourceControllerGlobal.subscribe(listener);

		eventService.publish(new StringEvent(COMMAND_CONTROLLER_GLOBAL, "Test String"));
		ArgumentCaptor<StringEvent> captor = ArgumentCaptor.forClass(StringEvent.class);
		verify(listener).onEvent(captor.capture());
		assertThat(captor.getValue()).isNotNull();
		assertThat(captor.getValue().getValue()).isEqualTo("Test String");
	}




	@Test
	public void testModuleStyling() {

		StyleService styleService = new Provider<>(StyleService.class).get();
		styleService.createFromString("test-style-a", "-fx-background.color: red");
		styleService.createFromString("test-style-b", "-fx-background.color: blue");

		final Module module = new Module(new ModuleView.EmptyView(), new ModuleController.EmptyController())
				.withStyles("test-style-a", "test-style-b")
				.withStyleClasses("style-class");

		assertThat(module.getStyleClass()).contains("style-class");
		verify(styleService).applyStyleTo(eq("test-style-a"), eq(module));
		verify(styleService).applyStyleTo(eq("test-style-b"), eq(module));
	}




	static class StringEvent extends Publishable {


		@Getter
		private final String value;




		public StringEvent(final String value) {
			this.value = value;
		}




		public StringEvent(final String channel, final String value) {
			this.value = value;
			setChannel(Channel.name(channel));
		}

	}


}
