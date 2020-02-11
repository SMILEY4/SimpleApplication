package de.ruegnerlukas.simpleapplication.core.presentation.views;

import de.ruegnerlukas.simpleapplication.common.events.EventPackage;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class ViewServiceImpl implements ViewService {


	/**
	 * The provider for the event service.
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
	 * All window handles of the currently visible windows and views.
	 */
	private final Map<String, WindowHandle> viewHandles = new HashMap<>();




	@Override
	public void initialize(final Stage stage, final boolean showViewAtStartup, final View view) {
		Validations.INPUT.notNull(stage).exception("The stage can not be null.");
		Validations.STATE.isNull(primaryStage).exception("The view service was already initialized.");

		View startupView = view;
		if (startupView == null) {
			startupView = new EmptyView();
		}
		registerView(startupView);

		this.primaryStage = stage;
		this.primaryStage.setScene(new Scene(startupView.getNode(), startupView.getWidth(), startupView.getHeight()));
		this.primaryStage.setTitle(startupView.getTitle());
		if (showViewAtStartup) {
			showView(view.getId());
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
	public WindowHandle showView(final String viewId) {
		Validations.INPUT.notBlank(viewId).exception("The view id may not be null or empty.");
		Validations.INPUT.containsKey(views, viewId).exception("No view with the id {} found.", viewId);
		Validations.STATE.notNull(primaryStage).exception("The view service is not yet initialized / the primary stage is null.");
		final WindowHandle handle = new WindowHandle(WindowHandle.ID_PRIMARY, views.get(viewId), primaryStage);
		viewHandles.put(handle.getHandleId(), handle);
		return showView(viewId, handle);
	}




	@Override
	public WindowHandle showView(final String viewId, final WindowHandle handle) {
		Validations.INPUT.notBlank(viewId).exception("The view id may not be null or empty.");
		Validations.INPUT.containsKey(views, viewId).exception("No view with the id {} found.", viewId);
		Validations.INPUT.notNull(handle).exception("The handle may not be null.");

		final String prevView = handle.getView().getId();
		handle.setView(views.get(viewId));
		final Stage stage = handle.getStage();
		final Scene scene = stage.getScene();
		final View view = handle.getView();
		scene.setRoot(view.getNode());
		stage.setWidth(view.getWidth());
		stage.setHeight(view.getHeight());
		stage.setTitle(view.getTitle());

		if (!stage.isShowing()) {
			stage.show();
		}

		eventServiceProvider.get().publish(ApplicationConstants.EVENT_SHOW_VIEW, new EventPackage<>(
				ViewEvent.builder()
						.prevViewId(prevView.equals(viewId) ? null : prevView)
						.viewId(viewId)
						.windowHandle(handle)
						.build()));

		return handle;
	}




	@Override
	public WindowHandle popupView(final String viewId, final boolean wait) {
		Validations.INPUT.notBlank(viewId).exception("The view id may not be null or empty.");
		Validations.INPUT.containsKey(views, viewId).exception("No view with the id {} found.", viewId);
		return popupView(viewId, viewHandles.get(WindowHandle.ID_PRIMARY), wait);
	}




	@Override
	public WindowHandle popupView(final String viewId, final WindowHandle parent, final boolean wait) {
		Validations.INPUT.notBlank(viewId).exception("The view id may not be null or empty.");
		Validations.INPUT.containsKey(views, viewId).exception("No view with the id {} found.", viewId);
		Validations.INPUT.notNull(parent).exception("The parent may not be null.");
		Validations.INPUT.containsKey(viewHandles, parent.getHandleId())
				.exception("The parent '{}' was not found.", parent.getHandleId());

		final View view = views.get(viewId);
		final Scene scene = new Scene(view.getNode(), view.getWidth(), view.getHeight());
		scene.setRoot(view.getNode());
		final Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(parent.getStage());
		stage.setTitle(view.getTitle());
		stage.setScene(scene);
		final WindowHandle handle = new WindowHandle(createHandleId(), view, stage);
		viewHandles.put(handle.getHandleId(), handle);

		eventServiceProvider.get().publish(ApplicationConstants.EVENT_OPEN_POPUP, new EventPackage<>(
				ViewEvent.builder()
						.prevViewId(null)
						.viewId(viewId)
						.windowHandle(handle)
						.build()));

		if (wait) {
			stage.showAndWait();
		} else {
			stage.show();
		}

		return handle;
	}




	@Override
	public void closePopup(final WindowHandle handle) {
		Validations.INPUT.notNull(handle).exception("The handle may not be null.");
		Validations.INPUT.containsKey(viewHandles, handle.getHandleId())
				.exception("The handle '{}' was not found.", handle.getHandleId());

		handle.getStage().close();
		handle.getStage().getScene().setRoot(new Pane());
		viewHandles.remove(handle.getHandleId());

		eventServiceProvider.get().publish(ApplicationConstants.EVENT_CLOSE_POPUP, new EventPackage<>(
				ViewEvent.builder()
						.prevViewId(null)
						.viewId(handle.getView().getId())
						.windowHandle(handle)
						.build()));

	}




	@Override
	public List<WindowHandle> getViewHandles(final String viewId) {
		return viewHandles
				.values()
				.stream()
				.filter(handle -> handle.getView().getId().equals(viewId))
				.collect(Collectors.toUnmodifiableList());
	}




	/**
	 * @return the handle of the primary window
	 */
	@Override
	public WindowHandle getPrimaryWindowHandle() {
		return viewHandles.get(WindowHandle.ID_PRIMARY);
	}




	/**
	 * Generates an id for a {@link WindowHandle}.
	 *
	 * @return an id for a {@link WindowHandle}
	 */
	private String createHandleId() {
		return "windowhandle_" + UUID.randomUUID().toString();
	}


}
