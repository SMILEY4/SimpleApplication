package de.ruegnerlukas.simpleapplication.testapp;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.core.application.Application;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginFinder;
import de.ruegnerlukas.simpleapplication.core.presentation.views.View;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService;
import javafx.scene.control.Button;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestApplication {


	public static void main(String[] args) {

		final PluginFinder pluginFinder = new PluginFinder() {
			@Override
			public void findPlugins() {
				add(new LoggingPlugin());
				add(new UIPlugin());
			}
		};

		new Application(pluginFinder).run();
	}




	/**
	 * responsible for displaying the ui
	 */
	@Slf4j
	static class UIPlugin extends Plugin {


		public UIPlugin() {
			super("core.ui", "UI Plugin", "0.1");
		}




		@Override
		public void onLoad() {
			final EventService eventService = new Provider<>(EventService.class).get();
			eventService.getEvent("presentation_initialized").subscribe(e -> createViews());
		}




		private void createViews() {
			log.info("{} creating views.", this.getId());

			final ViewService viewService = new Provider<>(ViewService.class).get();
			final String ID_A = "core.ui.a";
			final String ID_B = "core.ui.b";

			final Button buttonA = new Button("Switch A -> B");
			buttonA.setOnAction(e -> viewService.showViewPrimary(ID_B));

			final View viewA = View.builder()
					.id(ID_A)
					.width(300)
					.height(100)
					.title("View A")
					.node(buttonA)
					.build();


			final Button buttonB = new Button("Switch B -> A");
			buttonB.setOnAction(e -> viewService.showViewPrimary(ID_A));

			final View viewB = View.builder()
					.id(ID_B)
					.width(200)
					.height(500)
					.title("View AB")
					.node(buttonB)
					.build();


			viewService.registerView(viewA);
			viewService.registerView(viewB);
			viewService.showViewPrimary(viewA.getId());
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
			super("core.logging", "Logging Plugin", "0.1");
		}




		@Override
		public void onLoad() {
			log.info("LOGGING: onLoad {}.", getId());
		}




		@Override
		public void onUnload() {
			log.info("LOGGING: onUnload {}.", getId());
		}

	}


}
