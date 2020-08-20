package de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult.MUTATED;

/**
 * This strategy can be applied for any situation (even when nodes do not have an id property).
 */
public class StandardMutationStrategy implements ChildNodesMutationStrategy {


	@Override
	public StrategyDecisionResult canBeAppliedTo(final SUINode original, final SUINode target, final boolean allChildrenHaveId) {
		return StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA;
	}




	@Override
	public MutationResult mutate(final MasterNodeHandlers nodeHandlers,
								 final SUINode original,
								 final SUINode target,
								 final StrategyDecisionResult decisionData) {

		final List<SUINode> newChildList = new ArrayList<>();

		boolean childrenChanged = false;
		for (int i = 0; i < Math.max(original.childCount(), target.childCount()); i++) {
			final SUINode childTarget = target.childCount() <= i ? null : target.getChild(i);
			final SUINode childOriginal = original.childCount() <= i ? null : original.getChild(i);


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
				SUINode childMutated = nodeHandlers.getMutator().mutate(childOriginal, childTarget);
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
