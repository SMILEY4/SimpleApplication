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
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiButton;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiComboBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiVBox;
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

import java.util.ArrayList;
import java.util.Map;

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


			private boolean english = false;

			private String selected = null;




			public void setEnglish(final boolean english) {
				System.out.println("set english = " + english);
				this.english = english;
			}




			public void setSelected(final String selected) {
				System.out.println("set selected = " + selected);
				this.selected = selected;
			}




			public static final Map<String, String> TO_ENGLISH = Map.of(
					"Montag", "Monday",
					"Dienstag", "Tuesday",
					"Mittwoch", "Wednesday",
					"Donnerstag", "Thursday",
					"Freitag", "Friday",
					"Samstag", "Saturday",
					"Sonntag", "Sunday"
			);

			public static final Map<String, String> TO_GERMAN = Map.of(
					"Monday", "Montag",
					"Tuesday", "Dienstag",
					"Wednesday", "Mittwoch",
					"Thursday", "Donnerstag",
					"Friday", "Freitag",
					"Saturday", "Samstag",
					"Sunday", "Sonntag"
			);

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
							SuiVBox.vbox(
									Properties.id("vbox"),
									Properties.items(
											SuiButton.button(
													Properties.id("button"),
													Properties.textContent(state.isEnglish() ? "To German" : "To English"),
													EventProperties.eventAction(e -> {
														state.update(TestUIState.class, s -> {
															if (s.getSelected() != null) {
																if (s.isEnglish()) {
																	s.setSelected(TestUIState.TO_GERMAN.get(s.getSelected()));
																} else {
																	s.setSelected(TestUIState.TO_ENGLISH.get(s.getSelected()));
																}
															}
															s.setEnglish(!s.isEnglish());
														});
													})
											),
											SuiComboBox.comboBox(
													Properties.id("combo-box"),
													Properties.contentItems(
															state.isEnglish() ? new ArrayList<>(TestUIState.TO_GERMAN.keySet()) : new ArrayList<>(TestUIState.TO_ENGLISH.keySet()),
															state.getSelected()),
													EventProperties.eventValueChangedType(".", String.class, e -> {
														System.out.println("EVENT " + e.getPrevValue() + " -> " + e.getValue());
														state.update(TestUIState.class, s -> {
															s.setSelected(e.getValue());
														});
													}),
													Properties.searchable(true),
													Properties.editable(false)
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




		@Override
		public void onUnload() {
		}

	}


}
