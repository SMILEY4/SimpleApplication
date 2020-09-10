package de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies;

import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;

/**
 * A strategy used when the target node has no children. This strategy simply removes all children from the original node.
 */
public class RemoveAllStrategy implements ChildNodesMutationStrategy {


	/**
	 * Checks whether this strategy can be used for mutating the given node.
	 * To be able to apply this node, the following facts must be true:
	 * - the given original node has at least one child node
	 * - the given target node has no child nodes
	 *
	 * @param original          the original node to mutate
	 * @param target            the target node
	 * @param allChildrenHaveId whether all participating child nodes have an id property
	 * @return the result of the decision.
	 */
	@Override
	public StrategyDecisionResult canBeAppliedTo(final SuiNode original, final SuiNode target, final boolean allChildrenHaveId) {
		if (original.getChildNodeStore().hasChildren() && !target.getChildNodeStore().hasChildren()) {
			return StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA;
		} else {
			return StrategyDecisionResult.NOT_APPLICABLE;
		}
	}




	@Override
	public MutationResult mutate(final SuiNode original,
								 final SuiNode target,
								 final Tags tags,
								 final StrategyDecisionResult decisionData) {
		original.getChildNodeStore().clearChildren();
		return MutationResult.MUTATED;
	}

}
