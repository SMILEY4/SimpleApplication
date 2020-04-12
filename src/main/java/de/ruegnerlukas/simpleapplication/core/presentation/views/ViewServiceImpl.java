package de.ruegnerlukas.simpleapplication.core.presentation.views;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.presentation.style.EventRootStyleMark;
import de.ruegnerlukas.simpleapplication.core.presentation.style.StyleService;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class ViewServiceImpl implements ViewService {


	/**
	 * The provider for the event service.
	 */
	private final Provider<EventService> eventServiceProvider = new Provider<>(EventService.class);

	/**
	 * The provider for the style service.
	 */
	private final Provider<StyleService> styleServiceProvider = new Provider<>(StyleService.class);

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
	private final Map<String, WindowHandle> windowHandles = new HashMap<>();




	@Override
	public void initialize(final Stage stage, final boolean showViewAtStartup, final View view) {
		Validations.INPUT.notNull(stage).exception("The stage can not be null.");
		Validations.STATE.isNull(primaryStage).exception("The view service was already initialized.");

		listenRootStyles();

		View startupView = view;
		if (startupView == null) {
			startupView = new EmptyView();
		}
		registerView(startupView);
		setupPrimaryStage(stage, startupView);
		if (showViewAtStartup) {
			showView(view.getId());
		}
	}




	/**
	 * Setup listeners to listen for changes of root-styles.
	 */
	private void listenRootStyles() {
		eventServiceProvider.get().subscribe(Channel.type(EventRootStyleMark.class), publishable -> {
			final EventRootStyleMark event = (EventRootStyleMark) publishable;
			if (event.isRootStyle()) {
				windowHandles.values().forEach(handle -> {
					final Parent root = handle.getStage().getScene().getRoot();
					styleServiceProvider.get().applyStyleTo(event.getStyle(), root);
				});
			}
		});
	}




	/**
	 * Setup primary stage with the given view
	 *
	 * @param stage the primary stage passed to this view service
	 * @param view  the view to (possibly) show at startup
	 */
	private void setupPrimaryStage(final Stage stage, final View view) {
		final Scene scene = new Scene(view.getNode(), view.getSize().getWidth(), view.getSize().getHeight());
		primaryStage = stage;
		primaryStage.setScene(scene);
		primaryStage.setTitle(view.getTitle());
		styleServiceProvider.get().applyStylesTo(styleServiceProvider.get().getRootStyles(), scene.getRoot());
	}




	@Override
	public void registerView(final View view) {
		Validations.INPUT.notNull(view).exception("The view can not be null.");
		views.put(view.getId(), view);
		log.info("Registered view {}.", view.getId());
	}




	@Override
	public void deregisterView(final String viewId) {
		Validations.STATE.isEmpty(getWindowHandles(viewId)).exception("Cannot deregister a view that is currently in use.");
		if (views.remove(viewId) != null) {
			log.info("Deregister view {}.", viewId);
		}
	}




	@Override
	public Optional<View> findView(final String viewId) {
		return Optional.ofNullable(views.get(viewId));
	}




	@Override
	public WindowHandle showView(final String viewId) {
		Validations.INPUT.notBlank(viewId).exception("The view id may not be null or empty.");
		Validations.INPUT.containsKey(views, viewId).exception("No view with the id {} found.", viewId);
		Validations.STATE.notNull(primaryStage).exception("The view service is not yet initialized / the primary stage is null.");
		WindowHandle handle = getPrimaryWindowHandle();
		if (handle == null) {
			handle = new WindowHandle(WindowHandle.ID_PRIMARY, views.get(viewId), primaryStage);
			windowHandles.put(handle.getHandleId(), handle);
		}
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
		setStageSize(stage, view);
		stage.setTitle(view.getTitle());

		final StyleService styleService = styleServiceProvider.get();
		styleService.disconnectNode(views.get(prevView).getNode());
		styleService.applyStylesTo(styleService.getRootStyles(), view.getNode());
		styleService.applyStylesTo(view.getStyles(), view.getNode());

		if (!stage.isShowing()) {
			stage.show();
		}

		eventServiceProvider.get().publish(new EventShowView(prevView.equals(viewId) ? null : prevView, viewId, handle));

		return handle;
	}




	@Override
	public WindowHandle popupView(final String viewId, final PopupConfiguration config) {
		Validations.INPUT.notBlank(viewId).exception("The view id may not be null or empty.");
		Validations.INPUT.containsKey(views, viewId).exception("No view with the id {} found.", viewId);
		Validations.INPUT.notNull(config).exception("The popup config may not be null.");

		final View view = views.get(viewId);
		final Scene scene = new Scene(view.getNode(), view.getSize().getWidth(), view.getSize().getHeight());
		scene.setRoot(view.getNode());
		final Stage stage = new Stage();
		stage.initModality(config.getModality());
		stage.initOwner(config.getParent() == null ? getPrimaryWindowHandle().getStage() : config.getParent().getStage());
		stage.initStyle(config.getStyle());
		stage.setAlwaysOnTop(config.isAlwaysOnTop());
		stage.setTitle(view.getTitle());
		setStageSize(stage, view);
		stage.setScene(scene);
		final WindowHandle handle = new WindowHandle(createHandleId(), view, stage);
		windowHandles.put(handle.getHandleId(), handle);

		final StyleService styleService = styleServiceProvider.get();
		styleService.applyStylesTo(styleService.getRootStyles(), view.getNode());
		styleService.applyStylesTo(view.getStyles(), view.getNode());

		eventServiceProvider.get().publish(new EventOpenPopup(viewId, handle));

		if (config.isWait()) {
			stage.showAndWait();
		} else {
			stage.show();
		}

		return handle;
	}




	@Override
	public void closePopup(final WindowHandle handle) {
		Validations.INPUT.notNull(handle).exception("The handle may not be null.");
		Validations.INPUT.containsKey(windowHandles, handle.getHandleId())
				.exception("The handle '{}' was not found.", handle.getHandleId());
		handle.getStage().close();
		handle.getStage().getScene().setRoot(new Pane());
		windowHandles.remove(handle.getHandleId());
		styleServiceProvider.get().disconnectNode(handle.getView().getNode());
		eventServiceProvider.get().publish(new EventClosePopup(handle.getView().getId(), handle));
	}




	@Override
	public List<WindowHandle> getWindowHandles(final String viewId) {
		return windowHandles
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
		return windowHandles.get(WindowHandle.ID_PRIMARY);
	}




	/**
	 * Generates an id for a {@link WindowHandle}.
	 *
	 * @return an id for a {@link WindowHandle}
	 */
	private String createHandleId() {
		return "windowhandle_" + UUID.randomUUID().toString();
	}




	/**
	 * Set the size (min, max, pref) of the given {@link Stage} to the size specified in the given {@link View}.
	 *
	 * @param stage the stage
	 * @param view  the view
	 */
	private void setStageSize(final Stage stage, final View view) {
		stage.setWidth(view.getSize().getWidth());
		stage.setHeight(view.getSize().getHeight());
		stage.setMinWidth(view.getMinSize().getWidth());
		stage.setMinHeight(view.getMinSize().getHeight());
		stage.setMaxWidth(view.getMaxSize().getWidth());
		stage.setMaxHeight(view.getMaxSize().getHeight());
	}


}
