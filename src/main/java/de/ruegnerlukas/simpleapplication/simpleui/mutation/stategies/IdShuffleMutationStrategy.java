package de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import de.ruegnerlukas.simpleapplication.simpleui.properties.IdProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IdShuffleMutationStrategy implements ChildNodesMutationStrategy {


	@Override
	public DecisionData canBeAppliedTo(final SUINode original, final SUINode target, final boolean allChildrenHaveId) {
		if (!allChildrenHaveId || original.childCount() != target.childCount()) {
			return DecisionData.NOT_APPLIABLE;
		} else {
			final Set<String> idsOriginal = original.getChildrenIds();
			final Set<String> idsTarget = target.getChildrenIds();
			if (idsOriginal.equals(idsTarget)) {
				return new IdShuffleDecisionData(true, countDiffs(original, target));
			} else {
				return DecisionData.NOT_APPLIABLE;
			}
		}
	}




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




	public static class IdShuffleDecisionData extends DecisionData {


		public int diffCount;




		public IdShuffleDecisionData(final boolean canBeApplied, int diffCount) {
			super(canBeApplied);
			this.diffCount = diffCount;
		}

	}




	@Override
	public BaseNodeMutator.MutationResult mutate(final MasterNodeHandlers nodeHandlers, final SUINode original, final SUINode target, final DecisionData decisionData) {
		final IdShuffleDecisionData idShuffleDecisionData = (IdShuffleDecisionData) decisionData;
		if (idShuffleDecisionData.diffCount == 0) {
			mutateNoDiffs(nodeHandlers, original, target);
		} else {
			mutateWithDiffs(nodeHandlers, original, target);
		}

		return BaseNodeMutator.MutationResult.MUTATED;
	}




	private void mutateNoDiffs(final MasterNodeHandlers nodeHandlers, final SUINode original, final SUINode target) {

		List<IdMutationStrategy.ReplaceOperation> replaceOperations = new ArrayList<>(original.childCount());

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

		original.applyChildTransformations(replaceOperations, List.of(), List.of(), List.of());


	}




	private void mutateWithDiffs(final MasterNodeHandlers nodeHandlers, final SUINode original, final SUINode target) {
		List<IdMutationStrategy.ReplaceOperation> replaceOperations = new ArrayList<>(original.childCount());
		List<SUINode> newNodes = new ArrayList<>(original.childCount());

		for (int i = 0; i < target.childCount(); i++) {

			final SUINode originalNode = original.getChild(i);
			final String targetId = target.getChild(i).getProperty(IdProperty.class).getId();

			final SUINode childNode = nodeHandlers.getMutator().mutate(original.findChildUnsafe(targetId), target.getChild(i));
			if (childNode.getFxNode() == null) {
				nodeHandlers.getFxNodeBuilder().build(childNode);
			}

			newNodes.add(childNode);
			if (!originalNode.equals(childNode)) {
				replaceOperations.add(new IdMutationStrategy.ReplaceOperation(i, childNode, originalNode));
			}
		}

		if (replaceOperations.size() < Math.min(128, original.childCount() / 3)) {
			original.applyChildTransformations(replaceOperations, List.of(), List.of(), List.of());
		} else {
			original.setChildren(newNodes, true);
		}
	}


}
















