package de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.operations;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import javafx.scene.Node;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * The interface for operations on SUINodes
 */
@Getter
@AllArgsConstructor
public abstract class BaseOperation {


	/**
	 * The "cost"-value associated with this operation
	 */
	private final int cost;

	/**
	 * The type of this operation.
	 */
	private final OperationType type;




	/**
	 * Applies this operation to the given list of nodes.
	 *
	 * @param list the list to modify
	 */
	public abstract void applyTo(List<SuiNode> list);

	/**
	 * Applies this operation to the given map of nodes.
	 *
	 * @param map the map to modify. The key is the id of the node and the value is the node.
	 */
	public abstract void applyTo(Map<String, SuiNode> map);

	/**
	 * Applies this operation to the given child nodes.
	 *
	 * @param nodes the child node list to modify
	 */
	public abstract void applyToFx(List<Node> nodes);


}
