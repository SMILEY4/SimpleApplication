package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.simpleui.mutation.operations.OperationType;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.operations.RemoveOperation;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.stream.Collectors;

public final class SUIUtils {


	/**
	 * Hidden constructor.
	 */
	private SUIUtils() {
		// hidden constructor
	}




	/**
	 * @return a default generic implementation of a {@link SUINode.ChildListener} for {@link Pane}s.
	 */
	public static SUINode.ChildListener defaultPaneChildListener() {
		return node -> {
			final Pane pane = (Pane) node.getFxNode();
			if (node.hasChildren()) {
				pane.getChildren().setAll(node.streamChildren()
						.map(SUINode::getFxNode)
						.collect(Collectors.toList()));
			} else {
				pane.getChildren().clear();
			}
		};
	}




	/**
	 * @return a default generic implementation of a {@link SUINode.ChildTransformListener} for {@link Pane}s.
	 */
	public static SUINode.ChildTransformListener defaultPaneChildTransformListener() {
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
