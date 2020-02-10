package de.ruegnerlukas.simpleapplication.core.presentation.views;

import de.ruegnerlukas.simpleapplication.common.events.EventPackage;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ViewService {


	/**
	 * The provider for the {@link EventService}.
	 */
	private final Provider<EventService> eventServiceProvider = new Provider<>(EventService.class);

	/**
	 * The primary stage of the (javafx-) application
	 */
	private Stage primaryStage = null;

	/**
	 * The id of the view currently shown in the primary stage (or null).
	 */
	private String currentPrimaryViewId = null;

	/**
	 * All registered views.
	 */
	private Map<String, View> views = new HashMap<>();




	/**
	 * Initializes this view service with the primary stage of the javafx application.
	 *
	 * @param stage             the primary stage
	 * @param showViewAtStartup whether to show a view at startup or hide the window
	 * @param view              the view to show at startup (or null)
	 */
	public void initialize(final Stage stage, final boolean showViewAtStartup, final View view) {
		Validations.INPUT.notNull(stage).exception("The stage can not be null.");
		Validations.STATE.isNull(primaryStage).exception("The view service was already initialized.");

		View startupView = view;
		if (startupView == null) {
			startupView = new EmptyView();
		}
		registerView(startupView);

		final Scene scene = new Scene(startupView.getNode(), startupView.getWidth(), startupView.getHeight());
		this.primaryStage = stage;
		this.primaryStage.setScene(scene);
		this.primaryStage.setTitle(startupView.getTitle());
		if (showViewAtStartup) {
			currentPrimaryViewId = startupView.getId();
			this.primaryStage.show();
		}
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
		eventShowViewPrimaryPre(currentPrimaryViewId, viewId);
		setView(primaryStage, views.get(viewId));
		final String lastViewId = currentPrimaryViewId;
		currentPrimaryViewId = viewId;
		eventShowViewPrimaryPost(lastViewId, currentPrimaryViewId);
	}




	/**
	 * Triggers the {@link ApplicationConstants#EVENT_SHOW_VIEW_PRIMARY_PRE}-event.
	 *
	 * @param viewIdCurrent the id of the current view
	 * @param viewIdNext    the id of the next view
	 */
	private void eventShowViewPrimaryPre(final String viewIdCurrent, final String viewIdNext) {
		eventServiceProvider.get().publish(ApplicationConstants.EVENT_SHOW_VIEW_PRIMARY_PRE,
				new EventPackage<>(new String[]{viewIdCurrent, viewIdNext}));
	}




	/**
	 * Triggers the {@link ApplicationConstants#EVENT_SHOW_VIEW_PRIMARY_POST}-event.
	 *
	 * @param viewIdLast    the id of the last view
	 * @param viewIdCurrent the id of the current view
	 */
	private void eventShowViewPrimaryPost(final String viewIdLast, final String viewIdCurrent) {
		eventServiceProvider.get().publish(ApplicationConstants.EVENT_SHOW_VIEW_PRIMARY_POST,
				new EventPackage<>(new String[]{viewIdLast, viewIdCurrent}));
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
		if (!stage.isShowing()) {
			stage.show();
		}
		log.info("Show view {}.", view.getId());
	}

}
