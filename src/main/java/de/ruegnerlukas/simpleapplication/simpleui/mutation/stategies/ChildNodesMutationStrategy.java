package de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;

public interface ChildNodesMutationStrategy {


	/**
	 * Mutate the child nodes of the given original node to match the given target node.
	 *
	 * @param nodeHandlers the primary node handlers
	 * @param original     the original node
	 * @param target       the target node to match
	 * @return the result of the mutation
	 */
	BaseNodeMutator.MutationResult mutate(MasterNodeHandlers nodeHandlers, SUINode original, SUINode target);

}
