package de.ruegnerlukas.simpleapplication.simpleui.mutation;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.AddAllStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.ChildNodesMutationStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.IdMutationStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.IdShuffleMutationStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.RemoveAllStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.StandardMutationStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.properties.IdProperty;

public class MutationStrategyDecider {


	private final ChildNodesMutationStrategy standardStrategy = new StandardMutationStrategy();

	private final ChildNodesMutationStrategy idStrategy = new IdMutationStrategy();

	private final ChildNodesMutationStrategy removeAllStrategy = new RemoveAllStrategy();

	private final ChildNodesMutationStrategy addAllStrategy = new AddAllStrategy();

	private final ChildNodesMutationStrategy idShuffleStrategy = new IdShuffleMutationStrategy();




	public BaseNodeMutator.MutationResult mutate(final MasterNodeHandlers nodeHandlers, final SUINode original, final SUINode target) {

		if (!original.hasChildren() && !target.hasChildren()) {
			return BaseNodeMutator.MutationResult.MUTATED;
		}

		final boolean allHaveId = allChildrenHaveId(original) && allChildrenHaveId(target);

		BaseNodeMutator.MutationResult resultRemoveAll = runStrategy(removeAllStrategy, nodeHandlers, original, target, allHaveId);
		if (resultRemoveAll != null) {
			return resultRemoveAll;
		}

		BaseNodeMutator.MutationResult resultAddAll = runStrategy(addAllStrategy, nodeHandlers, original, target, allHaveId);
		if (resultAddAll != null) {
			return resultAddAll;
		}

		BaseNodeMutator.MutationResult resultIdShuffle = runStrategy(idShuffleStrategy, nodeHandlers, original, target, allHaveId);
		if (resultIdShuffle != null) {
			return resultIdShuffle;
		}

		BaseNodeMutator.MutationResult resultIdStrategy = runStrategy(idStrategy, nodeHandlers, original, target, allHaveId);
		if (resultIdStrategy != null) {
			return resultIdStrategy;
		}

		return runStrategy(standardStrategy, nodeHandlers, original, target, allHaveId);
	}




	private BaseNodeMutator.MutationResult runStrategy(final ChildNodesMutationStrategy strategy, final MasterNodeHandlers nodeHandlers,
													   final SUINode original, final SUINode target, final boolean allHaveId) {
		ChildNodesMutationStrategy.DecisionData decisionData = strategy.canBeAppliedTo(original, target, allHaveId);
		if (decisionData.canBeApplied) {
			return strategy.mutate(nodeHandlers, original, target, decisionData);
		} else {
			return null;
		}
	}




	/**
	 * Checks whether all children of the given node have a valid id
	 *
	 * @param parent the parent to check
	 * @return whether all nodes have a valid id
	 */
	private boolean allChildrenHaveId(final SUINode parent) {
		return parent.streamChildren().allMatch(node -> node.hasProperty(IdProperty.class));
	}

}
