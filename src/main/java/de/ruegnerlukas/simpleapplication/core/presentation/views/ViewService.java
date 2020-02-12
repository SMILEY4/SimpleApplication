package de.ruegnerlukas.simpleapplication.core.presentation.views;

import javafx.stage.Stage;

import java.util.List;

public interface ViewService {


	/**
	 * Initializes this view service with the primary stage of the javafx application.
	 *
	 * @param stage             the primary stage
	 * @param showViewAtStartup whether to show a view at startup or hide the window
	 * @param view              the view to show at startup (or null)
	 */
	void initialize(Stage stage, boolean showViewAtStartup, View view);

	/**
	 * Registers the given view.
	 *
	 * @param view the view to register
	 */
	void registerView(View view);


	/**
	 * Deregister the given view.
	 *
	 * @param viewId the id of the view to deregister
	 */
	void deregisterView(String viewId);

	/**
	 * Shows the view with the given id in the primary window.
	 *
	 * @param viewId the id of the view
	 */
	WindowHandle showView(String viewId);

	/**
	 * Replaces the view of the given handle with the view with the given id.
	 *
	 * @param viewId the id of the view
	 * @param handle the handle of the window place the view in
	 */
	WindowHandle showView(String viewId, WindowHandle handle);

	/**
	 * Shows the view with the given id in a new window.
	 *
	 * @param viewId the id of the view
	 * @param wait   whether to wait for the popup to close
	 */
	WindowHandle popupView(String viewId, boolean wait);

	/**
	 * Shows the view with the given id in a new popup window with the given view-binding id as the parent.
	 *
	 * @param viewId the id of the view
	 * @param parent the {@link WindowHandle} of the parent stage/popup
	 * @param wait   whether to wait for the popup to close
	 */
	WindowHandle popupView(String viewId, WindowHandle parent, boolean wait);

	/**
	 * Closes the window with the given handle.
	 *
	 * @param handle the handle of the popup window
	 */
	void closePopup(WindowHandle handle);

	/**
	 * @param viewId the id of the view
	 * @return a list of all current window handles showing the give view
	 */
	List<WindowHandle> getViewHandles(String viewId);

	/**
	 * @return the handle of the primary window
	 */
	WindowHandle getPrimaryWindowHandle();


}