package de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.operations;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import javafx.scene.Node;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * An operation that swaps the nodes at the given indices.
 */
@Getter
public class SwapOperation extends BaseOperation {


	/**
	 * The cost associated with swap operations.
	 */
	public static final int SWAP_COST = 3;

	/**
	 * The smaller index,
	 */
	private final int indexMin;

	/**
	 * The large index,
	 */
	private final int indexMax;




	/**
	 * @param indexMin the smaller index
	 * @param indexMax the large index
	 */
	public SwapOperation(final int indexMin, final int indexMax) {
		super(SWAP_COST, OperationType.SWAP);
		this.indexMin = indexMin;
		this.indexMax = indexMax;
	}




	@Override
	public void applyTo(final List<SuiNode> list) {
		SuiNode elementMax = list.remove(indexMax);
		SuiNode elementMin = list.set(indexMin, elementMax);
		list.add(indexMax, elementMin);
	}




	@Override
	public void applyTo(final Map<String, SuiNode> map) {
		// do nothing here
	}




	@Override
	public void applyToFx(final List<Node> nodes) {
		Node elementMax = nodes.remove(indexMax);
		Node elementMin = nodes.set(indexMin, elementMax);
		nodes.add(indexMax, elementMin);
	}


}



