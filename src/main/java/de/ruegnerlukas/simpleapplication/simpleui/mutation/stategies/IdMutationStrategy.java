package de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies;

import de.ruegnerlukas.simpleapplication.common.utils.Loop;
import de.ruegnerlukas.simpleapplication.common.utils.Pair;
import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.operations.AddOperation;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.operations.BaseOperation;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.operations.OperationType;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.operations.RemoveOperation;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.operations.ReplaceOperation;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.operations.SwapOperation;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.ListTransformer.BaseTransformation;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.ListTransformer.ReplaceTransformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * This strategy can be applied in any situation where all participating child nodes haven an id property.
 */
public class IdMutationStrategy implements ChildNodesMutationStrategy {


	/**
	 * The cutoff value dictating when refresh the fx child list completely and when to transform it with individual operations.
	 */
	private static final int COST_CUTOFF = 8192;




	/**
	 * Checks whether this strategy can be used for mutating the given node.
	 * To be able to apply this node, the following facts must be true:
	 * - all participating child nodes have an id property
	 *
	 * @param original          the original node to mutate
	 * @param target            the target node
	 * @param allChildrenHaveId whether all participating child nodes have an id property
	 * @return the result of the decision.
	 */
	@Override
	public StrategyDecisionResult canBeAppliedTo(final SUINode original, final SUINode target, final boolean allChildrenHaveId) {
		if (allChildrenHaveId) {
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

		final List<String> idsOriginal = extractChildIds(original);
		final List<String> idsTarget = extractChildIds(target);

		final ListTransformer transformer = new ListTransformer(idsOriginal, idsTarget);
		final List<BaseTransformation> transformations = transformer.calculateTransformations();
		transformations.addAll(mutatePermanentChildren(nodeHandlers, original, target, transformer.getPermanentElements()));

		AtomicInteger totalCost = new AtomicInteger(0);
		final List<AddOperation> addOperations = new ArrayList<>();
		final List<RemoveOperation> removeOperations = new ArrayList<>();
		final List<ReplaceOperation> replaceOperations = new ArrayList<>();
		final List<SwapOperation> swapOperations = new ArrayList<>();
		transformations.forEach(transformation -> {
			final BaseOperation operation = transformation.toOperation(nodeHandlers, original, target);
			totalCost.addAndGet(operation.getCost());
			switch (operation.getType()) {
				case ADD:
					addOperations.add((AddOperation) operation);
					break;
				case REMOVE:
					removeOperations.add((RemoveOperation) operation);
					break;
				case REPLACE:
					replaceOperations.add((ReplaceOperation) operation);
					break;
				case SWAP:
					swapOperations.add((SwapOperation) operation);
					break;
				default:
					throw new IllegalStateException("Unexpected operation type: " + operation.getType());
			}
		});

		final boolean useOperationsForFxNode = totalCost.get() < COST_CUTOFF;
		original.applyTransformOperations(OperationType.REMOVE, removeOperations, useOperationsForFxNode);
		original.applyTransformOperations(OperationType.ADD, addOperations, useOperationsForFxNode);
		original.applyTransformOperations(OperationType.SWAP, swapOperations, useOperationsForFxNode);
		original.applyTransformOperations(OperationType.REPLACE, replaceOperations, useOperationsForFxNode);
		if (!useOperationsForFxNode) {
			original.triggerChildListChange();
		}

		return MutationResult.MUTATED;
	}




	/**
	 * Extract the ids of all child nodes of the given parent
	 *
	 * @param parent the parent node
	 * @return the list of all ids
	 */
	private List<String> extractChildIds(final SUINode parent) {
		return parent.streamChildren()
				.map(SUINode::getIdUnsafe)
				.collect(Collectors.toList());
	}




	/**
	 * Mutates all children that are in the original and target node, i.e. are not removed or added during the transformation
	 *
	 * @param nodeHandlers the simpleui node handlers
	 * @param original     the original parent node
	 * @param target       the target parent node
	 * @param permanents   the children that are not removed or added during the transformation
	 * @return the replace transformations required if a child node is rebuild
	 */
	private List<ReplaceTransformation> mutatePermanentChildren(final MasterNodeHandlers nodeHandlers,
																final SUINode original,
																final SUINode target,
																final Set<Pair<String, Integer>> permanents) {
		return Loop.asyncCollectingLoop(new ArrayList<>(permanents), true, permanent -> {
			final String nodeId = permanent.getLeft();
			final int index = permanent.getRight();
			final SUINode childOriginal = original.findChildUnsafe(nodeId);
			final SUINode childTarget = target.getChild(index);
			final MutationResult result = nodeHandlers.getMutator().mutateNode(childOriginal, childTarget, nodeHandlers);
			if (result == MutationResult.REQUIRES_REBUILD) {
				return new ReplaceTransformation(index, nodeId);
			} else {
				return null;
			}
		});
	}


}
