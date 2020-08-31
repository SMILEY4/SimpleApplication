package de.ruegnerlukas.simpleapplication.simpleui.utils;

import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.operations.OperationType;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.operations.RemoveOperation;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.stream.Collectors;

public final class SuiUtils {


	/**
	 * Hidden constructor.
	 */
	private SuiUtils() {
		// hidden constructor
	}




	/**
	 * @return a default generic implementation of a {@link SuiNode.ChildListener} for {@link Pane}s.
	 */
	public static SuiNode.ChildListener defaultPaneChildListener() {
		return node -> {
			final Pane pane = (Pane) node.getFxNode();
			if (node.hasChildren()) {
				pane.getChildren().setAll(node.streamChildren()
						.map(SuiNode::getFxNode)
						.collect(Collectors.toList()));
			} else {
				pane.getChildren().clear();
			}
		};
	}




	/**
	 * @return a default generic implementation of a {@link SuiNode.ChildTransformListener} for {@link Pane}s.
	 */
	public static SuiNode.ChildTransformListener defaultPaneChildTransformListener() {
		return (node, type, ops) -> {
			final Pane pane = (Pane) node.getFxNode();
			if (type == OperationType.REMOVE) {
				List<Node> nodesToRemove = ops.stream()
						.map(op -> (RemoveOperation) op)
						.map(op -> op.getNode().getFxNode())
						.collect(Collectors.toList());
				pane.getChildren().removeAll(nodesToRemove);
			} else {
				ops.forEach(op -> op.applyTo(pane));
			}
		};
	}


}
