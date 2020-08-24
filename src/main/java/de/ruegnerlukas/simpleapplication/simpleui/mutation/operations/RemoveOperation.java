package de.ruegnerlukas.simpleapplication.simpleui.mutation.operations;


import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import javafx.scene.layout.Pane;
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
	private final SUINode node;




	/**
	 * @param index the index of the node to remove
	 * @param node  the node to remove
	 */
	public RemoveOperation(final int index, final SUINode node) {
		super(REMOVE_COST, OperationType.REMOVE);
		this.index = index;
		this.node = node;
	}




	@Override
	public void applyTo(final List<SUINode> list) {
		list.remove(index);
	}




	@Override
	public void applyTo(final Map<String, SUINode> map) {
		map.remove(node.getIdUnsafe());
	}




	@Override
	public void applyTo(final Pane pane) {
		pane.getChildren().remove(index);
	}

}

