package de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies;

import de.ruegnerlukas.simpleapplication.simpleui.core.CoreServices;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult.MUTATED;

/**
 * This strategy can be applied for any situation (even when nodes do not have an id property).
 */
public class StandardMutationStrategy implements ChildNodesMutationStrategy {


	@Override
	public StrategyDecisionResult canBeAppliedTo(final SuiBaseNode original, final SuiBaseNode target, final boolean allChildrenHaveId) {
		return StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA;
	}




	@Override
	public MutationResult mutate(final SuiBaseNode original,
								 final SuiBaseNode target,
								 final StrategyDecisionResult decisionData) {

		final List<SuiBaseNode> newChildList = new ArrayList<>();

		final int originalChildCount = original.getChildNodeStore().count();
		final int targetChildCount = target.getChildNodeStore().count();

		for (int i = 0, n = Math.max(originalChildCount, targetChildCount); i < n; i++) {
			final SuiBaseNode childOriginal = originalChildCount <= i ? null : original.getChildNodeStore().get(i);
			final SuiBaseNode childTarget = targetChildCount <= i ? null : target.getChildNodeStore().get(i);

			if (isRemoved(childOriginal, childTarget)) {
				continue;
			}

			if (isAdded(childOriginal, childTarget)) {
				CoreServices.enrichWithFxNodes(childTarget);
				newChildList.add(childTarget);
				continue;
			}

			if (notAddedOrRemoved(childOriginal, childTarget)) {
				SuiBaseNode childMutated = CoreServices.mutateNode(childOriginal, childTarget);
				newChildList.add(childMutated);
			}

		}

		original.getChildNodeStore().setChildren(newChildList); // todo: optimize - do not set when nothing changed
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
