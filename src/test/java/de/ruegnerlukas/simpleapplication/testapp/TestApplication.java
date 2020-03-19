package de.ruegnerlukas.simpleapplication.testapp;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.StringFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.StringProvider;
import de.ruegnerlukas.simpleapplication.core.application.Application;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConfiguration;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginInformation;
import de.ruegnerlukas.simpleapplication.core.presentation.views.PopupConfiguration;
import de.ruegnerlukas.simpleapplication.core.presentation.views.View;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService;
import de.ruegnerlukas.simpleapplication.core.presentation.views.WindowHandle;
import javafx.geometry.Dimension2D;
import javafx.scene.control.Button;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestApplication {


	public static void main(String[] args) {
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
			eventService.subscribe(Channel.name(ApplicationConstants.EVENT_PRESENTATION_INITIALIZED), e -> createViews());
		}




		private void createViews() {
			log.info("{} creating views.", this.getId());

			final String applicationName = new StringProvider("application_name").get();

			final ViewService viewService = new Provider<>(ViewService.class).get();
			final String ID_A = "plugin.ui.a";
			final String ID_B = "plugin.ui.b";
			final String ID_B_POPUP = "plugin.ui.bpopup";
			final String ID_B_WARN = "plugin.ui.bwarn";

			/*
			Shows the views in the following order:
			ID_A -> ID_B -> (popup: ID_B_POPUP -> ID_B_WARN) -> ID_A
			 */

			// VIEW A

			final Button buttonA = new Button("Switch A -> B");
			buttonA.setOnAction(e -> viewService.showView(ID_B));

			final View viewA = View.builder()
					.id(ID_A)
					.size(new Dimension2D(300, 100))
					.maxSize(new Dimension2D(300, 300))
					.title(applicationName + " - View A")
					.node(buttonA)
					.build();

			// VIEW B

			final Button buttonB = new Button("Switch B -> A");
			buttonB.setOnAction(e -> {
				viewService.popupView(ID_B_POPUP, PopupConfiguration.builder().style(StageStyle.UNDECORATED).wait(false).build());
			});

			final View viewB = View.builder()
					.id(ID_B)
					.size(new Dimension2D(200, 500))
					.title(applicationName + " - View B")
					.node(buttonB)
					.build();


			viewService.registerView(viewA);
			viewService.registerView(viewB);
			viewService.showView(viewA.getId());


			// VIEW B CONFIRM

			final Button buttonBConfirm = new Button("Confirm switch");
			buttonBConfirm.setOnAction(e -> {
				final WindowHandle handlePopup = viewService.getWindowHandles(ID_B_POPUP).get(0);
				viewService.showView(ID_B_WARN, handlePopup);
			});


			final View viewBPopup = View.builder()
					.id(ID_B_POPUP)
					.size(new Dimension2D(300, 200))
					.title(applicationName + " - View B Confirm")
					.node(buttonBConfirm)
					.build();


			final Button buttonBWarn = new Button("You sure ?");
			buttonBWarn.setOnAction(e -> {
				final WindowHandle handlePopup = viewService.getWindowHandles(ID_B_WARN).get(0);
				viewService.closePopup(handlePopup);
				viewService.showView(ID_A);
			});


			final View viewBWarn = View.builder()
					.id(ID_B_WARN)
					.size(new Dimension2D(200, 300))
					.title(applicationName + " - View B LAST WARNING")
					.node(buttonBWarn)
					.build();


			viewService.registerView(viewA);
			viewService.registerView(viewB);
			viewService.registerView(viewBPopup);
			viewService.registerView(viewBWarn);
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
