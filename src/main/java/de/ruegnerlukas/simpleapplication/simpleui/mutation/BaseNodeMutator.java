package de.ruegnerlukas.simpleapplication.simpleui.mutation;


import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;

public interface BaseNodeMutator {


	/**
	 * Mutate the given node (and all children).
	 * The properties and fx-node of the original node will be changed to match the given target node.
	 * If this is not possible, {@link MutationResult#REQUIRES_REBUILD} will be returned.
	 *
	 * @param original     the original node. If possible, the fx-node of this node will be modified.
	 * @param target       the target node with the target properties
	 * @param nodeHandlers the primary node handlers
	 * @return whether the node was mutated or has to be rebuild completely
	 */
	MutationResult mutateNode(SuiNode original, SuiNode target, MasterNodeHandlers nodeHandlers);


}
