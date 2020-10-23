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
import de.ruegnerlukas.simpleapplication.core.presentation.views.View;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiButton;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import javafx.geometry.Dimension2D;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static de.ruegnerlukas.simpleapplication.common.eventbus.SubscriptionData.ofType;
import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.button;
import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.label;
import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.vBox;

@Slf4j
public class SUITestApplication {


	public static void main(String[] args) {
		SuiRegistry.initialize();
		SuiRegistry.get().registerProperty(SuiButton.class, ManagedStyleProperty.class, new ManagedStyleProperty.ManagedStyleUpdatingBuilder());

		final ApplicationConfiguration configuration = new ApplicationConfiguration();
		configuration.getPlugins().add(new UIPlugin());
		configuration.getProviderFactories().add(new StringFactory("application_name", "SUI Test App"));
		configuration.setShowViewAtStartup(false);
		new Application(configuration).run();
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




		@Getter
		@Setter
		private static class TestUIState extends SuiState {


			private String text = "-";


		}




		private void createViews() {
			log.info("{} creating views.", this.getId());


			final TestUIState testUIState = new TestUIState();

			final View view = View.builder()
					.id("sui.test.view")
					.size(new Dimension2D(600, 500))
					.title(new StringProvider("application_name").get())
					.icon(Resource.internal("testResources/icon.png"))
					.dataFactory(new SUIWindowHandleDataFactory(() -> new SuiSceneController(testUIState, TestUIState.class, state ->
									vBox()
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
											)
							))
					)
					.build();


			final EventBus suiEventBus = SuiRegistry.get().getEventBus();
			suiEventBus.subscribe(ofType(ActionEventData.class, Tags.containsAll("button1")), event -> testUIState.update(TestUIState.class, s -> s.setText("Hello World")));
			suiEventBus.subscribe(ofType(ActionEventData.class, Tags.containsAll("button2")), event -> testUIState.update(TestUIState.class, s -> s.setText("Some Tooltip")));
			suiEventBus.subscribe(ofType(ActionEventData.class, Tags.containsAll("button3")), event -> testUIState.update(TestUIState.class, s -> s.setText("Very Long Tooltip")));

			final ViewService viewService = new Provider<>(ViewService.class).get();
			viewService.registerView(view);
			viewService.showView(view.getId());
		}




		@Override
		public void onUnload() {
		}

	}


}
