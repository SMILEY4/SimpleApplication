package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.simpleui.properties.IdProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class SUINode {


	/**
	 * The type of this node.
	 */
	@Getter
	private final Class<?> nodeType;


	/**
	 * The properties of this node.
	 */
	@Getter
	private final Map<Class<? extends Property>, Property> properties = new HashMap<>();

	/**
	 * The child nodes of this node.
	 */
	private final List<SUINode> children = new ArrayList<>();

	/**
	 * The child nodes of this node with their id as a key.
	 */
	private final Map<String, SUINode> childMap = new HashMap<>();

	/**
	 * The listener for changes to child nodes (if required).
	 */
	@Getter
	private ChildListener childListener;


	/**
	 * The fx-node attached to this node (or null).
	 */
	@Setter
	@Getter
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
		childNodesFromProperties(state);
		this.childListener = childListener;
	}




	/**
	 * Create the child nodes from the properties of this node and the given state.
	 *
	 * @param state the current state
	 */
	private void childNodesFromProperties(final SUIState state) {
		final List<SUINode> children = new ArrayList<>();
		getPropertySafe(ItemListProperty.class).ifPresent(itemListProp -> itemListProp.getFactories().forEach(factory -> {
			final SUINode child = factory.create(state);
			children.add(child);
		}));
		getPropertySafe(ItemProperty.class).ifPresent(itemProp -> children.add(itemProp.getFactory().create(state)));
		setChildren(children, false);
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
	 * @return whether this nodes has child nodes
	 */
	public boolean hasChildren() {
		return !this.children.isEmpty();
	}




	/**
	 * @return the number of child nodes of this node
	 */
	public int childCount() {
		return this.children.size();
	}




	/**
	 * @param index the index of the child
	 * @return the child at the given index
	 * @throws IndexOutOfBoundsException if the given index is out of range
	 */
	public SUINode getChild(final int index) {
		return this.children.get(index);
	}




	/**
	 * Finds the child node with the given id
	 *
	 * @param id the id of the requested child node
	 * @return the child node with the given id (if one exists)
	 */
	public Optional<SUINode> findChild(final String id) {
		return Optional.ofNullable(childMap.get(id));
	}




	/**
	 * @return an unmodifiable list of children
	 */
	public List<SUINode> getChildrenUnmodifiable() {
		return Collections.unmodifiableList(this.children);
	}




	/**
	 * Replaces all children of this node with the given children
	 *
	 * @param childrenList       the list of new child nodes
	 * @param triggerChildChange true, to trigger the child change action
	 */
	public void setChildren(final List<SUINode> childrenList, final boolean triggerChildChange) {
		this.children.clear();
		this.children.addAll(childrenList);
		this.childMap.clear();
		for (SUINode child : this.children) {
			child.getPropertySafe(IdProperty.class).ifPresent(idProp -> childMap.put(idProp.getId(), child));
		}
		if (triggerChildChange) {
			triggerChildListChange();
		}
	}




	/**
	 * @return the children of this node as a stream
	 */
	public Stream<SUINode> streamChildren() {
		return this.children.stream();
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
