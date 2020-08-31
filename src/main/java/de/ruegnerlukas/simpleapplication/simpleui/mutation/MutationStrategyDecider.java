package de.ruegnerlukas.simpleapplication.simpleui.mutation;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.AddAllStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.ChildNodesMutationStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.IdMutationStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.IdShuffleMutationStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.RemoveAllStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.StandardMutationStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.StrategyDecisionResult;
import de.ruegnerlukas.simpleapplication.simpleui.properties.IdProperty;

import java.util.List;

public class MutationStrategyDecider {


	/**
	 * The default list of mutation strategies.
	 */
	public static final List<ChildNodesMutationStrategy> DEFAULT_STRATEGIES = List.of(
			new RemoveAllStrategy(),
			new AddAllStrategy(),
			new IdShuffleMutationStrategy(),
			new IdMutationStrategy(),
			new StandardMutationStrategy()
	);

	/**
	 * The list of available strategies. The first possible strategy from this list will be executed.
	 */
	private final List<ChildNodesMutationStrategy> strategies;




	/**
	 * @param strategies rhe list of available strategies. The first possible strategy from this list will be executed.
	 */
	public MutationStrategyDecider(final List<ChildNodesMutationStrategy> strategies) {
		this.strategies = strategies;
	}




	/**
	 * Tries to mutate the children of the given original node to match the given target node with a matching mutation strategy.
	 *
	 * @param nodeHandlers the primary node handlers
	 * @param original     the original node
	 * @param target       the target node to match
	 * @return the result of the mutation
	 */
	public MutationResult mutate(final MasterNodeHandlers nodeHandlers, final SuiNode original, final SuiNode target) {
		if (!original.hasChildren() && !target.hasChildren()) {
			return MutationResult.MUTATED;
		}
		final boolean allHaveId = allChildrenHaveId(original) && allChildrenHaveId(target);
		for (ChildNodesMutationStrategy strategy : strategies) {
			MutationResult result = runStrategy(strategy, nodeHandlers, original, target, allHaveId);
			if (result != null) {
				return result;
			}
		}
		return MutationResult.REQUIRES_REBUILD;
	}




	/**
	 * Tries to run the given mutation strategy. Returns 'null', if the strategy could not be applied.
	 *
	 * @param nodeHandlers the primary node handlers
	 * @param original     the original node
	 * @param target       the target node to match
	 * @param allHaveId    whether all participating child nodes have an id property
	 * @return the mutation result or 'null', if the strategy could not be applied.
	 */
	private MutationResult runStrategy(final ChildNodesMutationStrategy strategy, final MasterNodeHandlers nodeHandlers,
									   final SuiNode original, final SuiNode target, final boolean allHaveId) {
		StrategyDecisionResult decisionData = strategy.canBeAppliedTo(original, target, allHaveId);
		if (decisionData.isApplicable()) {
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
	private boolean allChildrenHaveId(final SuiNode parent) {
		return parent.streamChildren().allMatch(node -> node.hasProperty(IdProperty.class));
	}

}
