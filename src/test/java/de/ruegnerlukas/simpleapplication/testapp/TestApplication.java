package de.ruegnerlukas.simpleapplication.testapp;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.StringFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.StringProvider;
import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.common.utils.Pair;
import de.ruegnerlukas.simpleapplication.core.application.Application;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConfiguration;
import de.ruegnerlukas.simpleapplication.core.application.EventPresentationInitialized;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginInformation;
import de.ruegnerlukas.simpleapplication.core.presentation.simpleui.ManagedStyleProperty;
import de.ruegnerlukas.simpleapplication.core.presentation.simpleui.SUIWindowHandleDataFactory;
import de.ruegnerlukas.simpleapplication.core.presentation.style.StyleService;
import de.ruegnerlukas.simpleapplication.core.presentation.views.View;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService;
import de.ruegnerlukas.simpleapplication.simpleui.SuiSceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiAnchorPaneItem;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiButton;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiContainer;
import de.ruegnerlukas.simpleapplication.simpleui.events.MouseMoveEventData;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.streams.SuiStream;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.elements.SuiAnchorPane.anchorPane;

@Slf4j
public class TestApplication {


	public static void main(String[] args) {
		SuiRegistry.initialize();
		SuiRegistry.get().registerProperty(SuiButton.class, ManagedStyleProperty.class, new ManagedStyleProperty.ManagedStyleUpdatingBuilder());

		final ApplicationConfiguration configuration = new ApplicationConfiguration();
		configuration.getPlugins().add(new LoggingPlugin());
		configuration.getPlugins().add(new UIPlugin());
		configuration.getProviderFactories().add(new StringFactory("application_name", "Test App"));
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


			private List<String> strings = new ArrayList<>(List.of("A", "B", "C"));

			private int cycleCount = 1;

			private int globalCount = 1;


		}






		@Getter
		@Setter
		@AllArgsConstructor
		private static class ChangeCycleCountEvent extends Publishable {


			public int cycleCount;

		}




		private void createViews() {
			log.info("{} creating views.", this.getId());

			final String applicationName = new StringProvider("application_name").get();

			final ViewService viewService = new Provider<>(ViewService.class).get();
			final String ID_A = "plugin.ui.a";
			final String ID_B = "plugin.ui.b";
			final String ID_B_POPUP = "plugin.ui.bpopup";
			final String ID_B_WARN = "plugin.ui.bwarn";

			final EventService eventService = new Provider<>(EventService.class).get();

			final TestUIState testUIState = new TestUIState();

			final StyleService styleService = new Provider<>(StyleService.class).get();

			/*
			Shows the views in the following order:
			ID_A -> ID_B -> (popup: ID_B_POPUP -> ID_B_WARN) -> ID_A
			 */

			Thread thread = new Thread(() -> {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
//					styleService.reloadAll();
//					testUIState.update(TestUIState.class, state -> {
//						state.setGlobalCount(state.getGlobalCount() + 1);
//					});
				}
			});
			thread.setDaemon(true);
			thread.start();

			// VIEW A

