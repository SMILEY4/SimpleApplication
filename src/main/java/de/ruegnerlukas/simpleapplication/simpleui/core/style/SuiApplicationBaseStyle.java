package de.ruegnerlukas.simpleapplication.simpleui.core.style;

import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import javafx.application.Application;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor (access = AccessLevel.PRIVATE)
public final class SuiApplicationBaseStyle implements SuiBaseStyle {


	/**
	 * @param stylesheets optional css-files as stylesheets
	 * @return the default javafx style "caspian".
	 */
	public static SuiApplicationBaseStyle caspian(final Resource... stylesheets) {
		return new SuiApplicationBaseStyle(true, Application.STYLESHEET_CASPIAN, stylesheets);
	}




	/**
	 * @param stylesheets optional css files-as stylesheets
	 * @return the default javafx style "modena".
	 */
	public static SuiApplicationBaseStyle modena(final Resource... stylesheets) {
		return new SuiApplicationBaseStyle(true, Application.STYLESHEET_MODENA, stylesheets);
	}




	/**
	 * @param stylesheets the css-files to use as stylesheets
	 * @return the base style using the given css-file
	 */
	public static SuiApplicationBaseStyle cssStylesheet(final Resource... stylesheets) {
		return new SuiApplicationBaseStyle(false, null, stylesheets);
	}




	/**
	 * Whether this base style specifies one of the default javafx styles
	 */
	private final boolean defaultFxStyle;

	/**
	 * The identifying name of the default javafx style (or null)
	 */
	private final String defaultFxStyleName;


	/**
	 * the resource(s) with the css-file(s) (or null)
	 */
	private final Resource[] cssStylesheets;


}
