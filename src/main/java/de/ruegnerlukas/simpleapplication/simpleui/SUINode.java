package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.common.AsyncChunkProcessor;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.IdMutationStrategy.AddOperation;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.IdMutationStrategy.Operation;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.IdMutationStrategy.RemoveOperation;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.IdMutationStrategy.ReplaceOperation;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.IdMutationStrategy.SwapOperation;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SUINode {


	private static final int N_CORES = Runtime.getRuntime().availableProcessors();

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

	@Getter
	private ChildTransformListener childTransformListener;

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
		this(nodeType, propertyList, state, childListener, null);
	}




	public SUINode(final Class<?> nodeType, final List<Property> propertyList,
				   final SUIState state, final ChildListener childListener, final ChildTransformListener childTransformListener) {
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
	private void createChildNodesFromProperties(final SUIState state) {
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
	private List<SUINode> createChildNodesFromItemListProperty(final SUIState state, final ItemListProperty property) {

		if (property.getFactories().size() < 1028) {
			return property.getFactories().stream()
					.map(factory -> factory.create(state))
					.collect(Collectors.toList());
		} else {
			return new AsyncChunkProcessor<>(property.getFactories(), property.getFactories().size() / N_CORES, factories ->
					factories.stream()
							.map(factory -> factory.create(state))
							.collect(Collectors.toList()))
					.get();
		}
	}




	/**
	 * Create the child nodes from given item property and the given state.
	 *
	 * @param state    the current state
	 * @param property the item property
	 */
	private List<SUINode> createChildNodesFromItemProperty(final SUIState state, final ItemProperty property) {
		return List.of(property.getFactory().create(state));
	}




	/**
	 * Inform the listener (if possible) of any changes to the child nodes.
	 */
	public void triggerChildListChange() {
		if (childListener != null && getFxNode() != null) {
			childListener.onChange(this);
		}
	}




	public void triggerChildListTransform(final List<ReplaceOperation> replaceOperations,
										  final List<RemoveOperation> removeOperations,
										  final List<AddOperation> addOperations,
										  final List<SwapOperation> swapOperations) {
		if (childTransformListener != null && getFxNode() != null) {
			childTransformListener.onTransform(this, replaceOperations, removeOperations, addOperations, swapOperations);
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
	 * Finds the child node with the given id
	 *
	 * @param id the id of the requested child node
	 * @return the child node with the given id or null
	 */
	public SUINode findChildUnsafe(final String id) {
		return childMap.get(id);
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
	 * todo: prototype
	 */
	public void applyChildTransformations(final List<ReplaceOperation> replaceOperations,
										  final List<RemoveOperation> removeOperations,
										  final List<AddOperation> addOperations,
										  final List<SwapOperation> swapOperations) {

				/*
		Order of operations:
		- remove
		- add
		- swap
		- replace
		 */

				int cost = 0;
		for (Operation operation : removeOperations) {
			operation.apply(this.children);
			operation.apply(this.childMap);
			cost += operation.getCost();
		}
		for (Operation operation : addOperations) {
			operation.apply(this.children);
			operation.apply(this.childMap);
			cost += operation.getCost();
		}
		for (Operation operation : swapOperations) {
			operation.apply(this.children);
			operation.apply(this.childMap);
			cost += operation.getCost();
		}
		for (Operation operation : replaceOperations) {
			operation.apply(this.children);
			operation.apply(this.childMap);
			cost += operation.getCost();
		}

		if (childTransformListener != null && cost < 10000) {
			triggerChildListTransform(replaceOperations, removeOperations, addOperations, swapOperations);
		} else {
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






	public interface ChildTransformListener {


		void onTransform(SUINode parent, List<ReplaceOperation> replaceOperations, List<RemoveOperation> removeOperations, List<AddOperation> addOperations, List<SwapOperation> swapOperations);

	}

}
