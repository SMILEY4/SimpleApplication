package de.ruegnerlukas.simpleapplication.simpleui.core.windows;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.geometry.Dimension2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
public class SuiWindowManager implements WindowManager {


	/**
	 * A map with the controller for each opened stage
	 */
	private final Map<Stage, SuiSceneController> controllerMap = new HashMap<>();




	@Override
	public void openNew(final WindowOpenData windowOpenData) {
		Validations.INPUT.notNull(windowOpenData).exception("The window data may not be null.");
		Validations.INPUT.typeOf(windowOpenData, SuiWindowOpenData.class)
				.exception("Window-open-data not of type {}", SuiWindowOpenData.class);
		openNew((SuiWindowOpenData) windowOpenData);
	}




	/**
	 * Opens the new window
	 *
	 * @param windowOpenData information about the window
	 */
	private void openNew(final SuiWindowOpenData windowOpenData) {
		final SuiSceneController controller = new SuiSceneController(
				Optional.ofNullable(windowOpenData.getState()).orElse(new SuiState()),
				Optional.ofNullable(windowOpenData.getRootNodeFactory()).orElse(SuiElements.anchorPane()));

		final Scene scene = createScene(controller, windowOpenData.getSize());
		final Stage stage = createStage(scene, windowOpenData.getTitle(), windowOpenData.getOwner(), windowOpenData.getModality());

		stage.setOnCloseRequest(e -> {
			Optional.ofNullable(windowOpenData.getOnClose()).ifPresent(onClose -> onClose.accept(stage));
			stage.setOnCloseRequest(null);
			onClose(stage);
		});

		log.debug("Opening new window: title={}", windowOpenData.getTitle());
		controllerMap.put(stage, controller);
		showStage(stage, windowOpenData.isWait(), windowOpenData.getOnOpen());
	}




	/**
	 * Creates a new scene with the contents of the controller and the given size
	 *
	 * @param controller the controller providing the contents of the scene
	 * @param size       the width and height of the scene
	 * @return the created scene
	 */
	private Scene createScene(final SuiSceneController controller, final Dimension2D size) {
		final Scene scene = new Scene(
				(Parent) controller.getRootFxNode(),
				Optional.ofNullable(size).map(Dimension2D::getWidth).orElse(300.0),
				Optional.ofNullable(size).map(Dimension2D::getHeight).orElse(300.0));
		controller.addListener(newRoot -> scene.setRoot((Parent) newRoot.getFxNodeStore().get()));
		return scene;
	}




	/**
	 * Creates a new stage
	 *
	 * @param scene    the scene
	 * @param title    the title of the window
	 * @param owner    the owner of the stage
	 * @param modality the modality
	 * @return the created stage
	 */
	private Stage createStage(final Scene scene, final String title, final Window owner, final Modality modality) {
		final Stage stage = new Stage();
		stage.setTitle(title);
		stage.setScene(scene);
		stage.initOwner(owner);
		stage.initModality(modality);
		return stage;
	}




	/**
	 * Shows the given stage and waits if required.
	 *
	 * @param stage  the stage to show
	 * @param wait   whether to wait for the stage to close
	 * @param onOpen a consumer called when the stage is shown or null
	 */
	private void showStage(final Stage stage, final boolean wait, final Consumer<Stage> onOpen) {
		if (wait) {
			Optional.ofNullable(onOpen).ifPresent(consumer -> consumer.accept(stage));
			stage.showAndWait();
		} else {
			stage.show();
			Optional.ofNullable(onOpen).ifPresent(consumer -> consumer.accept(stage));
		}
	}




	@Override
	public void close(final WindowCloseData windowCloseData) {
		Validations.INPUT.notNull(windowCloseData).exception("The window data may not be null.");
		Validations.INPUT.typeOf(windowCloseData, SuiWindowCloseData.class)
				.exception("Window-close-data not of type {}", SuiWindowCloseData.class);
		close((SuiWindowCloseData) windowCloseData);
	}




	/**
	 * Closes an open window
	 *
	 * @param windowCloseData info about the window to close.
	 */
	private void close(final SuiWindowCloseData windowCloseData) {
		final Stage stage = windowCloseData.getWindow();
		if (stage != null && stage.isShowing()) {
			onClose(stage);
			stage.close();
		}
	}




	/**
	 * Called when closing a window/stage.
	 *
	 * @param stage the closed stage
	 */
	private void onClose(final Stage stage) {
		log.debug("Properly closing window: title={}.", stage.getTitle());
		controllerMap.remove(stage).dispose();
	}


}
