package de.ruegnerlukas.simpleapplication.simpleui.core.style;

import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.core.events.SuiListChangeListener;
import javafx.application.Application;
import javafx.stage.Window;

import java.util.HashMap;
import java.util.Map;

public class SuiStyleManager {


	/**
	 * The base style for the while application
	 */
	private SuiApplicationBaseStyle applicationBaseStyle = SuiApplicationBaseStyle.modena();

	/**
	 * The styles applied to individual window
	 */
	private final Map<Window, SuiWindowBaseStyle> windowStyles = new HashMap<>();




	/**
	 * Default constructor
	 */
	public SuiStyleManager() {
		Window.getWindows().addListener(new SuiListChangeListener<>(
				this::onWindowOpened,
				this::onWindowClosed
		));
	}




	/**
	 * Applies the given style to the whole application.
	 *
	 * @param applicationBaseStyle the new base style
	 */
	public void setApplicationBaseStyle(final SuiApplicationBaseStyle applicationBaseStyle) {
		Validations.INPUT.notNull(applicationBaseStyle).exception("The application base style may not be null.");
		this.applicationBaseStyle = applicationBaseStyle;
		applyToApplication(applicationBaseStyle);
	}




	/**
	 * Applies and registers the given style to the given window
	 *
	 * @param window      the window to apply the style to
	 * @param windowStyle the style to apply
	 */
	public void applyStyle(final Window window, final SuiWindowBaseStyle windowStyle) {
		Validations.INPUT.notNull(applicationBaseStyle).exception("The window base style may not be null.");
		windowStyles.put(window, windowStyle);
		applyToScene(window, windowStyle);
	}




	/**
	 * Called when a new window was opened.
	 *
	 * @param window the opened window
	 */
	private void onWindowOpened(final Window window) {
		applyToScene(window, applicationBaseStyle);
	}




	/**
	 * Called when a new window was opened.
	 *
	 * @param window the opened window
	 */
	private void onWindowClosed(final Window window) {
		windowStyles.remove(window);
	}




	/**
	 * Removes style sheets from the given window that where not applied as {@link SuiWindowBaseStyle}
	 *
	 * @param window the window
	 */
	private void clearStylesheets(final Window window) {
		final SuiWindowBaseStyle windowBaseStyle = windowStyles.get(window);
		if (windowBaseStyle == null) {
			window.getScene().getStylesheets().clear();
		} else {
			window.getScene().getStylesheets().removeIf(stylesheet -> {
				for (Resource resource : windowBaseStyle.getCssStylesheets()) {
					if (stylesheet.equals(toStylesheetPath(resource))) {
						return false;
					}
				}
				return true;
			});
		}
	}




	/**
	 * Applies the given style to the whole application (replacing the previous style)
	 *
	 * @param style the style to apply
	 */
	private void applyToApplication(final SuiApplicationBaseStyle style) {
		if (style.isDefaultFxStyle()) {
			Application.setUserAgentStylesheet(style.getDefaultFxStyleName());
		}
		Window.getWindows().forEach(window -> applyToScene(window, style));
	}




	/**
	 * Applies the given style to the given window (replacing the previous style)
	 *
	 * @param window the window to apply the style to
	 * @param style  the style to apply
	 */
	private void applyToScene(final Window window, final SuiBaseStyle style) {
		clearStylesheets(window);
		final Resource[] cssStyleSheets = (style instanceof SuiApplicationBaseStyle)
				? ((SuiApplicationBaseStyle) style).getCssStylesheets()
				: ((SuiWindowBaseStyle) style).getCssStylesheets();
		if (cssStyleSheets != null) {
			for (Resource resStylesheet : cssStyleSheets) {
				window.getScene().getStylesheets().add("file:" + resStylesheet.getPath());
			}
		}
	}




	/**
	 * @param resource the resource
	 * @return the path to the css-file
	 */
	private String toStylesheetPath(final Resource resource) {
		String path = resource.getPath();
		if (!resource.isInternal()) {
			path = "file:" + path;
		}
		return path;
	}

}
