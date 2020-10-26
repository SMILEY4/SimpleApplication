package de.ruegnerlukas.simpleapplication.simpleui.core.windows;

import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SuiWindows {


	private static final Map<String, Stage> openStages = new HashMap<>();

	private static final Map<String, SuiSceneController> openControllers = new HashMap<>();




	public static void initializePrimaryWindow(final String primaryWindowId, final Stage stage) {
		openStages.put(primaryWindowId, stage);
	}




	public static void open(final WindowConfig config) {

		final SuiSceneController controller = new SuiSceneController(config.getState(), config.getNodeFactory());

		final Scene scene = new Scene((Parent) controller.getRootFxNode());
		controller.addListener(suiNode -> scene.setRoot((Parent) suiNode.getFxNodeStore().get())); // todo: remove listener when closing ?

		final Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle(config.getTitle());
		stage.setWidth(config.getWidth());
		stage.setHeight(config.getHeight());
		stage.initModality(config.getModality());
		stage.initOwner(openStages.get(config.getOwnerWindowId()));

		openStages.put(config.getWindowId(), stage);
		openControllers.put(config.getWindowId(), controller);

		if (config.isWait()) {
			stage.showAndWait();
		} else {
			stage.show();
		}
	}




	public static void close(final String windowId) {
		final Stage stage = openStages.remove(windowId);
		final SuiSceneController controller = openControllers.remove(windowId);
		controller.getState().removeStateListener(controller);
		stage.close();
	}

}
