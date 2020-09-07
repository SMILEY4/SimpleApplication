package de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.operations.OperationType;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.operations.ReplaceOperation;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This strategy can be applied when all participating child nodes haven an id property and no direct child nodes where added or removed.
 * This strategy is optimized for when the order of ids was not changed, or was changed for the majority of nodes ( >x% of children).
 */
public class IdShuffleMutationStrategy implements ChildNodesMutationStrategy {


	/**
	 * The percentage that defines "the majority of child nodes".
	 * This strategy is only used, when more nodes than this percentage changes position
	 */
	private static final double PERCENTAGE_MAJORITY = 0.6;




	/**
	 * Checks whether this strategy can be used for mutating the given node.
	 * To be able to apply this node, the following facts must be true:
	 * - all participating child nodes have an id property
	 * - the amount of child nodes must match
	 * - the child nodes of the original and target node must have the same ids (in any order)
	 *
	 * @param original          the original node to mutate
	 * @param target            the target node
	 * @param allChildrenHaveId whether all participating child nodes have an id property
	 * @return the result of the decision.
	 */
	@Override
	public StrategyDecisionResult canBeAppliedTo(final SuiNode original, final SuiNode target, final boolean allChildrenHaveId) {
		if (!allChildrenHaveId || original.childCount() != target.childCount()) {
			return StrategyDecisionResult.NOT_APPLICABLE;
		} else {
			final Set<String> idsOriginal = original.getChildrenIds();
			final Set<String> idsTarget = target.getChildrenIds();
			if (idsOriginal.equals(idsTarget)) {
				final int diffCount = countDiffs(original, target);
				if (diffCount == 0 || (double) diffCount / (double) idsOriginal.size() > PERCENTAGE_MAJORITY) {
					return new IdShuffleDecisionResult(true, diffCount);
				} else {
					return StrategyDecisionResult.NOT_APPLICABLE;
				}
			} else {
				return StrategyDecisionResult.NOT_APPLICABLE;
			}
		}
	}




	/**
	 * Count the differences between the original children and target children.
	 * A 'diff' is counted when the id of the original and target node do not match at any index in the list.
	 *
	 * @param original the original node
	 * @param target   the target node
	 * @return the number of differences
	 */
	private int countDiffs(final SuiNode original, final SuiNode target) {
		int count = 0;
		for (int i = 0, n = original.childCount(); i < n; i++) {
			final String idOriginal = original.getChild(i).getIdUnsafe();
			final String idTarget = target.getChild(i).getIdUnsafe();
			if (!idOriginal.equals(idTarget)) {
				count++;
			}
		}
		return count;
	}




	/**
	 * The decision result with additional data for the mutation phase of the {@link IdShuffleMutationStrategy}.
	 */
	public static class IdShuffleDecisionResult extends StrategyDecisionResult {


		/**
		 * The number of differences.
		 * A 'diff' is counted when the id of the original and target node do not match at any index in the list.
		 */
		@Getter
		private final int diffCount;




		/**
		 * @param canBeApplied whether the mutation strategy can be used for the mutation.
		 * @param diffCount    the number of differences
		 */
		public IdShuffleDecisionResult(final boolean canBeApplied, final int diffCount) {
			super(canBeApplied);
			this.diffCount = diffCount;
		}

	}




	@Override
	public MutationResult mutate(final MasterNodeHandlers nodeHandlers,
								 final SuiNode original,
								 final SuiNode target,
								 final StrategyDecisionResult decisionData) {
		final IdShuffleDecisionResult decision = (IdShuffleDecisionResult) decisionData;
		if (decision.diffCount == 0) {
			mutateNoDiffs(nodeHandlers, original, target);
		} else {
			mutateWithDiffs(nodeHandlers, original, target);
		}
		return MutationResult.MUTATED;
	}




	/**
	 * Mutation for the case that no differences between the (order of) ids of the original and target node exist.
	 * In this special case, there is no need to search for the correct target child.
	 *
	 * @param nodeHandlers the node handlers
	 * @param original     the original node to mutate
	 * @param target       the target node
	 */
	private void mutateNoDiffs(final MasterNodeHandlers nodeHandlers, final SuiNode original, final SuiNode target) {
		final List<ReplaceOperation> replaceOperations = new ArrayList<>(original.childCount());
		for (int i = 0; i < target.childCount(); i++) {
			final SuiNode originalNode = original.getChild(i);
			final SuiNode targetNode = target.getChild(i);
			final SuiNode childNode = nodeHandlers.getMutator().mutate(originalNode, targetNode);
			if (childNode.getFxNode() == null) {
				nodeHandlers.getFxNodeBuilder().build(childNode);
			}
			if (!originalNode.equals(childNode)) {
				replaceOperations.add(new ReplaceOperation(i, childNode, originalNode));
			}
		}
		original.applyTransformOperations(OperationType.REPLACE, replaceOperations, true);
	}




	/**
	 * Mutation for the case that there are differences between the (order of) ids of the original and target.
	 *
	 * @param nodeHandlers the node handlers
	 * @param original     the original node to mutate
	 * @param target       the target node
	 */
	private void mutateWithDiffs(final MasterNodeHandlers nodeHandlers, final SuiNode original, final SuiNode target) {
		final List<SuiNode> newChildList = new ArrayList<>(original.childCount());
		for (int i = 0, n = target.childCount(); i < n; i++) {
			final SuiNode targetChild = target.getChild(i);
			final String targetChildId = targetChild.getIdUnsafe();
			final SuiNode originalChild = original.findChildUnsafe(targetChildId);
			final SuiNode newChildNode = nodeHandlers.getMutator().mutate(originalChild, targetChild);
			newChildList.add(newChildNode);
		}
		original.setChildren(newChildList, true);
	}


}
















