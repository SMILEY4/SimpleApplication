package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
public class SUINode {


	/**
	 * The type of this node.
	 */
	private final Class<?> nodeType;


	/**
	 * The properties of this node.
	 */
	private final Map<Class<? extends Property>, Property> properties = new HashMap<>();

	/**
	 * The child nodes of this node.
	 */
	@Getter
	private final List<SUINode> children;

	/**
	 * The listener for changes to child nodes (if required).
	 */
	@Getter
	private ChildListener childListener;


	/**
	 * The fx-node attached to this node (or null).
	 */
	@Setter
	private javafx.scene.Node fxNode;




	/**
	 * @param nodeType      the type of this node.
	 * @param propertyList  the properties of this node.
	 * @param state         the current state
	 * @param childListener the listener for changes to child nodes (if required).
	 */
	public SUINode(final Class<?> nodeType, final List<Property> propertyList,
				   final SUIState state, final ChildListener childListener) {
		this.nodeType = nodeType;
		propertyList.forEach(property -> properties.put(property.getKey(), property));
		this.children = childNodesFromProperties(state);
		this.childListener = childListener;
	}




	/**
	 * Create the child nodes from the properties of this node and the given state.
	 *
	 * @param state the current state
	 * @return the list of child nodes.
	 */
	private List<SUINode> childNodesFromProperties(final SUIState state) {
		final List<SUINode> children = new ArrayList<>();
		getPropertySafe(ItemListProperty.class).ifPresent(itemListProp -> itemListProp.getFactories().forEach(factory -> {
			final SUINode child = factory.create(state);
			children.add(child);
		}));
		getPropertySafe(ItemProperty.class).ifPresent(itemProp -> children.add(itemProp.getFactory().create(state)));
		return children;
	}




	/**
	 * Inform the listener (if possible) of any changes to the child nodes.
	 */
	public void triggerChildListChange() {
		if (childListener != null && getFxNode() != null) {
			childListener.onChange(this);
		}
	}




	/**
	 * Get the property with the given type.
	 *
	 * @param type the type of the requested property
	 * @param <T>  the generic type
	 * @return the requested property
	 */
	public <T> Optional<T> getPropertySafe(final Class<T> type) {
		return Optional.ofNullable(getProperty(type));
	}




	/**
	 * Get the property with the given type or null.
	 *
	 * @param type the type of the requested property
	 * @param <T>  the generic type
	 * @return the requested property or null
	 */
	public <T> T getProperty(final Class<T> type) {
		Property property = getProperties().get(type);
		if (property != null) {
			return (T) property;
		} else {
			return null;
		}
	}




	/**
	 * Check whether this node has a property of the given type.
	 *
	 * @param type the type of the property
	 * @return whether this node has a property of the given type.
	 */
	public boolean hasProperty(final Class<? extends Property> type) {
		return getProperties().containsKey(type);
	}




	public interface ChildListener {


		/**
		 * The child nodes of the given parent node have changed.
		 *
		 * @param parent the parent node of the changed child nodes
		 */
		void onChange(SUINode parent);

	}

}
