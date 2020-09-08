package de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies;

import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;

public interface ChildNodesMutationStrategy {


	/**
	 * Checks whether this strategy can be used for mutating the given node.
	 * The returned decision result instance will then be available in {@link ChildNodesMutationStrategy#mutate}
	 *
	 * @param original          the original node to mutate
	 * @param target            the target node
	 * @param allChildrenHaveId whether all participating child nodes have an id property
	 * @return the result of the decision.
	 */
	StrategyDecisionResult canBeAppliedTo(SuiBaseNode original, SuiBaseNode target, boolean allChildrenHaveId);

	/**
	 * Mutate the child nodes of the given original node to match the given target node.
	 *
	 * @param original     the original node
	 * @param target       the target node to match
	 * @param decisionData the decision result instance created in {@link ChildNodesMutationStrategy#canBeAppliedTo}
	 * @return the result of the mutation
	 */
	MutationResult mutate(SuiBaseNode original, SuiBaseNode target, StrategyDecisionResult decisionData);


}
