package de.ruegnerlukas.simpleapplication.core.presentation;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ProviderService;
import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.events.EventServiceImpl;
import de.ruegnerlukas.simpleapplication.core.presentation.simpleui.SUIViewNodeFactory;
import de.ruegnerlukas.simpleapplication.core.presentation.style.StyleService;
import de.ruegnerlukas.simpleapplication.core.presentation.style.StyleServiceImpl;
import de.ruegnerlukas.simpleapplication.core.presentation.views.PopupConfiguration;
import de.ruegnerlukas.simpleapplication.core.presentation.views.View;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewServiceImpl;
import de.ruegnerlukas.simpleapplication.core.presentation.views.WindowHandle;
import de.ruegnerlukas.simpleapplication.simpleui.SUISceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.SUISceneContextImpl;
import de.ruegnerlukas.simpleapplication.simpleui.SUIState;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.application.Platform;
import javafx.geometry.Dimension2D;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static de.ruegnerlukas.simpleapplication.simpleui.elements.SUIButton.button;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.textContent;
import static org.assertj.core.api.Assertions.assertThat;

public class SimpleUIViewTest extends ApplicationTest {


	private ViewService viewService;

	private EventService eventService;

	private StyleService styleService;




	@Override
	public void start(Stage stage) {
		SUIRegistry.initialize();

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

			final SUIState state = new SUIState();
			final View view = view("test.view.show.primary", state, "A Button");
			viewService.registerView(view);

			final WindowHandle handle = viewService.showView(view.getId());

			assertThat(handle.getRootNode()).isNotNull();
			assertThat(handle.getRootNode() instanceof Button).isTrue();
			assertThat(((Button) handle.getRootNode()).getText()).isEqualTo("A Button");

			assertThat(((SUIViewNodeFactory) view.getNodeFactory()).getSceneContext()).isPresent();
			final SUISceneContext context = ((SUIViewNodeFactory) view.getNodeFactory()).getSceneContext().get();
			assertThat(context.getState()).isEqualTo(state);
			assertThat(state.getListeners()).hasSize(1);
			assertThat(state.getListeners().get(0)).isEqualTo(context);
		});
	}




	@Test
	public void testReplaceView() {
		Platform.runLater(() -> {

			final SUIState state = new SUIState();
			final View view1 = view("test.view.replace.primary.1", state, "Button 1");
			final View view2 = view("test.view.replace.primary.2", state, "Button 2");
			viewService.registerView(view1);
			viewService.registerView(view2);

			final WindowHandle handle1 = viewService.showView(view1.getId());
			final WindowHandle handle2 = viewService.showView(view2.getId());

			assertThat(viewService.isWindowHandleActive(handle1)).isTrue();
			assertThat(viewService.isWindowHandleActive(handle2)).isTrue();
			assertThat(handle1).isEqualTo(handle2);

			assertThat(handle2.getRootNode()).isNotNull();
			assertThat(handle2.getRootNode() instanceof Button).isTrue();
			assertThat(((Button) handle2.getRootNode()).getText()).isEqualTo("Button 2");

			assertThat(((SUIViewNodeFactory) view1.getNodeFactory()).getSceneContext()).isEmpty();
			assertThat(((SUIViewNodeFactory) view2.getNodeFactory()).getSceneContext()).isPresent();

			final SUISceneContext context2 = ((SUIViewNodeFactory) view2.getNodeFactory()).getSceneContext().get();

			assertThat(context2.getState()).isEqualTo(state);

			assertThat(state.getListeners()).hasSize(1);
			assertThat(state.getListeners()).containsExactlyInAnyOrder((SUISceneContextImpl) context2);
		});
	}




	@Test
	public void testOpenPopup() {
		Platform.runLater(() -> {

			final SUIState state = new SUIState();
			final View view1 = view("test.view.open.popup.1", state, "Button 1");
			final View view2 = view("test.view.open.popup.2", state, "Button 2");
			viewService.registerView(view1);
			viewService.registerView(view2);

			final WindowHandle handle1 = viewService.showView(view1.getId());
			final WindowHandle handle2 = viewService.popupView(view2.getId(), popupConfig(handle1));

			assertThat(viewService.isWindowHandleActive(handle1)).isTrue();
			assertThat(viewService.isWindowHandleActive(handle2)).isTrue();

			assertThat(handle1.getRootNode()).isNotNull();
			assertThat(handle1.getRootNode() instanceof Button).isTrue();
			assertThat(((Button) handle1.getRootNode()).getText()).isEqualTo("Button 1");

			assertThat(handle2.getRootNode()).isNotNull();
			assertThat(handle2.getRootNode() instanceof Button).isTrue();
			assertThat(((Button) handle2.getRootNode()).getText()).isEqualTo("Button 2");

			assertThat(((SUIViewNodeFactory) view1.getNodeFactory()).getSceneContext()).isPresent();
			assertThat(((SUIViewNodeFactory) view2.getNodeFactory()).getSceneContext()).isPresent();

			final SUISceneContext context1 = ((SUIViewNodeFactory) view1.getNodeFactory()).getSceneContext().get();
			final SUISceneContext context2 = ((SUIViewNodeFactory) view2.getNodeFactory()).getSceneContext().get();

			assertThat(context1.getState()).isEqualTo(state);
			assertThat(context2.getState()).isEqualTo(state);

			assertThat(state.getListeners()).hasSize(2);
			assertThat(state.getListeners()).containsExactlyInAnyOrder((SUISceneContextImpl) context1, (SUISceneContextImpl) context2);
		});
	}



	@Test
	public void testOpenPopupMultiple() {
		Platform.runLater(() -> {

			final SUIState state = new SUIState();
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

			assertThat(handle1.getRootNode()).isNotNull();
			assertThat(handle1.getRootNode() instanceof Button).isTrue();
			assertThat(((Button) handle1.getRootNode()).getText()).isEqualTo("Button 1");

			assertThat(handle2.getRootNode()).isNotNull();
			assertThat(handle2.getRootNode() instanceof Button).isTrue();
			assertThat(((Button) handle2.getRootNode()).getText()).isEqualTo("Button 2");

			assertThat(handle3.getRootNode()).isNotNull();
			assertThat(handle3.getRootNode() instanceof Button).isTrue();
			assertThat(((Button) handle3.getRootNode()).getText()).isEqualTo("Button 2");

			assertThat(handle4.getRootNode()).isNotNull();
			assertThat(handle4.getRootNode() instanceof Button).isTrue();
			assertThat(((Button) handle4.getRootNode()).getText()).isEqualTo("Button 2");

			assertThat(state.getListeners()).hasSize(4);
		});
	}




	@Test
	public void testClosePopup() {
		Platform.runLater(() -> {

			final SUIState state = new SUIState();
			final View view1 = view("test.view.close.popup.1", state, "Button 1");
			final View view2 = view("test.view.close.popup.2", state, "Button 2");
			viewService.registerView(view1);
			viewService.registerView(view2);

			final WindowHandle handle1 = viewService.showView(view1.getId());
			final WindowHandle handle2 = viewService.popupView(view2.getId(), popupConfig(handle1));
			viewService.closePopup(handle2);

			assertThat(viewService.isWindowHandleActive(handle1)).isTrue();
			assertThat(viewService.isWindowHandleActive(handle2)).isFalse();

			assertThat(((SUIViewNodeFactory) view1.getNodeFactory()).getSceneContext()).isPresent();
			assertThat(((SUIViewNodeFactory) view2.getNodeFactory()).getSceneContext()).isEmpty();

			final SUISceneContext context1 = ((SUIViewNodeFactory) view1.getNodeFactory()).getSceneContext().get();

			assertThat(context1.getState()).isEqualTo(state);

			assertThat(state.getListeners()).hasSize(1);
			assertThat(state.getListeners()).containsExactlyInAnyOrder((SUISceneContextImpl) context1);
		});
	}




	private EventService eventService() {
		return new EventServiceImpl();
	}




	private StyleService styleService() {
		return new StyleServiceImpl();
	}




	private View view(final String id) {
		return view(id, new SUIViewNodeFactory(() -> new SUISceneContextImpl(
				button(
						textContent("A Button")
				)
		)));
	}




	private View view(final String id, final SUIState state, final String btnText) {
		return view(id, new SUIViewNodeFactory(() -> new SUISceneContextImpl(state,
				button(
						textContent(btnText)
				)
		)));
	}




	private View view(final String id, final SUIViewNodeFactory suiViewNodeFactory) {
		return View.builder()
				.id(id)
				.size(new Dimension2D(100, 10))
				.title(id)
				.nodeFactory(suiViewNodeFactory)
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
