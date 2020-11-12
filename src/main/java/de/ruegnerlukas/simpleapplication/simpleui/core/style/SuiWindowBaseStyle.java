package de.ruegnerlukas.simpleapplication.simpleui.core.style;

import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor (access = AccessLevel.PRIVATE)
public final class SuiWindowBaseStyle implements SuiBaseStyle {


	/**
	 * @param stylesheets the css-file(s) to use as stylesheet(s)
	 * @return the base style using the given css-file
	 */
	public static SuiWindowBaseStyle cssStylesheet(final Resource... stylesheets) {
		return new SuiWindowBaseStyle(stylesheets);
	}




	/**
	 * the resource(s) with the css-file(s) (or null)
	 */
	private final Resource[] cssStylesheets;



}
