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
import de.ruegnerlukas.simpleapplication.simpleui.SuiSceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiButton;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiTextField;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static de.ruegnerlukas.simpleapplication.simpleui.elements.SuiAnchorPane.anchorPane;
import static de.ruegnerlukas.simpleapplication.simpleui.elements.SuiAnchorPane.anchorPaneItem;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.alignment;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.editable;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.promptText;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.textContent;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.events.EventProperties.eventTextEntered;

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
		private static class TestUIState extends SuiState {



			private String text = "Sample Text";




			public void setText(final String text) {
				log.info("Setting text to \"{}\".", text);
				this.text = text;
			}

		}




		private void createViews() {
			log.info("{} creating views.", this.getId());


			final TestUIState testUIState = new TestUIState();

			final View view = View.builder()
					.id("sui.test.view")
					.size(new Dimension2D(500, 400))
					.maxSize(new Dimension2D(600, 600))
					.title(new StringProvider("application_name").get())
					.icon(Resource.internal("testResources/icon.png"))
					.dataFactory(new SUIWindowHandleDataFactory(() -> new SuiSceneContext(testUIState, TestUIState.class, state ->
							anchorPane(
									Properties.items(
											anchorPaneItem(
													SuiTextField.textField(
															textContent(state.getText()),
															promptText("Text Field"),
															eventTextEntered(e -> state.update(TestUIState.class, updateState -> updateState.setText(e.getText())))
													),
													Properties.anchor(100, null, 100, null)
											),
											anchorPaneItem(
													SuiTextField.textField(
															textContent(state.getText()),
															alignment(Pos.CENTER),
															editable(false)
													),
													Properties.anchor(160, null, 100, null)
											)
									)
							)

					)))
					.build();

			final ViewService viewService = new Provider<>(ViewService.class).get();
			viewService.registerView(view);
			viewService.showView(view.getId());

		}




		@Override
		public void onUnload() {
		}

	}


}
