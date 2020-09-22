package de.ruegnerlukas.simpleapplication.testapp;

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
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiAnchorPane;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiButton;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiSplitPane;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.profiler.SuiProfiler;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.geometry.Dimension2D;
import javafx.geometry.Orientation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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

			private boolean fixed = false;

		}




		private void createViews() {
			log.info("{} creating views.", this.getId());


			final TestUIState testUIState = new TestUIState();

			final View view = View.builder()
					.id("sui.test.view")
					.size(new Dimension2D(500, 400))
					.title(new StringProvider("application_name").get())
					.icon(Resource.internal("testResources/icon.png"))
					.dataFactory(new SUIWindowHandleDataFactory(() -> new SuiSceneController(testUIState, TestUIState.class, state ->
							SuiAnchorPane.anchorPane(
									Properties.items(
											SuiSplitPane.splitPane(
													Properties.id("splitpane"),
													Properties.anchorFitParent(),
													Properties.orientation(Orientation.HORIZONTAL),
													Properties.dividerPositions(state.isFixed(), 0.2, 0.8),
													EventProperties.dividerPositionListener(".", e -> {
														System.out.println(e.getDividerIndex() + ": " + e.getNextPosition());
													}),
													Properties.items(
															SuiAnchorPane.anchorPane(
																	Properties.id("anchorpane.1"),
																	Properties.item(
																			SuiButton.button(
																					Properties.id("btn.1"),
																					Properties.anchorFitParent(),
																					Properties.textContent("Button 1")
																			)
																	)
															),
															SuiAnchorPane.anchorPane(
																	Properties.id("anchorpane.2"),
																	Properties.item(
																			SuiButton.button(
																					Properties.id("btn.2"),
																					Properties.anchorFitParent(),
																					Properties.textContent("Button 2"),
																					EventProperties.eventAction(".", e -> state.update(TestUIState.class, s -> s.setFixed(!s.isFixed())))
																			)
																	)
															),
															SuiAnchorPane.anchorPane(
																	Properties.id("anchorpane.3"),
																	Properties.item(
																			SuiButton.button(
																					Properties.id("btn.3"),
																					Properties.anchorFitParent(),
																					Properties.textContent("Button 3")
																			)
																	)
															)
													)
											)
									)
							)

					)))
					.build();

			final ViewService viewService = new Provider<>(ViewService.class).get();
			viewService.registerView(view);
			viewService.showView(view.getId());

			log.info(System.lineSeparator() + "====== SUI STATS ======" + System.lineSeparator() + SuiProfiler.get().getStatisticsAsPrettyString());
		}


//		private NodeFactory killerGridOfDoom(int cols, int rows, String tabTitle) {
//			return SuiVBox.vbox(
//					Properties.id("grid"+tabTitle),
//					Properties.tabTitle(tabTitle),
//					Properties.items(
//							IntStream.range(0, rows).mapToObj(row ->
//									SuiHBox.hbox(
//											Properties.id(row + ".cols"),
//											Properties.items(
//													IntStream.range(0, cols).mapToObj(col ->
//															SuiButton.button(
//																	Properties.id(col + "." + row),
//																	Properties.textContent(col + "." + row),
//																	Properties.minSize(0, 0)
//															)
//													)
//											)
//									)
//							)
//					)
//			);
//		}




		@Override
		public void onUnload() {
		}

	}


}
