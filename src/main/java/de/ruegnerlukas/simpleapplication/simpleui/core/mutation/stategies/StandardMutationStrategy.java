package de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies;

import de.ruegnerlukas.simpleapplication.simpleui.core.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult.MUTATED;

/**
 * This strategy can be applied for any situation (even when nodes do not have an id property).
 */
public class StandardMutationStrategy implements ChildNodesMutationStrategy {


	@Override
	public StrategyDecisionResult canBeAppliedTo(final SuiNode original, final SuiNode target, final boolean allChildrenHaveId) {
		return StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA;
	}




	@Override
	public MutationResult mutate(final MasterNodeHandlers nodeHandlers,
								 final SuiNode original,
								 final SuiNode target,
								 final StrategyDecisionResult decisionData) {

		final List<SuiNode> newChildList = new ArrayList<>();

		boolean childrenChanged = false;
		for (int i = 0; i < Math.max(original.childCount(), target.childCount()); i++) {
			final SuiNode childTarget = target.childCount() <= i ? null : target.getChild(i);
			final SuiNode childOriginal = original.childCount() <= i ? null : original.getChild(i);


			if (isRemoved(childOriginal, childTarget)) {
				childrenChanged = true;
				continue;
			}

			if (isAdded(childOriginal, childTarget)) {
				nodeHandlers.getFxNodeBuilder().build(childTarget);
				newChildList.add(childTarget);
				childrenChanged = true;
				continue;
			}

			if (notAddedOrRemoved(childOriginal, childTarget)) {
				SuiNode childMutated = nodeHandlers.getMutator().mutate(childOriginal, childTarget);
				newChildList.add(childMutated);
				childrenChanged = true;
			}

		}

		original.setChildren(newChildList, childrenChanged);
		return MUTATED;
	}




	/**
	 * Check whether the object was added (i.e. only the target object exists).
	 *
	 * @param original the original object
	 * @param target   the target object
	 * @return whether the object was added.
	 */
	private boolean isAdded(final Object original, final Object target) {
		return (original == null) && (target != null);
	}




	/**
	 * Check whether the object was removed (i.e. only the original object exists).
	 *
	 * @param original the original object
	 * @param target   the target object
	 * @return whether the object was removed.
	 */
	private boolean isRemoved(final Object original, final Object target) {
		return (original != null) && (target == null);
	}




	/**
	 * Check whether the object was neither removed nor added (both != null).
	 *
	 * @param original the original object
	 * @param target   the target object
	 * @return true, if the object was neither removed nor added
	 */
	private boolean notAddedOrRemoved(final Object original, final Object target) {
		return (original != null) && (target != null);
	}


}