package de.ruegnerlukas.simpleapplication.simpleui.mutation;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;

public interface BaseNodeMutator {


	/**
	 * The result of a mutation.
	 */
	enum MutationResult {

		/**
		 * The element can not be mutated and has to be rebuild.
		 */
		REBUILD,

		/**
		 * The element was successfully mutated.
		 */
		MUTATED
	}


	/**
	 * Mutate the given node (and all children).
	 * The properties and fx-node of the original node will be changed to match the given target node.
	 * If this is not possible, {@link MutationResult#REBUILD} will be returned.
	 *
	 * @param original the original node. If possible, the fx-node of this node will be modified.
	 * @param target   the target node with the target properties
	 * @param context  the context
	 * @return whether the node was mutated or has to be rebuild completely
	 */
	MutationResult mutateNode(SNode original, SNode target, SceneContext context);


}
