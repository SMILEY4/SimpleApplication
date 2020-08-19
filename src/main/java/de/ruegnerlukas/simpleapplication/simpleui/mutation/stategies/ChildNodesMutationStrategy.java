package de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import lombok.AllArgsConstructor;

public interface ChildNodesMutationStrategy {


	DecisionData canBeAppliedTo(SUINode original, SUINode target, boolean allChildrenHaveId);

	/**
	 * Mutate the child nodes of the given original node to match the given target node.
	 *
	 * @param nodeHandlers the primary node handlers
	 * @param original     the original node
	 * @param target       the target node to match
	 * @return the result of the mutation
	 */
	BaseNodeMutator.MutationResult mutate(MasterNodeHandlers nodeHandlers, SUINode original, SUINode target, DecisionData decisionData);


	@AllArgsConstructor
	class DecisionData {

		public static final DecisionData NOT_APPLIABLE = new DecisionData(false);

		public static final DecisionData APPLIABLE_NO_DATA = new DecisionData(true);

		public boolean canBeApplied;

	}

}
