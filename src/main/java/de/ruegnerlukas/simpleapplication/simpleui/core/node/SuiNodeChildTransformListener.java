package de.ruegnerlukas.simpleapplication.simpleui.core.node;

import de.ruegnerlukas.simpleapplication.simpleui.core.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.operations.BaseOperation;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.operations.OperationType;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.operations.RemoveOperation;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.stream.Collectors;

public interface SuiNodeChildTransformListener {


	/**
	 * A child transform listener that does nothing.
	 */
	SuiNodeChildTransformListener NO_OP = (node, type, operations) -> {
		// to nothing
	};

	/**
	 * A child transform listener applicable to most cases.
	 */
	SuiNodeChildTransformListener DEFAULT = (node, type, operations) -> {
		final Pane pane = (Pane) node.getFxNode();
		if (type == OperationType.REMOVE) {
			List<Node> nodesToRemove = operations.stream()
					.map(op -> (RemoveOperation) op)
					.map(op -> op.getNode().getFxNode())
					.collect(Collectors.toList());
			pane.getChildren().removeAll(nodesToRemove);
		} else {
			operations.forEach(op -> op.applyTo(pane));
		}
	};


	/**
	 * The given operations of the given type must be applied to the children of the fx-node of the given parent node.
	 *
	 * @param parent     the parent node
	 * @param type       the type of all of the operations
	 * @param operations the operations to apply
	 */
	void onTransformOperations(SuiNode parent, OperationType type, List<? extends BaseOperation> operations);

}
