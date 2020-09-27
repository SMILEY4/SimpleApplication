package de.ruegnerlukas.simpleapplication.simpleui.core.mutation.operations;


import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.Node;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * An operation that removes the given node at the given index.
 */
@Getter
public class RemoveOperation extends BaseOperation {


	/**
	 * The cost associated with remove operations.
	 */
	public static final int REMOVE_COST = 1;

	/**
	 * The index of the node to remove
	 */
	private final int index;

	/**
	 * The node to remove
	 */
	private final SuiNode node;




	/**
	 * @param index the index of the node to remove
	 * @param node  the node to remove
	 */
	public RemoveOperation(final int index, final SuiNode node) {
		super(REMOVE_COST, OperationType.REMOVE);
		this.index = index;
		this.node = node;
	}




	@Override
	public void applyTo(final List<SuiNode> list) {
		list.remove(index);
	}




	@Override
	public void applyTo(final Map<String, SuiNode> map) {
		map.remove(node.getPropertyStore().getIdUnsafe());
	}




	@Override
	public void applyToFx(final List<Node> nodes) {
		nodes.remove(index);
	}

}

