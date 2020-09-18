package de.ruegnerlukas.simpleapplication.core.presentation;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ProviderService;
import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.events.EventServiceImpl;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import de.ruegnerlukas.simpleapplication.core.presentation.style.StyleService;
import de.ruegnerlukas.simpleapplication.core.presentation.style.StyleServiceImpl;
import de.ruegnerlukas.simpleapplication.core.presentation.views.EventClosePopup;
import de.ruegnerlukas.simpleapplication.core.presentation.views.EventOpenPopup;
import de.ruegnerlukas.simpleapplication.core.presentation.views.EventShowView;
import de.ruegnerlukas.simpleapplication.core.presentation.views.PopupConfiguration;
import de.ruegnerlukas.simpleapplication.core.presentation.views.View;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewServiceImpl;
import de.ruegnerlukas.simpleapplication.core.presentation.views.WindowHandle;
import de.ruegnerlukas.simpleapplication.core.presentation.views.WindowHandleData;
import javafx.application.Platform;
import javafx.geometry.Dimension2D;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewServiceTest extends ApplicationTest {


	private final String STARTUP_VIEW_ID = "view.test";

	private ViewService viewService;

	private EventService eventService;

	private StyleService styleService;




	@Override
	public void start(Stage stage) {

		eventService = eventService();
		styleService = styleService();

		ProviderService.cleanup();
		ProviderService.registerFactory(new InstanceFactory<>(EventService.class) {
			@Override
			public EventService buildObject() {
				return eventService;
			}
		});
		ProviderService.registerFactory(new InstanceFactory<>(StyleService.class) {
			@Override
			public StyleService buildObject() {
				return styleService;
			}
		});

		final View view = view(STARTUP_VIEW_ID);
		viewService = new ViewServiceImpl();
		viewService.initialize(stage, true, view);
		clearEvents();
	}




	@Test
	public void testInitializeWithView() {
		assertThat(viewService.getPrimaryWindowHandle()).isNotNull();
		assertThat(viewService.getWindowHandles(STARTUP_VIEW_ID)).isNotNull();
		assertThat(viewService.getWindowHandles(STARTUP_VIEW_ID)).hasSize(1);
		assertThat(viewService.getWindowHandles(STARTUP_VIEW_ID).get(0).getHandleId()).isEqualTo(WindowHandle.ID_PRIMARY);
		assertThat(viewService.getWindowHandles(STARTUP_VIEW_ID).get(0).getViewId()).isEqualTo(STARTUP_VIEW_ID);
		assertThat(viewService.findView(STARTUP_VIEW_ID)).isNotNull();
	}




	@Test
	public void testRegisterViews() {

		final View view = view("test.view.register");
		assertThat(viewService.findView(view.getId())).isNotPresent();

		viewService.registerView(view);
		assertThat(viewService.findView(view.getId())).isPresent();
		assertThat(viewService.findView(view.getId())).hasValue(view);
		assertThat(viewService.getWindowHandles(view.getId())).isEmpty();

		viewService.deregisterView(view.getId());
		assertThat(viewService.findView(view.getId())).isNotPresent();
	}




	@Test
	public void testShowViewPrimary() {
		Platform.runLater(() -> {
			final View view = view("test.view.show.primary");
			viewService.registerView(view);
			assertThat(viewService.findView(view.getId())).isPresent();
			final WindowHandle handle = viewService.showView(view.getId());
			assertWindowHandle(handle, view);

			assertEvent(Channel.type(EventShowView.class));
			Optional<Publishable> eventOpen = getEventAny(Channel.type(EventShowView.class));
			assertThat(eventOpen).isPresent();
			eventOpen.ifPresent(publishable -> {
				EventShowView event = (EventShowView) publishable;
				assertThat(event.getPrevViewId()).isEqualTo(STARTUP_VIEW_ID);
				assertThat(event.getViewId()).isEqualTo(view.getId());
				assertThat(event.getWindowHandle()).isEqualTo(handle);
			});

		});
	}




	@Test
	public void testShowViewReplace() {
		Platform.runLater(() -> {

			final View viewPrev = view("test.view.show.replace.prev");
			viewService.registerView(viewPrev);
			viewService.showView(viewPrev.getId());
			clearEvents();

			final View view = view("test.view.show.replace");
			viewService.registerView(view);
			assertThat(viewService.findView(view.getId())).isPresent();

			final WindowHandle handlePrimary = viewService.getPrimaryWindowHandle();
			final WindowHandle handle = viewService.showView(view.getId(), handlePrimary);
			assertWindowHandle(handle, handlePrimary.getHandleId(), view);

			assertEvent(Channel.type(EventShowView.class));
			Optional<Publishable> eventOpen = getEventAny(Channel.type(EventShowView.class));
			assertThat(eventOpen).isPresent();
			eventOpen.ifPresent(publishable -> {
				EventShowView event = (EventShowView) publishable;
				assertThat(event.getPrevViewId()).isEqualTo(viewPrev.getId());
				assertThat(event.getViewId()).isEqualTo(view.getId());
				assertThat(event.getWindowHandle()).isEqualTo(handle);
			});
		});
	}




	@Test
	public void testPopup() {
		Platform.runLater(() -> {
			final View view = view("test.view.popup");
			viewService.registerView(view);
			assertThat(viewService.findView(view.getId())).isPresent();

			// open popup
			final PopupConfiguration popupConfiguration = popupConfig(viewService.getPrimaryWindowHandle());
			final WindowHandle handle = viewService.popupView(view.getId(), popupConfiguration);
			assertThat(handle).isNotNull();
			assertThat(handle.getHandleId()).isNotEqualTo(WindowHandle.ID_PRIMARY);
			assertThat(handle.getViewId()).isEqualTo(view.getId());
			assertThat(viewService.getWindowHandles(view.getId())).hasSize(1);
			assertThat(viewService.getWindowHandles(view.getId()).get(0).getHandleId()).isEqualTo(handle.getHandleId());

			assertEvent(Channel.type(EventOpenPopup.class));
			Optional<Publishable> eventOpen = getEventAny(Channel.type(EventOpenPopup.class));
			assertThat(eventOpen).isPresent();
			eventOpen.ifPresent(publishable -> {
				EventOpenPopup event = (EventOpenPopup) publishable;
				assertThat(event.getViewId()).isEqualTo(view.getId());
				assertThat(event.getWindowHandle()).isEqualTo(handle);
			});

			// close popup
			viewService.closePopup(handle);
			assertThat(viewService.getWindowHandles(view.getId())).hasSize(0);

			assertEvent(Channel.type(EventClosePopup.class));
			Optional<Publishable> eventClose = getEventAny(Channel.type(EventClosePopup.class));
			assertThat(eventClose).isPresent();
			eventClose.ifPresent(publishable -> {
				EventClosePopup event = (EventClosePopup) publishable;
				assertThat(event.getViewId()).isEqualTo(view.getId());
				assertThat(event.getWindowHandle()).isEqualTo(handle);
			});
		});
	}




	@Test
	public void testPupupsSameView() {
		Platform.runLater(() -> {

			final View view = view("test.view.popup");
			viewService.registerView(view);
			assertThat(viewService.findView(view.getId())).isPresent();

			// open popups
			final PopupConfiguration popupConfigurationA = popupConfig(viewService.getPrimaryWindowHandle());
			final WindowHandle handleA = viewService.popupView(view.getId(), popupConfigurationA);

			final PopupConfiguration popupConfigurationB = popupConfig(viewService.getPrimaryWindowHandle());
			final WindowHandle handleB = viewService.popupView(view.getId(), popupConfigurationB);

			assertEvent(Channel.type(EventOpenPopup.class));
			List<Publishable> eventsOpen = getEventPackages(Channel.type(EventOpenPopup.class));
			assertThat(eventsOpen).hasSize(2);

			Optional<Publishable> eventOpenA = eventsOpen.stream().filter(event -> ((EventOpenPopup) event).getWindowHandle() == handleA).findAny();
			assertThat(eventOpenA).isPresent();
			eventOpenA.ifPresent(publishable -> {
				EventOpenPopup event = (EventOpenPopup) publishable;
				assertThat(event.getViewId()).isEqualTo(view.getId());
				assertThat(event.getWindowHandle()).isEqualTo(handleA);
			});

			Optional<Publishable> eventOpenB = eventsOpen.stream().filter(event -> ((EventOpenPopup) event).getWindowHandle() == handleB).findAny();
			assertThat(eventOpenB).isPresent();
			eventOpenB.ifPresent(publishable -> {
				EventOpenPopup event = (EventOpenPopup) publishable;
				assertThat(event.getViewId()).isEqualTo(view.getId());
				assertThat(event.getWindowHandle()).isEqualTo(handleB);
			});

			// clear events
			clearEvents();

			// close pupups
			viewService.closePopup(handleA);
			viewService.closePopup(handleB);
			assertThat(viewService.getWindowHandles(view.getId())).hasSize(0);

			assertEvent(Channel.type(EventClosePopup.class));
			List<Publishable> eventsClose = getEventPackages(Channel.type(EventClosePopup.class));
			assertThat(eventsClose).hasSize(2);

			Optional<Publishable> eventCloseA = eventsClose.stream().filter(event -> ((EventClosePopup) event).getWindowHandle() == handleA).findAny();
			assertThat(eventCloseA).isPresent();
			eventCloseA.ifPresent(publishable -> {
				EventClosePopup event = (EventClosePopup) publishable;
				assertThat(event.getViewId()).isEqualTo(view.getId());
				assertThat(event.getWindowHandle()).isEqualTo(handleA);
			});

			Optional<Publishable> eventCloseB = eventsClose.stream().filter(event -> ((EventClosePopup) event).getWindowHandle() == handleB).findAny();
			assertThat(eventCloseB).isPresent();
			eventCloseB.ifPresent(publishable -> {
				EventClosePopup event = (EventClosePopup) publishable;
				assertThat(event.getViewId()).isEqualTo(view.getId());
				assertThat(event.getWindowHandle()).isEqualTo(handleB);
			});

		});
	}




	@Test
	public void testStyles() {
		Platform.runLater(() -> {

			styleService.createFromString("style.a", "-fx-background-color: red");
			styleService.createFromString("style.b", "-fx-background-color: blue");

			final View viewA = view("test.view.styles.a", "style.a");
			final View viewB = view("test.view.styles.b", "style.b");
			viewService.registerView(viewA);
			viewService.registerView(viewB);

			final WindowHandle windowHandleA = viewService.showView(viewA.getId());
			assertThat(windowHandleA.getCurrentRootNode().getStyle().replace(" ", "")).isEqualTo("-fx-background-color:red;");
			assertThat(styleService.getAppliedStyleNames(windowHandleA.getCurrentRootNode())).containsExactlyInAnyOrder("style.a");

			final WindowHandle windowHandleB = viewService.showView(viewB.getId());
			assertThat(windowHandleB.getCurrentRootNode().getStyle().replace(" ", "")).isEqualTo("-fx-background-color:blue;");
			assertThat(styleService.getAppliedStyleNames(windowHandleB.getCurrentRootNode())).containsExactlyInAnyOrder("style.b");

		});
	}




	@Test
	public void testRootStyles() {
		Platform.runLater(() -> {

			styleService.createFromString("style.a", "-fx-background-color: red");
			styleService.createFromString("style.b", "-fx-background-color: blue");
			styleService.setRootStyle("style.a", true);

			final View viewA = view("test.view.styles.a");
			final View viewB = view("test.view.styles.b");
			viewService.registerView(viewA);
			viewService.registerView(viewB);

			final WindowHandle windowHandleA = viewService.showView(viewA.getId());
			assertThat(windowHandleA.getCurrentRootNode().getStyle().replace(" ", "")).isEqualTo("-fx-background-color:red;");
			assertThat(styleService.getAppliedStyleNames(windowHandleA.getCurrentRootNode())).containsExactlyInAnyOrder("style.a");

			final WindowHandle windowHandleB = viewService.showView(viewB.getId());
			assertThat(windowHandleB.getCurrentRootNode().getStyle().replace(" ", "")).isEqualTo("-fx-background-color:red;");
			assertThat(styleService.getAppliedStyleNames(windowHandleB.getCurrentRootNode())).containsExactlyInAnyOrder("style.a");

			styleService.setRootStyle("style.b", true);
			assertThat(windowHandleB.getCurrentRootNode().getStyle().replace(" ", "")).isEqualTo("-fx-background-color:red;-fx-background-color:blue;");
			assertThat(styleService.getAppliedStyleNames(windowHandleB.getCurrentRootNode())).containsExactlyInAnyOrder("style.a", "style.b");
		});
	}




	private void assertWindowHandle(final WindowHandle handle, final View expectedView) {
		assertWindowHandle(handle, expectedView.getId());
	}




	private void assertWindowHandle(final WindowHandle handle, final String expectedViewId) {
		assertWindowHandle(handle, WindowHandle.ID_PRIMARY, expectedViewId);
	}




	private void assertWindowHandle(final WindowHandle handle, final String expectedHandleId, final View expectedView) {
		assertWindowHandle(handle, expectedHandleId, expectedView.getId());
	}




	private void assertWindowHandle(final WindowHandle handle, final String expectedHandleId, final String expectedViewId) {
		assertThat(handle).isNotNull();
		assertThat(handle.getHandleId()).isEqualTo(expectedHandleId);
		assertThat(handle.getViewId()).isEqualTo(expectedViewId);
	}




	private PopupConfiguration popupConfig(final WindowHandle parent) {
		return PopupConfiguration.builder()
				.parent(parent)
				.wait(false)
				.build();
	}




	private View view(final String id) {
		return View.builder()
				.id(id)
				.size(new Dimension2D(100, 10))
				.title(id)
				.dataFactory(() -> new WindowHandleData() {
					@Override
					public Parent getNode() {
						return new Pane();
					}
					@Override
					public void dispose() {

					}
				})
				.icon(Resource.internal("testResources/icon.png"))
				.build();
	}




	private View view(final String id, String... styles) {
		return View.builder()
				.id(id)
				.size(new Dimension2D(100, 10))
				.title(id)
				.dataFactory(() -> Pane::new)
				.styles(Set.of(styles))
				.build();
	}




	private List<Publishable> events = new ArrayList<>();




	private EventService eventService() {
		final EventService eventService = new EventServiceImpl();
		eventService.subscribe(eventPackage -> events.add(eventPackage));
		return eventService;
	}




	private StyleService styleService() {
		return new StyleServiceImpl();
	}




	private void assertEvent(final Channel channel) {
		assertThat(getEventPackages(channel)).isNotEmpty();
	}




	private void clearEvents() {
		events.clear();
	}




	private List<Publishable> getEventPackages(final Channel channel) {
		return events.stream()
				.filter(e -> e.getChannel().equals(channel))
				.collect(Collectors.toList());
	}




	private Optional<Publishable> getEventAny(final Channel channel) {
		return events.stream()
				.filter(e -> e.getChannel().equals(channel))
				.findAny();
	}


}
