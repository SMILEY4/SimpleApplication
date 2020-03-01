package de.ruegnerlukas.simpleapplication.core.presentation;

import de.ruegnerlukas.simpleapplication.common.events.EventPackage;
import de.ruegnerlukas.simpleapplication.common.events.specializedevents.EmptyEventPackage;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ProviderService;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.presentation.views.PopupConfiguration;
import de.ruegnerlukas.simpleapplication.core.presentation.views.View;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewEvent;
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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewServiceTest extends ApplicationTest {


	private final String STARTUP_VIEW_ID = "view.test";

	private ViewService viewService;

	private EventService eventService;




	@Override
	public void start(Stage stage) {

		eventService = eventService();
		ProviderService.cleanup();
		ProviderService.registerFactory(new InstanceFactory<>(EventService.class) {
			@Override
			public EventService buildObject() {
				return eventService;
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

			assertEvent(ApplicationConstants.EVENT_SHOW_VIEW);
			ViewEvent eventOpen = getEventAny(ApplicationConstants.EVENT_SHOW_VIEW);
			assertThat(eventOpen.getPrevViewId()).isEqualTo(STARTUP_VIEW_ID);
			assertThat(eventOpen.getViewId()).isEqualTo(view.getId());
			assertThat(eventOpen.getWindowHandle()).isEqualTo(handle);
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

			assertEvent(ApplicationConstants.EVENT_SHOW_VIEW);
			ViewEvent eventOpen = getEventAny(ApplicationConstants.EVENT_SHOW_VIEW);
			assertThat(eventOpen.getPrevViewId()).isEqualTo(viewPrev.getId());
			assertThat(eventOpen.getViewId()).isEqualTo(view.getId());
			assertThat(eventOpen.getWindowHandle()).isEqualTo(handle);
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

			assertEvent(ApplicationConstants.EVENT_OPEN_POPUP);
			ViewEvent eventOpen = getEventAny(ApplicationConstants.EVENT_OPEN_POPUP);
			assertThat(eventOpen.getPrevViewId()).isNull();
			assertThat(eventOpen.getViewId()).isEqualTo(view.getId());
			assertThat(eventOpen.getWindowHandle()).isEqualTo(handle);

			// close popup
			viewService.closePopup(handle);
			assertThat(viewService.getWindowHandles(view.getId())).hasSize(0);

			assertEvent(ApplicationConstants.EVENT_CLOSE_POPUP);
			ViewEvent eventClose = getEventAny(ApplicationConstants.EVENT_CLOSE_POPUP);
			assertThat(eventClose.getPrevViewId()).isEqualTo(view.getId());
			assertThat(eventClose.getViewId()).isEqualTo(view.getId());
			assertThat(eventClose.getWindowHandle()).isEqualTo(handle);

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




	private List<EventPackage> eventPackages = new ArrayList<>();




	private EventService eventService() {
		final EventService eventService = new EventService();
		eventService.subscribe(eventPackage -> eventPackages.add(eventPackage));
		return eventService;
	}




	private void assertEvent(final String channel) {
		assertThat(getEventPackages(channel)).isNotEmpty();
	}




	private void clearEvents() {
		eventPackages.clear();
	}




	private List<EventPackage> getEventPackages(final String channel) {
		return eventPackages.stream()
				.filter(e -> List.of(e.getChannels()).contains(channel))
				.collect(Collectors.toList());
	}




	private <T> T getEventAny(final String channel) {
		return (T) eventPackages.stream()
				.filter(e -> List.of(e.getChannels()).contains(channel))
				.findAny().orElseGet(EmptyEventPackage::new).getEvent();
	}


}
