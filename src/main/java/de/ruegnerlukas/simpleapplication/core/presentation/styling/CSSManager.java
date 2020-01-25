package de.ruegnerlukas.simpleapplication.core.presentation.styling;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public final class CSSManager {


	/**
	 * Utility class
	 */
	private CSSManager() {
	}




	/**
	 * The registered {@link CSSStyle}s managed by the {@link CSSManager}. Registered styles can be reloaded all at once.
	 */
	private static Set<CSSStyle> managedCSSStyles = new HashSet<>();




	/**
	 * Creates and registers a new {@link CSSStyle} from the given file.
	 *
	 * @param file the css-file
	 * @return the created {@link CSSStyle}
	 */
	public static CSSStyle fromFile(final File file) {
		Validations.INPUT.exists(file).exception("The css-file does not exist.");
		try {
			final CSSStyle style = new CSSStyle(file.toURI().toURL().toExternalForm());
			registerCssStyle(style);
			return style;
		} catch (MalformedURLException e) {
			log.error("Could not load css file.", e);
			return null;
		}
	}




	/**
	 * Registers the given {@link CSSStyle}. Registered styles can be reloaded all at once.
	 *
	 * @param style the {@link CSSStyle}
	 */
	private static void registerCssStyle(final CSSStyle style) {
		Validations.INPUT.notNull(style).exception("The style may not be null.");
		managedCSSStyles.add(style);
	}




	/**
	 * Deregister the given {@link CSSStyle}. Registered styles can be reloaded all at once.
	 *
	 * @param style the {@link CSSStyle}
	 */
	public static void deregisterCssStyle(final CSSStyle style) {
		managedCSSStyles.remove(style);
	}




	/**
	 * Reload all (registered) {@link CSSStyle}s and nodes.
	 */
	public static void reload() {
		for (CSSStyle cssStyle : managedCSSStyles) {
			cssStyle.reload();
		}
	}

}
