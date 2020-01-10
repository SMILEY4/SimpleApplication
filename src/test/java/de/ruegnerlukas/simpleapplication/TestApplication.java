package de.ruegnerlukas.simpleapplication;

import de.ruegnerlukas.simpleapplication.common.events.GenericEventSource;
import de.ruegnerlukas.simpleapplication.common.events.ListenableEventSource;
import de.ruegnerlukas.simpleapplication.common.events.ListenableEventSourceGroup;
import de.ruegnerlukas.simpleapplication.common.events.TriggerableEventSource;
import de.ruegnerlukas.simpleapplication.common.events.TriggerableEventSourceGroup;
import de.ruegnerlukas.simpleapplication.common.events.events.EmptyEvent;
import de.ruegnerlukas.simpleapplication.common.extensions.ExtensionPoint;
import de.ruegnerlukas.simpleapplication.common.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.presentation.PresentationConfig;
import de.ruegnerlukas.simpleapplication.core.presentation.uimodule.ModuleController;
import de.ruegnerlukas.simpleapplication.core.presentation.uimodule.ModuleFactory;
import de.ruegnerlukas.simpleapplication.core.presentation.uimodule.ModuleView;
import de.ruegnerlukas.simpleapplication.core.presentation.uimodule.UIModule;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class TestApplication {


	public static void main(String[] args) {
		SimpleApplication.setPresentationConfig(PresentationConfig.builder()
				.id("test_scene")
				.title("Test App")
				.width(600)
				.height(450)
				.baseModule(new ModuleFactory() {
					@Override
					public UIModule module() {
						return new UIModule(
								new ModuleView() {
									private Button btnExit;

									private GenericEventSource<EmptyEvent> btnExitEvent = new GenericEventSource<>();




									@Override
									public void initializeView(final Pane moduleRoot) {
										log.info("Hello from init view.");
										btnExit = new Button("Exit");
										moduleRoot.getChildren().add(btnExit);
										btnExit.setOnAction(e -> {
											btnExitEvent.trigger(new EmptyEvent());
										});
									}




									@Override
									public Map<String, ListenableEventSource> getEventEndpoints() {
										return Map.of("on_exit", btnExitEvent);
									}




									@Override
									public Map<String, TriggerableEventSource> getFunctionEndpoints() {
										return Map.of();
									}


								},
								new ModuleController() {
									@Override
									public void initialize(ListenableEventSourceGroup events, TriggerableEventSourceGroup functions) {
										events.getEventSource("on_exit").subscribe(event -> {
											log.info("Pressed the exit button.");
											SimpleApplication.stopApplication();
										});
									}




									@Override
									public List<ExtensionPoint> getExtensionPoints() {
										return List.of();
									}
								}
						);
					}
				})
				.build());

		SimpleApplication.getPluginManager().register(new Plugin("test_plugin", "dev", "Test Plugin", new String[]{}) {
			@Override
			public boolean onLoad() {
				log.info("Hello from loading the plugin.");
				return true;
			}




			@Override
			public void onUnload() {
				log.info("Hello from unloading the plugin");
			}
		});

		SimpleApplication.startApplication();
	}


}
