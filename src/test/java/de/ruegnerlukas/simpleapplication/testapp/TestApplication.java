package de.ruegnerlukas.simpleapplication.testapp;

import de.ruegnerlukas.simpleapplication.common.events.EventPackage;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.StringFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.StringProvider;
import de.ruegnerlukas.simpleapplication.core.application.Application;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConfiguration;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.presentation.views.View;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewHandle;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService;
import javafx.scene.control.Button;
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
			super("plugin.ui", "UI Plugin", "0.1");
		}




		@Override
		public void onLoad() {
			final EventService eventService = new Provider<>(EventService.class).get();
			eventService.subscribe(ApplicationConstants.EVENT_PRESENTATION_INITIALIZED, e -> createViews());
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
			ID_A -> ID_B -> (popup: ID_B_POPUP -> ID_B_WARN) -> ID_A
			 */

			// VIEW A

			final Button buttonA = new Button("Switch A -> B");
			buttonA.setOnAction(e -> viewService.showView(ID_B));

			final View viewA = View.builder()
					.id(ID_A)
					.width(300)
					.height(100)
					.title(applicationName + " - View A")
					.node(buttonA)
					.build();


			// VIEW B

			final Button buttonB = new Button("Switch B -> A");
			buttonB.setOnAction(e -> viewService.popupView(ID_B_POPUP, false));

			final View viewB = View.builder()
					.id(ID_B)
					.width(200)
					.height(500)
					.title(applicationName + " - View B")
					.node(buttonB)
					.build();


			viewService.registerView(viewA);
			viewService.registerView(viewB);
			viewService.showView(viewA.getId());


			// VIEW B CONFIRM

			final Button buttonBConfirm = new Button("Confirm switch");
			buttonBConfirm.setOnAction(e -> {
				final ViewHandle handlePopup = viewService.getViewHandles(ID_B_POPUP).get(0);
				viewService.showView(ID_B_WARN, handlePopup);
			});


			final View viewBPopup = View.builder()
					.id(ID_B_POPUP)
					.width(300)
					.height(200)
					.title(applicationName + " - View B Confirm")
					.node(buttonBConfirm)
					.build();


			final Button buttonBWarn = new Button("You sure ?");
			buttonBWarn.setOnAction(e -> {
				final ViewHandle handlePopup = viewService.getViewHandles(ID_B_WARN).get(0);
				viewService.closePopup(handlePopup);
				viewService.showView(ID_A);
			});


			final View viewBWarn = View.builder()
					.id(ID_B_WARN)
					.width(300)
					.height(200)
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
			super("plugin.logging", "Logging Plugin", "0.1");
		}




		@Override
		public void onLoad() {
			log.info("LOGGING: onLoad {}.", getId());
			final EventService eventService = new Provider<>(EventService.class).get();
			eventService.subscribe(this::onEvent);
		}




		private void onEvent(final EventPackage<Object> eventPackage) {
			log.info("LOGGING: event: channel(s)='{}'; receivers='{}'; time='{}': '{}'",
					String.join(", ", eventPackage.getChannels()),
					eventPackage.getReceivers(),
					eventPackage.getTimestamp(),
					eventPackage.getEvent());
		}




		@Override
		public void onUnload() {
			log.info("LOGGING: onUnload {}.", getId());
		}

	}


}
