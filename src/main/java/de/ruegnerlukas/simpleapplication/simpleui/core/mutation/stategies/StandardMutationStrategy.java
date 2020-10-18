package de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies;

import de.ruegnerlukas.simpleapplication.simpleui.core.SuiServices;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;

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
	public MutationResult mutate(final SuiNode original,
								 final SuiNode target,
								 final Tags tags,
								 final StrategyDecisionResult decisionData) {

		final List<SuiNode> newChildList = new ArrayList<>();

		final int originalChildCount = original.getChildNodeStore().count();
		final int targetChildCount = target.getChildNodeStore().count();

		boolean wasChanged = false;

		for (int i = 0, n = Math.max(originalChildCount, targetChildCount); i < n; i++) {
			final SuiNode childOriginal = originalChildCount <= i ? null : original.getChildNodeStore().get(i);
			final SuiNode childTarget = targetChildCount <= i ? null : target.getChildNodeStore().get(i);

			if (isRemoved(childOriginal, childTarget)) {
				wasChanged = true;
				continue;
			}

			if (isAdded(childOriginal, childTarget)) {
				SuiServices.get().enrichWithFxNodes(childTarget);
				newChildList.add(childTarget);
				wasChanged = true;
				continue;
			}

			if (notAddedOrRemoved(childOriginal, childTarget)) {
				SuiNode childMutated = SuiServices.get().mutateNode(childOriginal, childTarget, tags);
				newChildList.add(childMutated);
				if (!childMutated.equals(childOriginal)) {
					wasChanged = true;
				}
			}

		}

		if (wasChanged) {
			original.getChildNodeStore().setChildren(newChildList);
		}
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
