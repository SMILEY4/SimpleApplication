package de.ruegnerlukas.simpleapplication.core.presentation.style;

import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import javafx.scene.Node;

public abstract class Style {


	/**
	 * Creates a new {@link Style} handle from the given stylesheet
	 *
	 * @param resource the {@link Resource} pointing to a stylesheet
	 * @return the created style
	 */
	public static Style fromFile(final Resource resource) {
		return new ResourceStyle(resource);
	}




	/**
	 * Creates a new {@link Style} handle from the given string.
	 *
	 * @param string the css as string. Either separated by a ';' or as multiple strings.
	 * @return the created style
	 */
	public static Style fromString(final String... string) {
		return new StringStyle(string);
	}




	/**
	 * Applies this style to the given node
	 *
	 * @param target the {@link Node}.
	 */
	public abstract void applyTo(Node target);


	/**
	 * Applies this style to the given node and removes other styles.
	 *
	 * @param target the {@link Node}
	 */
	public abstract void applyToOnly(Node target);


	/**
	 * Removes this style from the given node.
	 *
	 * @param target the {@link Node}
	 */
	public abstract void removeFrom(Node target);

}
