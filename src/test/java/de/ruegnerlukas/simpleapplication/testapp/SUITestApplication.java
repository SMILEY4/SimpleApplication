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
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiMenuBar;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.suimenu.SuiCheckMenuItem;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.suimenu.SuiMenu;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.suimenu.SuiMenuItem;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.suimenu.SuiSeparatorMenuItem;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.profiler.SuiProfiler;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.geometry.Dimension2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
											SuiMenuBar.menuBar(
													Properties.anchor(0, null, 0, 0),
													Properties.menuBarContent(
															new SuiMenu("_File",
																	new SuiMenuItem("New", new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN), () -> System.out.println("new")),
																	new SuiMenuItem("Open", new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN), () -> System.out.println("open")),
																	new SuiMenu("Open Recent",
																			new SuiMenuItem("Project A", () -> System.out.println("A")),
																			new SuiMenuItem("Project B", () -> System.out.println("B")),
																			new SuiMenuItem("Project C", () -> System.out.println("C"))
																	),
																	new SuiMenuItem("Close", new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN), () -> System.out.println("close"))
															),
															new SuiMenu("_Edit",
																	new SuiCheckMenuItem("Cut", s -> System.out.println("cut: " + s)),
																	new SuiCheckMenuItem("Copy", true, s -> System.out.println("copy: " + s)),
																	new SuiCheckMenuItem("Paste", s -> System.out.println("paste: " + s))
															),
															new SuiMenu("_Help",
																	new SuiMenuItem("Help", () -> System.out.println("help")),
																	new SuiMenuItem("Update", () -> System.out.println("update")),
																	new SuiSeparatorMenuItem(),
																	new SuiMenuItem("About", () -> System.out.println("about"))
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
