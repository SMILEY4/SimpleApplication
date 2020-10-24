package de.ruegnerlukas.simpleapplication.testapp;

import de.ruegnerlukas.simpleapplication.common.eventbus.EventBus;
import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.StringFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.StringProvider;
import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.core.application.Application;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConfiguration;
import de.ruegnerlukas.simpleapplication.core.application.EventPresentationInitialized;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginInformation;
import de.ruegnerlukas.simpleapplication.core.presentation.simpleui.ManagedStyleProperty;
import de.ruegnerlukas.simpleapplication.core.presentation.simpleui.SUIWindowHandleDataFactory;
import de.ruegnerlukas.simpleapplication.core.presentation.views.PopupConfiguration;
import de.ruegnerlukas.simpleapplication.core.presentation.views.View;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService;
import de.ruegnerlukas.simpleapplication.core.presentation.views.WindowHandle;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiButton;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.windows.WindowInformation;
import javafx.geometry.Dimension2D;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static de.ruegnerlukas.simpleapplication.common.eventbus.SubscriptionData.ofType;
import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.button;
import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.component;
import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.label;
import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.vBox;

@Slf4j
public class SUITestApplication {


	public static final Map<String, Stage> stages = new HashMap<>();




	public static void main(String[] args) {
		SuiRegistry.initialize();
		SuiRegistry.get().registerProperty(SuiButton.class, ManagedStyleProperty.class, new ManagedStyleProperty.ManagedStyleUpdatingBuilder());

		final ApplicationConfiguration configuration = new ApplicationConfiguration();
		configuration.getPlugins().add(new UIPlugin());
		configuration.getProviderFactories().add(new StringFactory("application_name", "SUI Test App"));
		configuration.setShowViewAtStartup(false);
		new Application(configuration).run();
	}




	@Getter
	@Setter
	private static class TestUIState extends SuiState {


		private String text = "-";


	}






	/**
	 * responsible for displaying the ui
	 */
	@Slf4j
	static class UIPlugin extends Plugin {


		public UIPlugin() {
			super(new PluginInformation("plugin.ui", "UI Plugin", "0.1", false));
		}




		@Override
		public void onLoad() {
			final EventService eventService = new Provider<>(EventService.class).get();
			eventService.subscribe(Channel.type(EventPresentationInitialized.class), e -> createViews());
		}




		private void createViews() {
			log.info("{} creating views.", this.getId());

			final TestUIState testUIState = new TestUIState();

			final View viewParent = View.builder()
					.id("sui.test.view")
					.size(new Dimension2D(600, 500))
					.title(new StringProvider("application_name").get())
					.icon(Resource.internal("testResources/icon.png"))
					.dataFactory(new SUIWindowHandleDataFactory(() -> new SuiSceneController(testUIState, TestUIState.class, SUITestApplication::parentWindow)))
					.build();

			final View viewChild = View.builder()
					.id("sui.test.view.child")
					.size(new Dimension2D(300, 100))
					.title(new StringProvider("application_name").get() + " - child")
					.icon(Resource.internal("testResources/icon.png"))
					.dataFactory(new SUIWindowHandleDataFactory(() -> new SuiSceneController(testUIState, TestUIState.class, SUITestApplication::childWindow)))
					.build();


			final EventBus suiEventBus = SuiRegistry.get().getEventBus();
			suiEventBus.subscribe(ofType(ActionEventData.class, Tags.containsAll("button1")), event -> testUIState.update(TestUIState.class, s -> s.setText("Hello World")));
			suiEventBus.subscribe(ofType(ActionEventData.class, Tags.containsAll("button2")), event -> testUIState.update(TestUIState.class, s -> s.setText("Some Tooltip")));
			suiEventBus.subscribe(ofType(ActionEventData.class, Tags.containsAll("button3")), event -> testUIState.update(TestUIState.class, s -> s.setText("Very Long Tooltip")));

			final ViewService viewService = new Provider<>(ViewService.class).get();
			viewService.registerView(viewParent);
			viewService.registerView(viewChild);
			WindowHandle parentHandle = viewService.showView(viewParent.getId());
			viewService.popupView(viewChild.getId(), PopupConfiguration.builder().parent(parentHandle).modality(Modality.NONE).build());

			try {
				stages.put("PRIMARY", forceExtractPrimaryStage());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}




		@Override
		public void onUnload() {
		}

	}




	private static Stage forceExtractPrimaryStage() throws Exception {
		final ViewService viewService = new Provider<>(ViewService.class).get();
		WindowHandle primaryWindowHandle = viewService.getPrimaryWindowHandle();
		Field field = WindowHandle.class.getDeclaredField("stage");
		field.setAccessible(true);
		return (Stage) field.get(primaryWindowHandle);
	}




	private static NodeFactory parentWindow(final TestUIState state) {
		return vBox()
				.id("vbox")
				.items(
						label()
								.id("label")
								.textContent(state.getText())
								.tooltip(state.getText(), true, 200)
								.sizeMin(300, 30),
						button()
								.id("button1")
								.textContent("Hello World")
								.emitEventAction(Tags.from("button1")),
						button()
								.id("button2")
								.textContent("Some Tooltip")
								.emitEventAction(Tags.from("button2")),
						button()
								.id("button3")
								.textContent("Very Long Tooltip")
								.emitEventAction(Tags.from("button3"))
				);
	}




	private static NodeFactory childWindow(final TestUIState state) {
		return vBox()
				.id("vbox")
				.items(
						label()
								.id("label")
								.textContent("CHILD: " + state.getText())
								.tooltip(state.getText(), true, 200)
								.sizeMin(300, 30),
						button()
								.id("button")
								.textContent("Open Popup")
								.eventAction(".", e -> {
									final String windowTitle = "New Popup " + new Random().nextLong();
									SuiRegistry.get().getWindowManager().openNew(
											WindowInformation.builder()
													.title(windowTitle)
													.size(new Dimension2D(200, 200))
													.rootNodeFactory(popupWindow(windowTitle))
													.state(state)
													.owner(stages.get("PRIMARY"))
													.modality(Modality.NONE)
													.onOpen(stage -> {
														stages.put(stage.getTitle(), stage);
														System.out.println("OPEN");
													})
													.onClose(stage -> {
														stages.remove(stage.getTitle());
														System.out.println("CLOSE");
													})
													.build()
									);
								})
				);
	}




	private static NodeFactory popupWindow(final String windowTitle) {
		return component(TestUIState.class,
				state -> vBox()
						.items(
								label()
										.id("label1")
										.textContent("POPUP !!!"),
								label()
										.id("label2")
										.textContent("   -> " + state.getText()),
								button()
										.id("button")
										.textContent("Close Window")
										.eventAction(".", e -> SuiRegistry.get().getWindowManager().close(stages.get(windowTitle)))
						)
		);
	}


}
