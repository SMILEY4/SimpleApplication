package de.ruegnerlukas.simpleapplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestApplication {


	public static void main(String[] args) {
//		SimpleApplication.setPresentationConfig(PresentationConfig.builder()
//				.id("test_scene")
//				.title("Test App")
//				.width(600)
//				.height(450)
//				.baseModule(new ModuleFactory() {
//					@Override
//					public UIModule module() {
//						return new UIModule(
//								new ModuleView() {
//									private Button btnExit;
//
//									private EmptyEvent.EmptyEventSource btnExitEvent = new EmptyEvent.EmptyEventSource();
//
//
//
//
//									@Override
//									public void initializeView(final Pane moduleRoot) {
//										log.info("Hello from init view.");
//										btnExit = new Button("Exit");
//										moduleRoot.getChildren().add(btnExit);
//										btnExit.setOnAction(e -> {
//											btnExitEvent.trigger();
//										});
//									}
//
//
//
//
//									@Override
//									public Map<String, ListenableEventSource<?>> getEventEndpoints() {
//										return Map.of("on_exit", btnExitEvent);
//									}
//
//
//
//
//									@Override
//									public Map<String, TriggerableEventSource<?>> getFunctionEndpoints() {
//										return Map.of();
//									}
//
//
//								},
//								new ModuleController() {
//									@Override
//									public void initialize(ListenableEventSourceGroup events, TriggerableEventSourceGroup functions) {
//										events.find("on_exit").subscribe(event -> {
//											log.info("Pressed the exit button.");
//											SimpleApplication.stopApplication();
//										});
//									}
//
//
//
//
//									@Override
//									public List<ExtensionPoint> getExtensionPoints() {
//										return List.of();
//									}
//								}
//						);
//					}
//				})
//				.build());
//
//		SimpleApplication.getPluginManager().register(new Plugin("test_plugin", "dev", "Test Plugin", new String[]{}) {
//			@Override
//			public boolean onLoad() {
//				log.info("Hello from loading the plugin.");
//				return true;
//			}
//
//
//
//
//			@Override
//			public void onUnload() {
//				log.info("Hello from unloading the plugin");
//			}
//		});
//
//		SimpleApplication.startApplication();
	}


}
