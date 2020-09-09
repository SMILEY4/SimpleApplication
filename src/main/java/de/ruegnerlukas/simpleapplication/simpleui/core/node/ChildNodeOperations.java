package de.ruegnerlukas.simpleapplication.simpleui.core.node;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.operations.BaseOperation;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.operations.OperationType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public abstract class ChildNodeOperations {


	/**
	 * The listener for changes to child nodes (if required).
	 */
	@Getter
	private final SuiNodeChildListener childListener;

	/**
	 * The listener for child node transformations (if required).
	 */
	@Getter
	private final SuiNodeChildTransformListener childTransformListener;

	/**
	 * The simpleui node these operations work on
	 */
	@Setter (AccessLevel.PROTECTED)
	private SuiNode managedNode;




	/**
	 * @param childListener          the listener for changes to child nodes (if required).
	 * @param childTransformListener the listener for child node transformations (if required).
	 */
	public ChildNodeOperations(final SuiNodeChildListener childListener, final SuiNodeChildTransformListener childTransformListener) {
		this.childListener = childListener;
		this.childTransformListener = childTransformListener;
	}




	/**
	 * @return the list of child nodes
	 */
	protected abstract List<SuiNode> getChildren();

	/**
	 * @return the child nodes with their id as the key
	 */
	protected abstract Map<String, SuiNode> getChildMap();




	/**
	 * Replaces the child nodes of this node with the given child nodes
	 *
	 * @param children the new child nodes
	 */
	public void setChildren(final List<SuiNode> children) {
		Validations.INPUT.notNull(children).exception("The new children may not be null.");
		Validations.INPUT.containsNoNull(children).exception("The new children may not contain null-elements.");
		getChildren().clear();
		getChildren().addAll(children);
		rebuildChildMap();
		if (childListener != null) {
			childListener.onChange(this.managedNode);
		}
	}




	/**
	 * Removes all child nodes of this node.
	 */
	public void clearChildren() {
		getChildren().clear();
		getChildMap().clear();
		if (childListener != null) {
			childListener.onChange(managedNode);
		}
	}




	/**
	 * Applies the list of operations to this node.
	 * All given operations must be of the given type.
	 *
	 * @param type       the type of all given operations
	 * @param operations the operations to apply
	 */
	public void applyTransformOperations(final OperationType type, final List<? extends BaseOperation> operations) {
		applyTransformOperations(type, operations, false);
	}




	/**
	 * Applies the list of operations to this node.
	 * All given operations must be of the given type.
	 *
	 * @param type       the type of all given operations
	 * @param operations the operations to apply
	 * @param silent     whether to trigger the listener
	 */
	public void applyTransformOperations(final OperationType type, final List<? extends BaseOperation> operations, final boolean silent) {
		if (!operations.isEmpty()) {
			operations.forEach(operation -> {
				operation.applyTo(getChildren());
				operation.applyTo(getChildMap());
			});
			if (!silent) {
				if (getChildTransformListener() != null) {
					getChildTransformListener().onTransformOperations(managedNode, type, operations);
				} else if (getChildListener() != null) {
					getChildListener().onChange(managedNode);
				}
			}
		}
	}




	/**
	 * Completely rebuilds the child map.
	 */
	private void rebuildChildMap() {
		final Map<String, SuiNode> childMap = getChildMap();
		childMap.clear();
		for (SuiNode child : getChildren()) {
			final String id = child.getPropertyStore().getIdUnsafe();
			if (id != null) {
				childMap.put(id, child);
			}
		}
	}

}
