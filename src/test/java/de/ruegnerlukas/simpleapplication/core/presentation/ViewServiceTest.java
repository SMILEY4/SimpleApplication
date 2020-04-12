package de.ruegnerlukas.simpleapplication.core.presentation;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ProviderService;
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
import javafx.application.Platform;
import javafx.geometry.Dimension2D;
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
			assertThat(handle).isNotNull();
			assertThat(handle.getHandleId()).isEqualTo(WindowHandle.ID_PRIMARY);
			assertThat(handle.getViewId()).isEqualTo(view.getId());

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
			assertThat(handle).isNotNull();
			assertThat(handle.getHandleId()).isEqualTo(handlePrimary.getHandleId());
			assertThat(handle.getViewId()).isEqualTo(view.getId());

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
			assertThat(eventOpen).isPresent();
			eventClose.ifPresent(publishable -> {
				EventClosePopup event = (EventClosePopup) publishable;
				assertThat(event.getViewId()).isEqualTo(view.getId());
				assertThat(event.getWindowHandle()).isEqualTo(handle);
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

			assertThat(viewA.getNode().getStyle()).isEmpty();
			assertThat(viewB.getNode().getStyle()).isEmpty();
			assertThat(styleService.getAppliedStyleNames(viewA.getNode())).isEmpty();
			assertThat(styleService.getAppliedStyleNames(viewB.getNode())).isEmpty();

			viewService.showView(viewA.getId());
			assertThat(viewA.getNode().getStyle().replace(" ", "")).isEqualTo("-fx-background-color:red;");
			assertThat(viewB.getNode().getStyle()).isEmpty();
			assertThat(styleService.getAppliedStyleNames(viewA.getNode())).containsExactlyInAnyOrder("style.a");
			assertThat(styleService.getAppliedStyleNames(viewB.getNode())).isEmpty();

			viewService.showView(viewB.getId());
			assertThat(viewA.getNode().getStyle()).isEmpty();
			assertThat(viewB.getNode().getStyle().replace(" ", "")).isEqualTo("-fx-background-color:blue;");
			assertThat(styleService.getAppliedStyleNames(viewA.getNode())).isEmpty();
			assertThat(styleService.getAppliedStyleNames(viewB.getNode())).containsExactlyInAnyOrder("style.b");

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

			assertThat(viewA.getNode().getStyle()).isEmpty();
			assertThat(viewB.getNode().getStyle()).isEmpty();
			assertThat(styleService.getAppliedStyleNames(viewA.getNode())).isEmpty();
			assertThat(styleService.getAppliedStyleNames(viewB.getNode())).isEmpty();

			viewService.showView(viewA.getId());
			assertThat(viewA.getNode().getStyle().replace(" ", "")).isEqualTo("-fx-background-color:red;");
			assertThat(viewB.getNode().getStyle()).isEmpty();
			assertThat(styleService.getAppliedStyleNames(viewA.getNode())).containsExactlyInAnyOrder("style.a");
			assertThat(styleService.getAppliedStyleNames(viewB.getNode())).isEmpty();

			viewService.showView(viewB.getId());
			assertThat(viewA.getNode().getStyle()).isEmpty();
			assertThat(viewB.getNode().getStyle().replace(" ", "")).isEqualTo("-fx-background-color:red;");
			assertThat(styleService.getAppliedStyleNames(viewA.getNode())).isEmpty();
			assertThat(styleService.getAppliedStyleNames(viewB.getNode())).containsExactlyInAnyOrder("style.a");

			styleService.setRootStyle("style.b", true);
			assertThat(viewA.getNode().getStyle()).isEmpty();
			assertThat(viewB.getNode().getStyle().replace(" ", "")).isEqualTo("-fx-background-color:red;-fx-background-color:blue;");
			assertThat(styleService.getAppliedStyleNames(viewA.getNode())).isEmpty();
			assertThat(styleService.getAppliedStyleNames(viewB.getNode())).containsExactlyInAnyOrder("style.a", "style.b");
		});
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
				.node(new Pane())
				.build();
	}




	private View view(final String id, String... styles) {
		return View.builder()
				.id(id)
				.size(new Dimension2D(100, 10))
				.title(id)
				.node(new Pane())
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
