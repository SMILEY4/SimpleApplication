package de.ruegnerlukas.simpleapplication.core.presentation.views;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ViewService {


	/**
	 * The primary stage of the (javafx-) application
	 */
	private Stage primaryStage = null;

	/**
	 * All registered views.
	 */
	private Map<String, View> views = new HashMap<>();

	/**
	 * The default width of the stage when no view is shown.
	 */
	private static final int DEFAULT_WIDTH = 500;

	/**
	 * The default height of the stage when no view is shown.
	 */
	private static final int DEFAULT_HEIGHT = 400;

	/**
	 * The default title of the stage when no view is shown.
	 */
	private static final String DEFAULT_TITLE = "";




	/**
	 * Initializes this view service with the primary stage of the javafx application.
	 *
	 * @param stage the primary stage
	 */
	public void initializePrimary(final Stage stage) {
		Validations.INPUT.notNull(stage).exception("The stage can not be null.");
		Validations.STATE.isNull(primaryStage).exception("The view service was already initialized.");
		this.primaryStage = stage;
		this.primaryStage.setScene(new Scene(new AnchorPane(), DEFAULT_WIDTH, DEFAULT_HEIGHT));
		this.primaryStage.setTitle(DEFAULT_TITLE);
		this.primaryStage.show();
	}




	/**
	 * Registers the given view.
	 *
	 * @param view the view to register
	 */
	public void registerView(final View view) {
		Validations.INPUT.notNull(view).exception("The view can not be null.");
		views.put(view.getId(), view);
		log.info("Registered view {}.", view.getId());
	}




	/**
	 * Shows the view with the given id in the primary stage/window.
	 *
	 * @param viewId the id of the view
	 */
	public void showViewPrimary(final String viewId) {
		Validations.INPUT.notBlank(viewId).exception("The view id can not be null or empty.");
		Validations.INPUT.containsKey(views, viewId).exception("Could not find a view with id {}.", viewId);
		Validations.PRESENCE.notNull(primaryStage).exception("The primary stage is not yet set.");
		setView(primaryStage, views.get(viewId));
	}




	/**
	 * Shows the given view in the given stage.
	 *
	 * @param stage the javafx stage
	 * @param view  the view to show
	 */
	private void setView(final Stage stage, final View view) {
		Scene scene = stage.getScene();
		scene.setRoot(view.getNode());
		stage.setWidth(view.getWidth());
		stage.setHeight(view.getHeight());
		stage.setTitle(view.getTitle());
		log.info("Show view {}.", view.getId());
	}

}
