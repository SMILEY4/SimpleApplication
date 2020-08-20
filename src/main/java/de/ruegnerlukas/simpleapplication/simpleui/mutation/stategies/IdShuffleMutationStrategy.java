package de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.properties.IdProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This strategy can be applied when all participating child nodes haven an id property and no direct child nodes where added or removed.
 */
public class IdShuffleMutationStrategy implements ChildNodesMutationStrategy {


	/**
	 * The percentage threshold for the bulk set operation.
	 * Lower than this and the changes are applied individually, higher and the new child list is set in one call.
	 */
	private static final double THRESHOLD_BULK_SET_PERCENTAGE = 0.3;

	/**
	 * The min number of changes.
	 * Lower than this and the changes are applied individually, higher and the new child list is set in one call.
	 */
	private static final int THRESHOLD_BULK_SET_MIN = 128;




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
	public StrategyDecisionResult canBeAppliedTo(final SUINode original, final SUINode target, final boolean allChildrenHaveId) {
		if (!allChildrenHaveId || original.childCount() != target.childCount()) {
			return StrategyDecisionResult.NOT_APPLICABLE;
		} else {
			final Set<String> idsOriginal = original.getChildrenIds();
			final Set<String> idsTarget = target.getChildrenIds();
			if (idsOriginal.equals(idsTarget)) {
				return new IdShuffleDecisionResult(true, countDiffs(original, target));
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
	private int countDiffs(final SUINode original, final SUINode target) {
		int count = 0;
		for (int i = 0, n = original.childCount(); i < n; i++) {
			final String idOriginal = original.getChild(i).getProperty(IdProperty.class).getId();
			final String idTarget = target.getChild(i).getProperty(IdProperty.class).getId();
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
		public final int diffCount;




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
								 final SUINode original,
								 final SUINode target,
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
	private void mutateNoDiffs(final MasterNodeHandlers nodeHandlers, final SUINode original, final SUINode target) {

		final List<IdMutationStrategy.ReplaceOperation> replaceOperations = new ArrayList<>(original.childCount());

		for (int i = 0; i < target.childCount(); i++) {
			final SUINode originalNode = original.getChild(i);
			final SUINode targetNode = target.getChild(i);
			final SUINode childNode = nodeHandlers.getMutator().mutate(originalNode, targetNode);

			if (childNode.getFxNode() == null) {
				nodeHandlers.getFxNodeBuilder().build(childNode);
			}

			if (!originalNode.equals(childNode)) {
				replaceOperations.add(new IdMutationStrategy.ReplaceOperation(i, childNode, originalNode));
			}
		}

		if (!replaceOperations.isEmpty()) {
			original.applyChildTransformations(replaceOperations, List.of(), List.of(), List.of());
		}
	}




	/**
	 * Mutation for the case that there are differences between the (order of) ids of the original and target.
	 *
	 * @param nodeHandlers the node handlers
	 * @param original     the original node to mutate
	 * @param target       the target node
	 */
	private void mutateWithDiffs(final MasterNodeHandlers nodeHandlers, final SUINode original, final SUINode target) {
		final List<IdMutationStrategy.ReplaceOperation> replaceOperations = new ArrayList<>(original.childCount());
		final List<SUINode> newChildList = new ArrayList<>(original.childCount());

		for (int i = 0; i < target.childCount(); i++) {
			final SUINode targetChild = original.getChild(i);
			final String targetChildId = targetChild.getProperty(IdProperty.class).getId();

			final SUINode childNode = nodeHandlers.getMutator().mutate(original.findChildUnsafe(targetChildId), targetChild);

			if (childNode.getFxNode() == null) {
				nodeHandlers.getFxNodeBuilder().build(childNode);
			}

			final SUINode originalChild = original.getChild(i);
			if (!originalChild.equals(childNode)) {
				replaceOperations.add(new IdMutationStrategy.ReplaceOperation(i, childNode, originalChild));
			}
			newChildList.add(childNode);
		}

		final int threshold = (int) Math.min(THRESHOLD_BULK_SET_MIN, original.childCount() * THRESHOLD_BULK_SET_PERCENTAGE);
		if (replaceOperations.size() < threshold) {
			original.applyChildTransformations(replaceOperations, List.of(), List.of(), List.of());
		} else {
			original.setChildren(newChildList, true);
		}
	}


}
















