package de.ruegnerlukas.simpleapplication.simpleui.core.mutation.operations;

import de.ruegnerlukas.simpleapplication.simpleui.core.SuiNode;
import javafx.scene.layout.Pane;
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
	 * Applies this operation to the child nodes of the given javafx pane.
	 *
	 * @param pane the pane to modify
	 */
	public abstract void applyTo(Pane pane);


}
