package de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.stategies;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;

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
	StrategyDecisionResult canBeAppliedTo(SuiNode original, SuiNode target, boolean allChildrenHaveId);

	/**
	 * Mutate the child nodes of the given original node to match the given target node.
	 *
	 * @param original     the original node
	 * @param target       the target node to match
	 * @param tags         tags associated with the state update triggering this mutation
	 * @param decisionData the decision result instance created in {@link ChildNodesMutationStrategy#canBeAppliedTo}
	 * @return the result of the mutation
	 */
	MutationResult mutate(SuiNode original, SuiNode target, Tags tags, StrategyDecisionResult decisionData);


}
