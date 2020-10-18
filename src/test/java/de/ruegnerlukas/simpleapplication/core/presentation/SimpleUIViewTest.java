package de.ruegnerlukas.simpleapplication.core.presentation;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ProviderService;
import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.events.EventServiceImpl;
import de.ruegnerlukas.simpleapplication.core.presentation.simpleui.SUIWindowHandleData;
import de.ruegnerlukas.simpleapplication.core.presentation.simpleui.SUIWindowHandleDataFactory;
import de.ruegnerlukas.simpleapplication.core.presentation.style.StyleService;
import de.ruegnerlukas.simpleapplication.core.presentation.style.StyleServiceImpl;
import de.ruegnerlukas.simpleapplication.core.presentation.views.PopupConfiguration;
import de.ruegnerlukas.simpleapplication.core.presentation.views.View;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewServiceImpl;
import de.ruegnerlukas.simpleapplication.core.presentation.views.WindowHandle;
import de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.application.Platform;
import javafx.geometry.Dimension2D;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleUIViewTest extends ApplicationTest {


	private ViewService viewService;

	private EventService eventService;

	private StyleService styleService;




	@Override
	public void start(Stage stage) {
		SuiRegistry.initialize();

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

		final View view = view("view.test.startup");
		viewService = new ViewServiceImpl();
		viewService.initialize(stage, true, view);
	}




	@Test
	public void testShowView() {
		Platform.runLater(() -> {

			final SuiState state = new SuiState();
			final View view = view("test.view.show.primary", state, "A Button");
			viewService.registerView(view);

			final WindowHandle handle = viewService.showView(view.getId());

			assertThat(handle.getCurrentRootNode()).isNotNull();
			assertThat(handle.getCurrentRootNode() instanceof Button).isTrue();
			assertThat(((Button) handle.getCurrentRootNode()).getText()).isEqualTo("A Button");

			final SuiSceneController context = ((SUIWindowHandleData) handle.getData()).getSceneContext();
			assertThat(context).isNotNull();
			assertThat(context.getState()).isEqualTo(state);
			assertThat(state.getListeners()).hasSize(1);
			assertThat(state.getListeners().get(0)).isEqualTo(context);
		});
	}




	@Test
	public void testReplaceView() {
		Platform.runLater(() -> {

			final SuiState state = new SuiState();
			final View view1 = view("test.view.replace.primary.1", state, "Button 1");
			final View view2 = view("test.view.replace.primary.2", state, "Button 2");
			viewService.registerView(view1);
			viewService.registerView(view2);

			final WindowHandle handle1 = viewService.showView(view1.getId());
			final WindowHandle handle2 = viewService.showView(view2.getId());

			assertThat(handle1).isEqualTo(handle2);
			assertThat(viewService.isWindowHandleActive(handle2)).isTrue();

			assertThat(handle2.getCurrentRootNode()).isNotNull();
			assertThat(handle2.getCurrentRootNode() instanceof Button).isTrue();
			assertThat(((Button) handle2.getCurrentRootNode()).getText()).isEqualTo("Button 2");

			final SuiSceneController context = ((SUIWindowHandleData) handle2.getData()).getSceneContext();
			assertThat(context.getState()).isEqualTo(state);
			assertThat(state.getListeners()).hasSize(1);
			assertThat(state.getListeners()).containsExactlyInAnyOrder((SuiSceneController) context);
		});
	}




	@Test
	public void testOpenPopup() {
		Platform.runLater(() -> {

			final SuiState state = new SuiState();
			final View view1 = view("test.view.open.popup.1", state, "Button 1");
			final View view2 = view("test.view.open.popup.2", state, "Button 2");
			viewService.registerView(view1);
			viewService.registerView(view2);

			final WindowHandle handle1 = viewService.showView(view1.getId());
			final WindowHandle handle2 = viewService.popupView(view2.getId(), popupConfig(handle1));

			assertThat(viewService.isWindowHandleActive(handle1)).isTrue();
			assertThat(viewService.isWindowHandleActive(handle2)).isTrue();

			assertThat(handle1.getCurrentRootNode()).isNotNull();
			assertThat(handle1.getCurrentRootNode() instanceof Button).isTrue();
			assertThat(((Button) handle1.getCurrentRootNode()).getText()).isEqualTo("Button 1");

			assertThat(handle2.getCurrentRootNode()).isNotNull();
			assertThat(handle2.getCurrentRootNode() instanceof Button).isTrue();
			assertThat(((Button) handle2.getCurrentRootNode()).getText()).isEqualTo("Button 2");

			final SuiSceneController context1 = ((SUIWindowHandleData) handle1.getData()).getSceneContext();
			final SuiSceneController context2 = ((SUIWindowHandleData) handle2.getData()).getSceneContext();
			assertThat(context1).isNotEqualTo(context2);

			assertThat(context1.getState()).isEqualTo(state);
			assertThat(context2.getState()).isEqualTo(state);

			assertThat(state.getListeners()).hasSize(2);
			assertThat(state.getListeners()).containsExactlyInAnyOrder((SuiSceneController) context1, (SuiSceneController) context2);
		});
	}




	@Test
	public void testOpenPopupMultiple() {
		Platform.runLater(() -> {

			final SuiState state = new SuiState();
			final View view1 = view("test.view.open.popup.1", state, "Button 1");
			final View view2 = view("test.view.open.popup.2", state, "Button 2");
			viewService.registerView(view1);
			viewService.registerView(view2);

			final WindowHandle handle1 = viewService.showView(view1.getId());
			final WindowHandle handle2 = viewService.popupView(view2.getId(), popupConfig(handle1));
			final WindowHandle handle3 = viewService.popupView(view2.getId(), popupConfig(handle1));
			final WindowHandle handle4 = viewService.popupView(view2.getId(), popupConfig(handle1));

			assertThat(viewService.isWindowHandleActive(handle1)).isTrue();
			assertThat(viewService.isWindowHandleActive(handle2)).isTrue();
			assertThat(viewService.isWindowHandleActive(handle3)).isTrue();
			assertThat(viewService.isWindowHandleActive(handle4)).isTrue();

			assertThat(handle1.getCurrentRootNode()).isNotNull();
			assertThat(handle1.getCurrentRootNode() instanceof Button).isTrue();
			assertThat(((Button) handle1.getCurrentRootNode()).getText()).isEqualTo("Button 1");

			assertThat(handle2.getCurrentRootNode()).isNotNull();
			assertThat(handle2.getCurrentRootNode() instanceof Button).isTrue();
			assertThat(((Button) handle2.getCurrentRootNode()).getText()).isEqualTo("Button 2");

			assertThat(handle3.getCurrentRootNode()).isNotNull();
			assertThat(handle3.getCurrentRootNode() instanceof Button).isTrue();
			assertThat(((Button) handle3.getCurrentRootNode()).getText()).isEqualTo("Button 2");

			assertThat(handle4.getCurrentRootNode()).isNotNull();
			assertThat(handle4.getCurrentRootNode() instanceof Button).isTrue();
			assertThat(((Button) handle4.getCurrentRootNode()).getText()).isEqualTo("Button 2");

			final SuiSceneController context1 = ((SUIWindowHandleData) handle1.getData()).getSceneContext();
			final SuiSceneController context2 = ((SUIWindowHandleData) handle2.getData()).getSceneContext();
			final SuiSceneController context3 = ((SUIWindowHandleData) handle3.getData()).getSceneContext();
			final SuiSceneController context4 = ((SUIWindowHandleData) handle4.getData()).getSceneContext();
			assertThat(context1).isNotEqualTo(context2);
			assertThat(context1).isNotEqualTo(context3);
			assertThat(context1).isNotEqualTo(context4);

			assertThat(context1.getState()).isEqualTo(state);
			assertThat(context2.getState()).isEqualTo(state);
			assertThat(context3.getState()).isEqualTo(state);
			assertThat(context4.getState()).isEqualTo(state);

			assertThat(state.getListeners()).hasSize(4);
			assertThat(state.getListeners()).containsExactlyInAnyOrder(
					(SuiSceneController) context1,
					(SuiSceneController) context2,
					(SuiSceneController) context3,
					(SuiSceneController) context4
			);
		});
	}




	@Test
	public void testClosePopup() {
		Platform.runLater(() -> {

			final SuiState state = new SuiState();
			final View view1 = view("test.view.close.popup.1", state, "Button 1");
			final View view2 = view("test.view.close.popup.2", state, "Button 2");
			viewService.registerView(view1);
			viewService.registerView(view2);

			final WindowHandle handle1 = viewService.showView(view1.getId());
			final WindowHandle handle2 = viewService.popupView(view2.getId(), popupConfig(handle1));
			viewService.closePopup(handle2);

			assertThat(viewService.isWindowHandleActive(handle1)).isTrue();
			assertThat(viewService.isWindowHandleActive(handle2)).isFalse();

			final SuiSceneController context1 = ((SUIWindowHandleData) handle1.getData()).getSceneContext();
			assertThat(handle2.getData()).isNull();
			assertThat(context1.getState()).isEqualTo(state);

			assertThat(state.getListeners()).hasSize(1);
			assertThat(state.getListeners()).containsExactlyInAnyOrder((SuiSceneController) context1);
		});
	}




	private EventService eventService() {
		return new EventServiceImpl();
	}




	private StyleService styleService() {
		return new StyleServiceImpl();
	}




	private View view(final String id) {
		return view(id, new SUIWindowHandleDataFactory(() -> new SuiSceneController(
				SuiElements.button().textContent("A Button")
		)));
	}




	private View view(final String id, final SuiState state, final String btnText) {
		return view(id, new SUIWindowHandleDataFactory(() -> new SuiSceneController(state,
				SuiElements.button().textContent(btnText)
		)));
	}




	private View view(final String id, final SUIWindowHandleDataFactory suiWindowHandleDataFactory) {
		return View.builder()
				.id(id)
				.size(new Dimension2D(100, 10))
				.title(id)
				.dataFactory(suiWindowHandleDataFactory)
				.icon(Resource.internal("testResources/icon.png"))
				.build();
	}




	private PopupConfiguration popupConfig(final WindowHandle parent) {
		return PopupConfiguration.builder()
				.parent(parent)
				.wait(false)
				.build();
	}

}
