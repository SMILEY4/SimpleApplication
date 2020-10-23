package de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies;

import de.ruegnerlukas.simpleapplication.simpleui.core.SuiServices;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult.MUTATED;

/**
 * A strategy used when the original node has no children. This strategy simply adds all children to the original node.
 */
public class AddAllStrategy implements ChildNodesMutationStrategy {


	/**
	 * Checks whether this strategy can be used for mutating the given node.
	 * To be able to apply this node, the following facts must be true:
	 * - the given original node has no child nodes
	 * - the given target node has at least one child node
	 *
	 * @param original          the original node to mutate
	 * @param target            the target node
	 * @param allChildrenHaveId whether all participating child nodes have an id property
	 * @return the result of the decision.
	 */
	@Override
	public StrategyDecisionResult canBeAppliedTo(final SuiNode original, final SuiNode target, final boolean allChildrenHaveId) {
		if (!original.getChildNodeStore().hasChildren() && target.getChildNodeStore().hasChildren()) {
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
		final List<SuiNode> newChildList = new ArrayList<>();
		for (int i = 0, n = target.getChildNodeStore().count(); i < n; i++) {
			final SuiNode childTarget = target.getChildNodeStore().get(i);
			SuiServices.get().enrichWithFxNodes(childTarget);
			newChildList.add(childTarget);
		}
		original.getChildNodeStore().setChildren(newChildList);
		return MUTATED;
	}

}
