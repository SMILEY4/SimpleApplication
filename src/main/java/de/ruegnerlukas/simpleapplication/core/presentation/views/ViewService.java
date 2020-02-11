package de.ruegnerlukas.simpleapplication.core.presentation.views;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	 * All registered views.
	 */
	private final Map<String, View> views = new HashMap<>();

	/**
	 * All view handles of the currently visible views.
	 */
	private final Map<String, ViewHandle> viewHandles = new HashMap<>();




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
	 * Deregister the given view.
	 *
	 * @param viewId the id of the view to deregister
	 */
	public void deregisterView(final String viewId) {
		Validations.STATE.isEmpty(getViewHandles(viewId)).exception("Cannot deregister a view that is currently in use.");
		if (views.remove(viewId) != null) {
			log.info("Deregister view {}.", viewId);
		}
	}




	/**
	 * Shows the view with the given id in the primary window.
	 *
	 * @param viewId the id of the view
	 */
	public ViewHandle showView(final String viewId) {
		Validations.INPUT.notBlank(viewId).exception("The view id can not be null or empty.");
		Validations.INPUT.containsKey(views, viewId).exception("Could not find a view with id {}.", viewId);
		Validations.PRESENCE.notNull(primaryStage).exception("The primary stage is not yet set.");
		final ViewHandle viewHandle = new StageViewHandle(ViewHandle.ID_PRIMARY, views.get(viewId), primaryStage);
		show(viewHandle);
		return viewHandle;
	}




	/**
	 * Replaces the view of the given handle with the view with the given id.
	 *
	 * @param viewId the id of the view
	 * @param handle the handle of the view to replace
	 */
	public ViewHandle showView(final String viewId, final ViewHandle handle) {
		Validations.INPUT.notBlank(viewId).exception("The view id can not be null or empty.");
		Validations.INPUT.notNull(handle).exception("The handle can not be null.");
		Validations.INPUT.containsKey(views, viewId).exception("Could not find a view with id {}.", viewId);
		ViewHandle viewHandle;
		if (handle instanceof PopupViewHandle) {
			viewHandle = new PopupViewHandle(handle.getHandleId(), views.get(viewId), null); // TODO
		} else {
			viewHandle = new StageViewHandle(handle.getHandleId(), views.get(viewId), handle.getStage());
		}
		show(viewHandle);
		return viewHandle;
	}




	/**
	 * Shows the view with the given id in a new popup window.
	 *
	 * @param viewId the id of the view
	 * @param wait   whether to wait for the popup to close
	 */
	public ViewHandle popupView(final String viewId, final boolean wait) {
		Validations.INPUT.notBlank(viewId).exception("The view id can not be null or empty.");
		Validations.INPUT.containsKey(views, viewId).exception("Could not find a view with id {}.", viewId);
		Validations.PRESENCE.notNull(primaryStage).exception("The primary stage is not yet set.");
		return popupView(viewId, viewHandles.get(ViewHandle.ID_PRIMARY), wait);
	}




	/**
	 * Shows the view with the given id in a new popup window with the given view-binding id as the parent.
	 *
	 * @param viewId the id of the view
	 * @param parent the {@link ViewHandle} of the parent stage/popup
	 * @param wait   whether to wait for the popup to close
	 */
	public ViewHandle popupView(final String viewId, final ViewHandle parent, final boolean wait) {
		Validations.INPUT.notBlank(viewId).exception("The view id can not be null or empty.");
		Validations.INPUT.containsKey(views, viewId).exception("Could not find a view with id {}.", viewId);
		Validations.PRESENCE.notNull(primaryStage).exception("The primary stage is not yet set.");
		final Popup popup = new Popup(parent.getStage(), views.get(viewId), wait);
		final ViewHandle viewHandle = new PopupViewHandle(
				viewId + "handle" + Integer.toHexString(popup.hashCode()), views.get(viewId), popup);
		show(viewHandle);
		return viewHandle;
	}




	/**
	 * Closes the popup with the given view handle.
	 *
	 * @param viewHandle the handle of the popup
	 */
	public void closePopup(final ViewHandle viewHandle) {
		Validations.INPUT.notNull(viewHandle).exception("The view handle can not be null.");
		Validations.INPUT.typeOf(viewHandle, PopupViewHandle.class).exception("The view handle must handle a popup.");
		viewHandles.remove(viewHandle.getHandleId());
		final PopupViewHandle popupViewHandle = (PopupViewHandle) viewHandle;
		final Popup popup = popupViewHandle.getPopup();
		popup.close();
	}




	/**
	 * Shows the given {@link ViewHandle}.
	 *
	 * @param viewHandle the handle containing the view
	 */
	private void show(final ViewHandle viewHandle) {

		viewHandles.put(viewHandle.getHandleId(), viewHandle);

		if (viewHandle instanceof StageViewHandle) {
			final StageViewHandle stageViewHandle = (StageViewHandle) viewHandle;
			final Stage stage = stageViewHandle.getStage();
			final Scene scene = stage.getScene();
			final View view = views.get(stageViewHandle.getViewId());
			scene.setRoot(view.getNode());
			stage.setWidth(view.getWidth());
			stage.setHeight(view.getHeight());
			stage.setTitle(view.getTitle());
			if (!stage.isShowing()) {
				stage.show();
			}
		}
		if (viewHandle instanceof PopupViewHandle) {
			final PopupViewHandle popupViewHandle = (PopupViewHandle) viewHandle;
			final Popup popup = popupViewHandle.getPopup();
			popup.show();
		}
	}




	/**
	 * @param viewId the id of the view
	 * @return a list of all current handles for this view
	 */
	public List<ViewHandle> getViewHandles(final String viewId) {
		return viewHandles
				.values()
				.stream()
				.filter(handle -> handle.getViewId().equals(viewId))
				.collect(Collectors.toUnmodifiableList());
	}

}
