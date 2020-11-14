package de.ruegnerlukas.simpleapplication.testapp;

import de.ruegnerlukas.simpleapplication.common.eventbus.EventBus;
import de.ruegnerlukas.simpleapplication.common.eventbus.SubscriptionData;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.StringFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.utils.Pair;
import de.ruegnerlukas.simpleapplication.core.application.Application;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConfiguration;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.core.application.EventApplicationStarted;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginInformation;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.InjectionIndexMarker;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.button;
import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.choiceBox;
import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.component;
import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.hBox;
import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.label;
import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.scrollPane;
import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.slider;
import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.vBox;
import static de.ruegnerlukas.simpleapplication.core.simpleui.core.node.WindowRootElement.windowRoot;

public class TestApplicationV2 {


	public static void main(String[] args) {
		SuiRegistry.initialize();

		final ApplicationConfiguration configuration = new ApplicationConfiguration();
		configuration.getPlugins().add(new EventLoggingPlugin());
		configuration.getPlugins().add(new UIPlugin());
		configuration.getProviderFactories().add(new StringFactory("application_name", "Test App V2"));
		new Application(configuration).run();
	}




	@Getter
	@Setter
	@ToString
	private static class TestUIState extends SuiState {


		private boolean showPopupEnableAutosave = false;

		private boolean showPopupAlwaysOnTop = false;

		private List<Pair<String, Long>> content = new ArrayList<>();

	}






	@Slf4j
	static class UIPlugin extends Plugin {


		public UIPlugin() {
			super(new PluginInformation("plugin.ui", "UI Plugin", "0.1", false));
		}




		@Override
		public void onLoad() {
			final EventBus eventBus = new Provider<>(EventBus.class).get();
			eventBus.subscribe(
					SubscriptionData.ofType(EventApplicationStarted.class),
					e -> createViews(new Provider<Stage>(ApplicationConstants.PROVIDED_PRIMARY_STAGE).get()));
		}




		@Override
		public void onUnload() {
		}




		private void createViews(final Stage stage) {
			final TestUIState testUIState = new TestUIState();
			final SuiSceneController controller = new SuiSceneController(
					testUIState,
					windowRoot(stage)
							.title("JFX Clipboard Testing")
							.size(400, 500)
							.content(TestUIState.class, UIPlugin::createUI)
							.modal(windowRoot()
									.title("NotImplemented: Enable Autosave")
									.size(400, 100)
									.condition(TestUIState.class, state -> state.showPopupEnableAutosave)
									.onClose(TestUIState.class, state -> state.setShowPopupEnableAutosave(false))
									.content(TestUIState.class, state -> createPopupUI(state, "Enable Autosave")))
							.modal(windowRoot()
									.title("NotImplemented: Always on top")
									.size(400, 100)
									.condition(TestUIState.class, state -> state.showPopupAlwaysOnTop)
									.onClose(TestUIState.class, state -> state.setShowPopupAlwaysOnTop(false))
									.content(TestUIState.class, state -> createPopupUI(state, "Always on top")))
			);

			SuiRegistry.get().inject("ij-point.toolbar",
					component(TestUIState.class,
							state -> button()
									.id("btn.enable-auto")
									.sizeMax(150, 30)
									.hGrow(Priority.ALWAYS)
									.textContent("Enable Autosave")
									.eventAction(".", e -> state.update(TestUIState.class, s -> s.setShowPopupEnableAutosave(true)))));

			SuiRegistry.get().inject("ij-point.toolbar",
					component(TestUIState.class,
							state -> choiceBox()
									.id("cb.test")
									.contentItems(List.of("a", "b", "c", "d"), "b")));

			controller.show();
		}




		private static NodeFactory createPopupUI(final TestUIState state, final String str) {
			return vBox()
					.items(
							label()
									.anchorsFitParent()
									.textContent("Not Implemented: " + str),
							slider()
					);
		}




		private static NodeFactory createUI(final TestUIState state) {
			return vBox()
					.id("root")
					.fitToWidth()
					.items(
							hBox()
									.id("toolbar")
									.spacing(5)
									.backgroundSolid(Color.LIGHTGRAY)
									.sizeMin(0, 40)
									.sizeMax(100000, 40)
									.alignment(Pos.CENTER)
									.itemsInjectable(
											"ij-point.toolbar",
											InjectionIndexMarker.injectLast(),
											button()
													.id("btn.save")
													.sizeMax(150, 30)
													.hGrow(Priority.ALWAYS)
													.textContent("Save Clipboard")
													.eventAction(".", e -> saveClipboard(state))
									),
							scrollPane()
									.id("content.scroll")
									.sizePreferred(1000000, 1000000)
									.fitToWidth()
									.fitToHeight()
									.item(
											vBox()
													.id("content.box")
													.items(state.getContent().stream().map(e -> buildContentItem(state, e)))
									)
					);
		}




		private static NodeFactory buildContentItem(final TestUIState state, final Pair<String, Long> content) {
			return hBox()
					.id("item-" + content.getLeft().hashCode() + "-" + content.getRight())
					.sizeMax(1000000, 50)
					.sizeMin(0, 50)
					.alignment(Pos.CENTER_LEFT)
					.spacing(5)
					.items(
							label()
									.id("content.label")
									.sizePreferred(1000000, 45)
									.textContent(content.getLeft()),
							button()
									.id("content.copy")
									.textContent("C")
									.sizeMin(35, 35)
									.sizeMax(35, 35)
									.eventAction(".", e -> log.debug("NotImplemented: Copy content to clipboard")),
							button()
									.id("content.remove")
									.textContent("X")
									.sizeMin(35, 35)
									.sizeMax(35, 35)
									.eventAction(".", e -> removeClipboardItem(state, content))
					);
		}




		private static void saveClipboard(final TestUIState state) {
			state.update(TestUIState.class, s -> {
				try {
					final String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
					s.getContent().add(0, Pair.of(data, System.currentTimeMillis()));
					log.info("Clipboard saved: " + data.substring(0, Math.min(data.length(), 20)));
				} catch (UnsupportedFlavorException | IOException e) {
					log.warn("Error saving clipboard: {}", e.getMessage());
				}
			});
		}




		private static void removeClipboardItem(final TestUIState state, final Pair<String, Long> content) {
			state.update(TestUIState.class, s -> {
				s.getContent().removeIf(
						e -> e.getLeft().equals(content.getLeft()) && e.getRight().longValue() == content.getRight().longValue());
				log.info("Content removed: " + content.getLeft().substring(0, Math.min(content.getLeft().length(), 20)));
			});
		}


	}






	@Slf4j
	static class EventLoggingPlugin extends Plugin {


		public EventLoggingPlugin() {
			super(new PluginInformation("plugin.event-logging", "Event Logging Plugin", "0.1", false));
		}




		@Override
		public void onLoad() {
			log.info("LOGGING: onLoad {}.", getId());
			final EventBus eventBus = new Provider<>(EventBus.class).get();
			eventBus.subscribe(SubscriptionData.anyType(), this::onEvent);
		}




		private void onEvent(final Object event) {
			log.info("LOGGING: event in channel='{}'", event);
		}




		@Override
		public void onUnload() {
			log.info("LOGGING: onUnload {}.", getId());
		}

	}


}
