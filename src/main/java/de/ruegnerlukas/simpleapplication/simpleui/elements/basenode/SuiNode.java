package de.ruegnerlukas.simpleapplication.simpleui.elements.basenode;

import de.ruegnerlukas.simpleapplication.common.utils.LoopUtils;
import de.ruegnerlukas.simpleapplication.simpleui.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.operations.BaseOperation;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.operations.OperationType;
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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SuiNode {


	/**
	 * The amount of child nodes that have to be created to use the async processing.
	 */
	private static final int CREATE_CHILD_LIST_ASYNC_CUTOFF = 1028;

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
	private final List<SuiNode> children = new ArrayList<>();

	/**
	 * The child nodes of this node with their id as a key.
	 */
	private final Map<String, SuiNode> childMap = new HashMap<>();

	/**
	 * The listener for changes to child nodes (if required).
	 */
	@Getter
	private ChildListener childListener;

	/**
	 * The listener for child node transformations (if required).
	 */
	@Getter
	private SuiNodeChildTransformListener childTransformListener;

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
	public SuiNode(final Class<?> nodeType,
				   final List<Property> propertyList,
				   final SuiState state,
				   final ChildListener childListener) {
		this(nodeType, propertyList, state, childListener, null);
	}




	/**
	 * @param nodeType               the type of this node.
	 * @param propertyList           the properties of this node.
	 * @param state                  the current state
	 * @param childListener          the listener for changes to child nodes (if required).
	 * @param childTransformListener the listener for child node transformations (if required).
	 */
	public SuiNode(final Class<?> nodeType,
				   final List<Property> propertyList,
				   final SuiState state,
				   final ChildListener childListener,
				   final SuiNodeChildTransformListener childTransformListener) {
		this.nodeType = nodeType;
		propertyList.forEach(property -> properties.put(property.getKey(), property));
		createChildNodesFromProperties(state);
		this.childListener = childListener;
		this.childTransformListener = childTransformListener;
	}




	/**
	 * Create the child nodes from the properties of this node and the given state.
	 *
	 * @param state the current state
	 */
	private void createChildNodesFromProperties(final SuiState state) {
		getPropertySafe(ItemListProperty.class)
				.ifPresent(property -> setChildren(createChildNodesFromItemListProperty(state, property), false));
		getPropertySafe(ItemProperty.class)
				.ifPresent(property -> setChildren(createChildNodesFromItemProperty(state, property), false));
	}




	/**
	 * Create the child nodes from given item list property and the given state.
	 *
	 * @param state    the current state
	 * @param property the item list property
	 */
	private List<SuiNode> createChildNodesFromItemListProperty(final SuiState state, final ItemListProperty property) {

		if (property.getFactories().size() < CREATE_CHILD_LIST_ASYNC_CUTOFF) {
			return property.getFactories().stream()
					.map(factory -> factory.create(state))
					.collect(Collectors.toList());
		} else {
			return LoopUtils.asyncCollectingLoop(property.getFactories(), factory -> factory.create(state));
		}
	}




	/**
	 * Create the child nodes from given item property and the given state.
	 *
	 * @param state    the current state
	 * @param property the item property
	 */
	private List<SuiNode> createChildNodesFromItemProperty(final SuiState state, final ItemProperty property) {
		return Optional.ofNullable(property.getFactory())
				.map(factory -> factory.create(state))
				.map(List::of)
				.orElse(List.of());
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
	public SuiNode getChild(final int index) {
		return this.children.get(index);
	}




	/**
	 * Finds the child node with the given id
	 *
	 * @param id the id of the requested child node
	 * @return the child node with the given id (if one exists)
	 */
	public Optional<SuiNode> findChild(final String id) {
		return Optional.ofNullable(childMap.get(id));
	}




	/**
	 * Finds the child node with the given id
	 *
	 * @param id the id of the requested child node
	 * @return the child node with the given id or null
	 */
	public SuiNode findChildUnsafe(final String id) {
		return childMap.get(id);
	}




	/**
	 * @return an unmodifiable list of children
	 */
	public List<SuiNode> getChildrenUnmodifiable() {
		return Collections.unmodifiableList(this.children);
	}




	/**
	 * @return the set ids of all child nodes (that have the id property)
	 */
	public Set<String> getChildrenIds() {
		return childMap.keySet();
	}




	/**
	 * @return the children of this node as a stream
	 */
	public Stream<SuiNode> streamChildren() {
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




	/**
	 * @return the id of this node defined by the {@link IdProperty}.
	 */
	public Optional<String> getId() {
		IdProperty idProperty = getProperty(IdProperty.class);
		if (idProperty != null) {
			return Optional.of(idProperty.getId());
		} else {
			return Optional.empty();
		}
	}




	/**
	 * @return the id of this node defined by the {@link IdProperty} or null.
	 */
	public String getIdUnsafe() {
		return getProperty(IdProperty.class).getId();
	}




	/**
	 * Replaces all children of this node with the given children
	 *
	 * @param childrenList    the list of new child nodes
	 * @param triggerListener true, to trigger the child change action
	 */
	public void setChildren(final List<SuiNode> childrenList, final boolean triggerListener) {
		this.children.clear();
		this.children.addAll(childrenList);
		this.childMap.clear();
		for (SuiNode child : this.children) {
			final IdProperty idProp = child.getProperty(IdProperty.class);
			if (idProp != null) {
				childMap.put(idProp.getId(), child);
			}
		}
		if (triggerListener) {
			triggerChildListChange();
		}
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
	 * Applies the list of operations to this node. Also optionally triggers the {@link SuiNodeChildTransformListener}.
	 * All given operations must be of the given type.
	 *
	 * @param type            the type of all given operations
	 * @param operations      the operations to apply
	 * @param triggerListener whether to trigger the listener
	 */
	public void applyTransformOperations(final OperationType type,
										 final List<? extends BaseOperation> operations,
										 final boolean triggerListener) {
		if (!operations.isEmpty()) {
			operations.forEach(operation -> {
				operation.applyTo(this.children);
				operation.applyTo(this.childMap);
			});
			if (triggerListener && getFxNode() != null) {
				if (childTransformListener != null) {
					childTransformListener.onTransformOperations(this, type, operations);
				} else {
					triggerChildListChange();
				}
			}
		}
	}









}