			final View viewA = View.builder()
					.id(ID_A)
					.size(new Dimension2D(300, 100))
					.maxSize(new Dimension2D(300, 300))
					.title(applicationName + " - View A")
					.icon(Resource.internal("testResources/icon.png"))
					.dataFactory(new SUIWindowHandleDataFactory(() -> new SuiSceneContext(testUIState, TestUIState.class, state ->
							anchorPane(

									EventProperties.eventMouseEntered(SuiStream.eventStream(MouseMoveEventData.class,
											stream -> stream
													.mapIgnoreNulls(e -> Pair.of(e.getX(), e.getY()))
													.forEach(e -> System.out.println("entered at " + e.getLeft() + "," + e.getRight()))
									)),

									Properties.items(
											SuiAnchorPaneItem.anchorPaneItem(
													SuiContainer.container(
															Properties.id("myContainer"),
															Properties.layout("myLayout", ((parent, nodes) -> {
																final double width = parent.getWidth();
																final double height = parent.getHeight();
																final Node node0 = nodes.get(0);
																final Node node1 = nodes.get(1);
																node0.resizeRelocate(0, 0, width, height / 2);
																node1.resizeRelocate(0, height / 2, width, height / 2);
															})),
															Properties.items(
																	SuiButton.button(
																			Properties.id("btn1"),
																			Properties.textContent("Child Button 1")
																	),
																	SuiButton.button(
																			Properties.id("btn2"),
																			Properties.textContent("Child Button 2")
																	)
															)
													),
//													choiceBox(
//															Properties.choices(state.strings),
//															Properties.choiceBoxConverter(String.class,
//																	s -> s.split(":")[1],
//																	s -> "item:" + s
//															),
//															EventProperties.eventSelectedItem(String.class, e -> {
//																System.out.println(e.getPrevItem() + " -> " + e.getItem());
//																if (e.getItem() != null) {
//																	state.update(TestUIState.class, s -> {
//																		s.strings.add("" + s.strings.size() + " - " + new Random().nextInt(1000));
//																		s.strings.remove(e.getItem());
//																	});
//																}
//															})
//													),
													Properties.anchor(0, 0, 0, 0)
											)
									)
							)

					)))
					.build();

//			final View viewA = View.builder()
//					.id(ID_A)
//					.size(new Dimension2D(300, 100))
//					.maxSize(new Dimension2D(300, 300))
//					.title(applicationName + " - View A")
//					.icon(Resource.internal("testResources/icon.png"))
//					.dataFactory(new SUIWindowHandleDataFactory(() -> new SUISceneContext(testUIState, TestUIState.class, state ->
//							button(
//									managedStyle(styleService, Style.fromResource(Resource.externalRelative("src/test/resources/testResources/testStyle.css"))),
//									textContent(state.getGlobalCount() + ":   Switch A -> B (" + state.getCycleCount() + ")"),
//									buttonListener(() -> eventService.publish(new ChangeCycleCountEvent(state.getCycleCount() + 1)))
//							)
//					)))
//					.build();

//			eventService.subscribe(Channel.type(ChangeCycleCountEvent.class), publishable -> {
//				final ChangeCycleCountEvent event = (ChangeCycleCountEvent) publishable;
//				testUIState.update(TestUIState.class, true, state -> {
//					state.setCycleCount(event.cycleCount);
//				});
//				viewService.showView(ID_B);
//			});

//			// VIEW B
//
//			final View viewB = View.builder()
//					.id(ID_B)
//					.size(new Dimension2D(200, 500))
//					.title(applicationName + " - View B")
//					.icon(Resource.internal("testResources/icon.png"))
//					.dataFactory(new SUIWindowHandleDataFactory(() -> new SUISceneContext(testUIState, TestUIState.class, state ->
//							button(
//									textContent(testUIState.getGlobalCount() + ":   Switch B -> A"),
//									buttonListener(() -> viewService.popupView(ID_B_POPUP, PopupConfiguration.builder().style(StageStyle.UNDECORATED).wait(false).build()))
//							)
//					)))
//					.build();
//
//
//			// VIEW B CONFIRM
//
//			final View viewBPopup = View.builder()
//					.id(ID_B_POPUP)
//					.size(new Dimension2D(300, 200))
//					.title(applicationName + " - View B Confirm")
//					.icon(Resource.internal("testResources/icon.png"))
//					.dataFactory(new SUIWindowHandleDataFactory(() -> new SUISceneContext(testUIState, TestUIState.class, state ->
//							button(
//									textContent(testUIState.getGlobalCount() + ":   Confirm switch"),
//									buttonListener(() -> {
//										final WindowHandle handlePopup = viewService.getWindowHandles(ID_B_POPUP).get(0);
//										viewService.showView(ID_B_WARN, handlePopup);
//									})
//							)
//					)))
//					.build();
//
//			final View viewBWarn = View.builder()
//					.id(ID_B_WARN)
//					.size(new Dimension2D(200, 300))
//					.title(applicationName + " - View B LAST WARNING")
//					.icon(Resource.internal("testResources/icon.png"))
//					.dataFactory(new SUIWindowHandleDataFactory(() -> new SUISceneContext(testUIState, TestUIState.class, state ->
//							button(
//									textContent(state.getGlobalCount() + ":   You sure ? (" + " -> " + state.getCycleCount() + ")"),
//									buttonListener(() -> {
//										final WindowHandle handlePopup = viewService.getWindowHandles(ID_B_WARN).get(0);
//										viewService.closePopup(handlePopup);
//										viewService.showView(ID_A);
//									})
//							)
//					)))
//					.build();

			viewService.registerView(viewA);
//			viewService.registerView(viewB);
//			viewService.registerView(viewBPopup);
//			viewService.registerView(viewBWarn);
			viewService.showView(viewA.getId());

		}




		@Override
		public void onUnload() {
		}

	}






	/**
	 * Logs as much as possible in the application
	 */
	@Slf4j
	static class LoggingPlugin extends Plugin {


		public LoggingPlugin() {
			super(new PluginInformation("plugin.logging", "Logging Plugin", "0.1", false));
		}




		@Override
		public void onLoad() {
			log.info("LOGGING: onLoad {}.", getId());
			final EventService eventService = new Provider<>(EventService.class).get();
			eventService.subscribe(this::onEvent); // TODO
		}




		private void onEvent(final Publishable event) {
			log.info("LOGGING: event in channel='{}'", event.getChannel());
		}




		@Override
		public void onUnload() {
			log.info("LOGGING: onUnload {}.", getId());
		}

	}


}
