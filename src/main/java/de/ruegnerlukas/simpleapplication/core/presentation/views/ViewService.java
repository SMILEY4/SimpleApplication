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


	private Stage primaryStage = null;

	private Map<String, View> views = new HashMap<>();




	public void initializePrimary(final Stage stage) {
		Validations.INPUT.notNull(stage).exception("The stage can not be null.");
		this.primaryStage = stage;
		this.primaryStage.setScene(new Scene(new AnchorPane(), 500, 400));
		this.primaryStage.setTitle("Application");
		this.primaryStage.show();
	}




	public void registerView(final View view) {
		Validations.INPUT.notNull(view).exception("The view can not be null.");
		views.put(view.getId(), view);
		log.info("Registered view {}.", view.getId());
	}




	public void showViewPrimary(final String viewId) {
		Validations.INPUT.notBlank(viewId).exception("The view id can not be null or empty.");
		Validations.INPUT.containsKey(views, viewId).exception("Could not find a view with id {}.", viewId);
		Validations.PRESENCE.notNull(primaryStage).exception("The primary stage is not yet set.");
		setView(primaryStage, views.get(viewId));
	}







	private void setView(final Stage stage, final View view) {
		Scene scene = stage.getScene();
		scene.setRoot(view.getNode());
		stage.setWidth(view.getWidth());
		stage.setHeight(view.getHeight());
		stage.setTitle(view.getTitle());
		log.info("Show view {}.", view.getId());
	}

}
