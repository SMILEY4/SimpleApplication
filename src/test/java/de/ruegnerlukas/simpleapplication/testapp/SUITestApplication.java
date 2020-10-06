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
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiAccordion;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiAnchorPane;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiButton;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiLabel;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.profiler.SuiProfiler;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.geometry.Dimension2D;
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


			public String expandedSection = "";

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
							SuiAnchorPane.anchorPane(
									Properties.items(
											SuiLabel.label(
													Properties.id("label"),
													Properties.textContent(state.expandedSection),
													Properties.anchor(0, null, 0, 0)
											),
											SuiAccordion.accordion(
													Properties.id("accordion"),
													Properties.anchor(50, 0, 0, 0),
													EventProperties.eventAccordionExpanded(".", e -> {
														if (e.isExpanded()) {
															state.update(TestUIState.class, s -> s.setExpandedSection(e.getSectionTitle()));
														} else {
															state.update(TestUIState.class, s -> s.setExpandedSection("none"));
														}
													}),
													Properties.items(
															SuiButton.button(
																	Properties.id("btn1"),
																	Properties.textContent("Button 1"),
																	Properties.title("Section 1")
															),
															SuiButton.button(
																	Properties.id("btn2"),
																	Properties.textContent("Button 2"),
																	Properties.title("Section 2")
															),
															SuiButton.button(
																	Properties.id("btn3"),
																	Properties.textContent("Button 3"),
																	Properties.title("Section 3")
															),
															SuiButton.button(
																	Properties.id("btn4"),
																	Properties.textContent("Button 4"),
																	Properties.title("Section 4")
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
