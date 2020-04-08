package de.ruegnerlukas.simpleapplication.core.presentation.style;

import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import javafx.scene.Node;

import java.util.Optional;

public interface StyleService {


	/**
	 * Creates a new {@link Style} from the given stylesheet and registers it with the given name.
	 *
	 * @param name     the name of the {@link Style}. If a style with the name already exists, it will be overwritten.
	 * @param resource the {@link Resource} pointing to a stylesheet
	 * @return the created and registered style
	 */
	Style createFromResource(String name, Resource resource);


	/**
	 * Creates a new {@link Style} from the given string and registers it with the given name.
	 *
	 * @param name   the name of the {@link Style}. If a style with the name already exists, it will be overwritten.
	 * @param string the css as string. Either separated by a ';' or as multiple strings.
	 * @return the created style
	 */
	Style createFromString(String name, String... string);

	/**
	 * Registers the given style with the given name. If a style with the name already exists, it will be overwritten.
	 *
	 * @param style the {@link Style} to register
	 * @param name  the name of the style
	 */
	void registerStyle(Style style, String name);


	/**
	 * De-registers the style with the given name.
	 *
	 * @param name the name of the {@link Style} to deregister
	 */
	void deregisterStyle(String name);


	/**
	 * Applies the style registered with the given name to the given target.
	 *
	 * @param name   the name of the {@link Style}
	 * @param target the target {@link Node}
	 */
	void applyStyleTo(String name, Node target);

	/**
	 * Applies the style registered with the given name to the given target node and removes all other previously applied styles.
	 *
	 * @param name   the name of the {@link Style}
	 * @param target the target {@link Node}
	 */
	void applyStyleToExclusive(String name, Node target);


	/**
	 * Removes the style registered with the given name from the given target node.
	 *
	 * @param name   the name of the {@link Style}
	 * @param target the target {@link Node}
	 */
	void removeStyleFrom(String name, Node target);


	/**
	 * Disconnects the node from this style service. This will remove all applied (and registered) {@link Style}s.
	 * @param node the {@link Node} to disconnect
	 */
	void disconnectNode(Node node);

	/**
	 * Reloads the style registered with the given name.
	 *
	 * @param name the name of the {@link Style}.
	 */
	void reloadStyle(String name);


	/**
	 * Reloads all registered styles.
	 */
	void reloadAll();


	/**
	 * Finds the style registered with the given name.
	 *
	 * @param name the name of the {@link Style}
	 * @return an optional with the style (if found)
	 */
	Optional<Style> findStyle(String name);

}
