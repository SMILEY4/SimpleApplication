package de.ruegnerlukas.simpleapplication.core.presentation.views;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ViewServiceImpl implements ViewService {


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




	@Override
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




	@Override
	public void registerView(final View view) {
		Validations.INPUT.notNull(view).exception("The view can not be null.");
		views.put(view.getId(), view);
		log.info("Registered view {}.", view.getId());
	}




	@Override
	public void deregisterView(final String viewId) {
		Validations.STATE.isEmpty(getViewHandles(viewId)).exception("Cannot deregister a view that is currently in use.");
		if (views.remove(viewId) != null) {
			log.info("Deregister view {}.", viewId);
		}
	}




	@Override
	public ViewHandle showView(final String viewId) {
		Validations.INPUT.notBlank(viewId).exception("The view id can not be null or empty.");
		Validations.INPUT.containsKey(views, viewId).exception("Could not find a view with id {}.", viewId);
		Validations.PRESENCE.notNull(primaryStage).exception("The primary stage is not yet set.");
		final ViewHandle viewHandle = new StageViewHandle(ViewHandle.ID_PRIMARY, views.get(viewId), primaryStage);
		show(viewHandle);
		return viewHandle;
	}




	@Override
	public ViewHandle showView(final String viewId, final ViewHandle handle) {
		Validations.INPUT.notBlank(viewId).exception("The view id can not be null or empty.");
		Validations.INPUT.notNull(handle).exception("The handle can not be null.");
		Validations.INPUT.containsKey(views, viewId).exception("Could not find a view with id {}.", viewId);
		ViewHandle viewHandle;
		if (handle instanceof PopupViewHandle) {
			viewHandle = new PopupViewHandle(handle.getHandleId(), views.get(viewId), ((PopupViewHandle) handle).getPopup());
		} else {
			viewHandle = new StageViewHandle(handle.getHandleId(), views.get(viewId), handle.getStage());
		}
		showAsStageViewHandle(viewHandle);
		return viewHandle;
	}




	@Override
	public ViewHandle popupView(final String viewId, final boolean wait) {
		Validations.INPUT.notBlank(viewId).exception("The view id can not be null or empty.");
		Validations.INPUT.containsKey(views, viewId).exception("Could not find a view with id {}.", viewId);
		Validations.PRESENCE.notNull(primaryStage).exception("The primary stage is not yet set.");
		return popupView(viewId, viewHandles.get(ViewHandle.ID_PRIMARY), wait);
	}




	@Override
	public ViewHandle popupView(final String viewId, final ViewHandle parent, final boolean wait) {
		Validations.INPUT.notBlank(viewId).exception("The view id can not be null or empty.");
		Validations.INPUT.containsKey(views, viewId).exception("Could not find a view with id {}.", viewId);
		Validations.PRESENCE.notNull(primaryStage).exception("The primary stage is not yet set.");
		final Popup popup = new Popup(parent.getStage(), views.get(viewId), wait);
		final ViewHandle viewHandle = new PopupViewHandle(createHandleId(viewId, popup.hashCode()), views.get(viewId), popup);
		show(viewHandle);
		return viewHandle;
	}




	@Override
	public void closePopup(final ViewHandle viewHandle) {
		Validations.INPUT.notNull(viewHandle).exception("The view handle can not be null.");
		Validations.INPUT.typeOf(viewHandle, PopupViewHandle.class).exception("The view handle must handle a popup.");
		viewHandles.remove(viewHandle.getHandleId());
		final PopupViewHandle popupViewHandle = (PopupViewHandle) viewHandle;
		final Popup popup = popupViewHandle.getPopup();
		popup.close();
	}




	@Override
	public List<ViewHandle> getViewHandles(final String viewId) {
		return viewHandles
				.values()
				.stream()
				.filter(handle -> handle.getViewId().equals(viewId))
				.collect(Collectors.toUnmodifiableList());
	}




	/**
	 * Shows the given {@link ViewHandle}.
	 *
	 * @param viewHandle the handle containing the view
	 */
	private void show(final ViewHandle viewHandle) {
		if (viewHandle instanceof StageViewHandle) {
			showAsStageViewHandle(viewHandle);
		}
		if (viewHandle instanceof PopupViewHandle) {
			final PopupViewHandle popupViewHandle = (PopupViewHandle) viewHandle;
			showAsPopupViewHandle(popupViewHandle);
		}
	}




	/**
	 * Shows the given view handle in its stage.
	 *
	 * @param viewHandle the view handle
	 */
	private void showAsStageViewHandle(final ViewHandle viewHandle) {
		viewHandles.put(viewHandle.getHandleId(), viewHandle);
		final Stage stage = viewHandle.getStage();
		final Scene scene = stage.getScene();
		final View view = views.get(viewHandle.getViewId());
		scene.setRoot(view.getNode());
		stage.setWidth(view.getWidth());
		stage.setHeight(view.getHeight());
		stage.setTitle(view.getTitle());
		if (!stage.isShowing()) {
			stage.show();
		}
	}




	/**
	 * Shows the given {@link PopupViewHandle} as a new popup.
	 *
	 * @param viewHandle the view handle
	 */
	private void showAsPopupViewHandle(final PopupViewHandle viewHandle) {
		viewHandles.put(viewHandle.getHandleId(), viewHandle);
		final Popup popup = viewHandle.getPopup();
		popup.show();
	}




	/**
	 * Creates an id for a {@link ViewHandle} from the given view id and an object.
	 *
	 * @param viewId the id of the view
	 * @param obj    an object to add to the id
	 * @return an id for a {@link ViewHandle}
	 */
	private String createHandleId(final String viewId, final Object obj) {
		return viewId + "handle" + Integer.toHexString(obj.hashCode());
	}


}
