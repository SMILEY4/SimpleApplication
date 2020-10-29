package de.ruegnerlukas.simpleapplication.simpleui.core.windows;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneControllerListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class SuiWindowsImpl implements SuiWindows {


	/**
	 * The default id of the primary window.
	 */
	public static final String DEFAULT_PRIMARY_WINDOW_ID = "window.id.primary";

	/**
	 * Information about currently open windows
	 */
	private final Map<String, OpenWindowData> openWindows = new HashMap<>();




	/**
	 * Sets the primary javafx window using the {@link SuiWindowsImpl#DEFAULT_PRIMARY_WINDOW_ID}
	 *
	 * @param stage the primary window
	 */
	public void registerPrimaryWindow(final Stage stage) {
		registerPrimaryWindow(DEFAULT_PRIMARY_WINDOW_ID, stage);
	}




	/**
	 * Sets the primary javafx window
	 *
	 * @param primaryWindowId the id to use for the primary window
	 * @param stage           the primary window
	 */
	public void registerPrimaryWindow(final String primaryWindowId, final Stage stage) {
		openWindows.put(primaryWindowId, OpenWindowData.builder()
				.windowId(primaryWindowId)
				.stage(stage)
				.build());
	}




	@Override
	public void openWindow(final WindowConfig windowConfig) {
		if (!openWindows.containsKey(windowConfig.getWindowId())) {

			Validations.INPUT.typeOf(windowConfig, SuiWindowConfig.class)
					.exception("The window config must be of type {}.", SuiWindowConfig.class);

			final SuiWindowConfig config = (SuiWindowConfig) windowConfig;

			if (config.getOwnerWindowId() != null) {
				Validations.INPUT.containsKey(openWindows, config.getOwnerWindowId())
						.exception("The owner window with the id {} is not open.", config.getOwnerWindowId());
			}

			final SuiSceneController controller = createSuiSceneController(config);
			final Scene scene = createScene(controller);
			final SuiSceneControllerListener listener = createSuiSceneControllerListener(scene, controller);
			final Stage stage = createStage(scene, config);

			final OpenWindowData openWindowData = OpenWindowData.builder()
					.windowId(config.getWindowId())
					.controller(controller)
					.controllerListener(listener)
					.stage(stage)
					.build();
			openWindows.put(openWindowData.getWindowId(), openWindowData);

			if (config.isWait()) {
				stage.showAndWait();
			} else {
				stage.show();
			}

		} else {
			log.warn("Can not open window {}. Window with same id is already open.", windowConfig.getWindowId());
		}
	}




	@Override
	public void closeWindow(final String windowId) {
		if (openWindows.containsKey(windowId)) {
			final OpenWindowData openWindowData = openWindows.remove(windowId);
			openWindowData.getController().dispose();
			openWindowData.getController().removeListener(openWindowData.getControllerListener());
			openWindowData.getStage().close();
		} else {
			log.warn("Can not close window {}. Window was not found in open windows.", windowId);
		}
	}




	/**
	 * Creates the simpleui-controller
	 *
	 * @param config the window config
	 * @return the create controller
	 */
	private SuiSceneController createSuiSceneController(final SuiWindowConfig config) {
		Validations.INPUT.notNull(config.getNodeFactory()).exception("the node factory may not be null.");
		return new SuiSceneController(Optional.ofNullable(config.getState()).orElse(new SuiState()), config.getNodeFactory());
	}




	/**
	 * Creates the javafx scene
	 *
	 * @return the create scene
	 */
	private Scene createScene(final SuiSceneController controller) {
		return new Scene((Parent) controller.getRootFxNode());
	}




	/**
	 * Creates the simpleui-controller listener updating the root node of the scene
	 *
	 * @param scene      the javafx scene
	 * @param controller the controller
	 * @return the created listener
	 */
	private SuiSceneControllerListener createSuiSceneControllerListener(final Scene scene, final SuiSceneController controller) {
		final SuiSceneControllerListener listener = suiNode -> scene.setRoot((Parent) suiNode.getFxNodeStore().get());
		controller.addListener(listener);
		return listener;
	}




	/**
	 * Creates the javafx stage
	 *
	 * @param scene  the javafx scene
	 * @param config the window config
	 * @return the create stage
	 */
	private Stage createStage(final Scene scene, final SuiWindowConfig config) {
		final Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle(config.getTitle());
		stage.initModality(Optional.ofNullable(config.getModality()).orElse(Modality.APPLICATION_MODAL));
		stage.initStyle(Optional.ofNullable(config.getWindowStyle()).orElse(StageStyle.DECORATED));
		stage.setAlwaysOnTop(config.isAlwaysOnTop());
		if (config.getSize() != null) {
			stage.setWidth(config.getSize().getWidth());
			stage.setHeight(config.getSize().getHeight());
		}
		if (config.getSizeMin() != null) {
			stage.setMinWidth(config.getSizeMin().getWidth());
			stage.setMinHeight(config.getSizeMin().getHeight());
		}
		if (config.getSizeMax() != null) {
			stage.setMaxWidth(config.getSizeMax().getWidth());
			stage.setMaxHeight(config.getSizeMax().getHeight());
		}
		if (config.getOwnerWindowId() != null) {
			stage.initOwner(openWindows.get(config.getOwnerWindowId()).getStage());
		}

		if (config.getOnClose() != null) {
			stage.setOnCloseRequest(e -> {
				stage.setOnCloseRequest(null);
				config.getOnClose().run();
			});
		}

		return stage;
	}




	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	private static class OpenWindowData {


		/**
		 * the id of the window
		 */
		private String windowId;

		/**
		 * The open window
		 */
		private Stage stage;

		/**
		 * the simpleui-controller
		 */
		private SuiSceneController controller;

		/**
		 * the listener to the simpleui-controller
		 */
		private SuiSceneControllerListener controllerListener;

	}

}
