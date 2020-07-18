package de.ruegnerlukas.simpleapplication.core.presentation.style;

import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import javafx.scene.Node;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
	 * Sets whether the style with the given name is a root-style.
	 * Root styles will be applied to all scenes by the {@link de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService}.
	 *
	 * @param name        the name of the style
	 * @param isRootStyle whether the style is a root-style
	 */
	void setRootStyle(String name, boolean isRootStyle);


	/**
	 * Applies the style registered with the given name to the given target.
	 *
	 * @param name   the name of the {@link Style}
	 * @param target the target {@link Node}
	 */
	void applyStyleTo(String name, Node target);


	/**
	 * Applies the styles registered with the given names to the given target.
	 *
	 * @param names  the names of the {@link Style}s
	 * @param target the target {@link Node}
	 */
	void applyStylesTo(List<String> names, Node target);


	/**
	 * Applies the styles registered with the given names to the given target.
	 *
	 * @param names  the names of the {@link Style}s
	 * @param target the target {@link Node}
	 */
	void applyStylesTo(Set<String> names, Node target);

	/**
	 * Applies the styles marked as root-styles to the given target.
	 *
	 * @param target the target {@link Node}
	 */
	void applyRootStylesTo(Node target);

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
	 * Disconnects the node from this style service. This will remove all applied (registered) {@link Style}s.
	 *
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


	/**
	 * @param target the target {@link Node}
	 * @return all styles applied to the given target node by this style service.
	 */
	List<Style> getAppliedStyles(Node target);


	/**
	 * @param target the target {@link Node}
	 * @return all names of the styles applied to the given target node by this style service.
	 */
	List<String> getAppliedStyleNames(Node target);


	/**
	 * @param style the name of the registered {@link Style}
	 * @return all {@link Node}s the given style was applied to.
	 */
	List<Node> getTargets(String style);

	/**
	 * Root styles will be applied to all scenes by the {@link de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService}.
	 *
	 * @return the names of the styles marked as root styles.
	 */
	List<String> getRootStyles();

}
