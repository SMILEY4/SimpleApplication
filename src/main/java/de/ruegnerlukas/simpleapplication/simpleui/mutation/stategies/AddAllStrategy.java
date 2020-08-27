package de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult.MUTATED;

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
	public StrategyDecisionResult canBeAppliedTo(final SUINode original, final SUINode target, final boolean allChildrenHaveId) {
		if (!original.hasChildren() && target.hasChildren()) {
			return StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA;
		} else {
			return StrategyDecisionResult.NOT_APPLICABLE;
		}
	}




	@Override
	public MutationResult mutate(final MasterNodeHandlers nodeHandlers,
								 final SUINode original,
								 final SUINode target,
								 final StrategyDecisionResult decisionData) {
		final List<SUINode> newChildList = new ArrayList<>();
		for (int i = 0; i < target.childCount(); i++) {
			final SUINode childTarget = target.getChild(i);
			nodeHandlers.getFxNodeBuilder().build(childTarget);
			newChildList.add(childTarget);
		}
		original.setChildren(newChildList, true);
		return MUTATED;
	}

}