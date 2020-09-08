package de.ruegnerlukas.simpleapplication.simpleui.core.mutation.operations;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * An operation that adds the given node at the given index.
 */
@Getter
public class AddOperation extends BaseOperation {


	/**
	 * The cost associated with add operations.
	 */
	public static final int ADD_COST = 1;

	/**
	 * The index
	 */
	private final int index;

	/**
	 * The node to add
	 */
	private final SuiBaseNode node;




	/**
	 * @param index the index
	 * @param node  the node to add
	 */
	public AddOperation(final int index, final SuiBaseNode node) {
		super(ADD_COST, OperationType.ADD);
		this.index = index;
		this.node = node;
	}




	@Override
	public void applyTo(final List<SuiBaseNode> list) {
		list.add(index, node);
	}




	@Override
	public void applyTo(final Map<String, SuiBaseNode> map) {
		map.put(node.getPropertyStore().getIdUnsafe(), node);
	}




	@Override
	public void applyTo(final Pane pane) {
		pane.getChildren().add(index, node.getFxNodeStore().get());
	}

}


