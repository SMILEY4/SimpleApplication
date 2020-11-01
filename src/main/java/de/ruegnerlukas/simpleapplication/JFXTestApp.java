package de.ruegnerlukas.simpleapplication;


import de.ruegnerlukas.simpleapplication.common.utils.Pair;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.InjectionIndexMarker;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.button;
import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.hBox;
import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.label;
import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.scrollPane;
import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.vBox;

@Slf4j
@SuppressWarnings ("ALL")
public class JFXTestApp extends Application {


	public static void main(String[] args) {
		launch(args);
	}




	@Getter
	@Setter
	private static class TestUIState extends SuiState {


		private List<Pair<String, Long>> content = new ArrayList<>();

	}




	@Override
	public void start(final Stage stage) throws Exception {

		SuiRegistry.initialize();

		final TestUIState testUIState = new TestUIState();
		final SuiSceneController controller = new SuiSceneController(testUIState, TestUIState.class, JFXTestApp::createUI);

		SuiRegistry.get().inject("ij-point.toolbar",
				button()
						.id("btn.enable-auto")
						.sizeMax(150, 30)
						.hGrow(Priority.ALWAYS)
						.textContent("Enable Autosave")
						.eventAction(".", e -> log.debug("NotImplemented: Enable Autosave")));

		SuiRegistry.get().inject("ij-point.toolbar",
				button()
						.id("btn.always-on-top")
						.sizeMax(150, 30)
						.hGrow(Priority.ALWAYS)
						.textContent("Always On Top")
						.eventAction(".", e -> log.debug("NotImplemented: Always on top")));

		final Scene scene = new Scene((Parent) controller.getRootFxNode(), 500, 600);
		stage.setScene(scene);
		stage.show();
	}




	private static NodeFactory createUI(final TestUIState state) {
		return vBox()
				.id("root")
				.fitToWidth()
				.items(
						hBox()
								.id("toolbar")
								.spacing(5)
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
