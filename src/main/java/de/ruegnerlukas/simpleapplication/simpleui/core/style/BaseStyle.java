package de.ruegnerlukas.simpleapplication.simpleui.core.style;

import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import javafx.application.Application;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor (access = AccessLevel.PRIVATE)
public final class BaseStyle {


	/**
	 * @return the default javafx style "caspian".
	 */
	public static BaseStyle caspian() {
		return new BaseStyle(true, Application.STYLESHEET_CASPIAN, null);
	}




	/**
	 * @return the default javafx style "modena".
	 */
	public static BaseStyle modena() {
		return new BaseStyle(true, Application.STYLESHEET_MODENA, null);
	}




	/**
	 * @param stylesheets the css-file(s) to use as stylesheet(s)
	 * @return the base style using the given css-file
	 */
	public static BaseStyle cssStylesheet(final Resource... stylesheets) {
		return new BaseStyle(false, null, stylesheets);
	}




	/**
	 * Whether this base style is one of the default javafx styles
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
