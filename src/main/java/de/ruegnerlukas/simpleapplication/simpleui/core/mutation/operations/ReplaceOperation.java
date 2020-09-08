package de.ruegnerlukas.simpleapplication.simpleui.core.mutation.operations;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * An operation that replaces the given node at the given index with the given new node.
 */
@Getter
public class ReplaceOperation extends BaseOperation {


	/**
	 * The cost associated with replace operations.
	 */
	public static final int REPLACE_COST = 1;

	/**
	 * The index
	 */
	private final int index;

	/**
	 * The new node
	 */
	private final SuiBaseNode nextNode;

	/**
	 * The original node
	 */
	private final SuiBaseNode prevNode;




	/**
	 * @param index    the index
	 * @param nextNode the new node
	 * @param prevNode the original node
	 */
	public ReplaceOperation(final int index, final SuiBaseNode nextNode, final SuiBaseNode prevNode) {
		super(REPLACE_COST, OperationType.REPLACE);
		this.index = index;
		this.nextNode = nextNode;
		this.prevNode = prevNode;
	}




	@Override
	public void applyTo(final List<SuiBaseNode> list) {
		list.set(index, nextNode);
	}




	@Override
	public void applyTo(final Map<String, SuiBaseNode> map) {
		map.remove(prevNode.getPropertyStore().getIdUnsafe());
		map.put(nextNode.getPropertyStore().getIdUnsafe(), nextNode);
	}




	@Override
	public void applyTo(final Pane pane) {
		pane.getChildren().set(index, nextNode.getFxNodeStore().get());
	}

}


