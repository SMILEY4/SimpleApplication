package de.ruegnerlukas.simpleapplication.core.presentation.styling;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import javafx.scene.Parent;

import java.util.HashSet;
import java.util.Set;

public class CSSStyle {


	/**
	 * The url of the css-file.
	 */
	private final String url;

	/**
	 * The nodes this style is applied to.
	 */
	private Set<Parent> nodes = new HashSet<>();




	/**
	 * The constructor.
	 *
	 * @param url the url of the css-file
	 */
	CSSStyle(final String url) {
		Validations.INPUT.notBlank(url, "The url may not be null or empty.");
		this.url = url;
	}




	/**
	 * Applies this style to the given node.
	 *
	 * @param node the node
	 */
	public void applyToNode(final Parent node) {
		Validations.INPUT.notNull(node, "The node to apply the styling to may not be null.");
		nodes.add(node);
		reload(node, true);
	}




	/**
	 * Removes this style from the given node.
	 *
	 * @param node the node
	 */
	public void removeFromNode(final Parent node) {
		if (nodes.remove(node)) {
			reload(node, false);
		}
	}




	/**
	 * Reloads this style and all nodes this style is applied to.
	 */
	public void reload() {
		for (Parent node : nodes) {
			reload(node, true);
		}
	}




	/**
	 * Reloads this style for the given node.
	 *
	 * @param node       the node
	 * @param applyStyle whether to apply or remove this style
	 */
	private void reload(final Parent node, final boolean applyStyle) {
		node.getStylesheets().clear();
		if (applyStyle) {
			node.getStylesheets().add(this.url);
		}
	}


}
