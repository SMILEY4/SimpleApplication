package de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies;

import de.ruegnerlukas.simpleapplication.common.utils.LoopUtils;
import de.ruegnerlukas.simpleapplication.common.utils.Pair;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiServices;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.operations.AddOperation;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.operations.BaseOperation;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.operations.OperationType;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.operations.RemoveOperation;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.operations.ReplaceOperation;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.operations.SwapOperation;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies.ListTransformer.BaseTransformation;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies.ListTransformer.ReplaceTransformation;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildListener;

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
	public StrategyDecisionResult canBeAppliedTo(final SuiBaseNode original, final SuiBaseNode target, final boolean allChildrenHaveId) {
		if (allChildrenHaveId) {
			return StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA;
		} else {
			return StrategyDecisionResult.NOT_APPLICABLE;
		}
	}




	@Override
	public MutationResult mutate(final SuiBaseNode original, final SuiBaseNode target, final StrategyDecisionResult decisionData) {

		final List<String> idsOriginal = extractChildIds(original);
		final List<String> idsTarget = extractChildIds(target);

		final ListTransformer transformer = new ListTransformer(idsOriginal, idsTarget);
		final List<BaseTransformation> transformations = transformer.calculateTransformations();
		transformations.addAll(mutatePermanentChildren(original, target, transformer.getPermanentElements()));

		AtomicInteger totalCost = new AtomicInteger(0);
		final List<AddOperation> addOperations = new ArrayList<>();
		final List<RemoveOperation> removeOperations = new ArrayList<>();
		final List<ReplaceOperation> replaceOperations = new ArrayList<>();
		final List<SwapOperation> swapOperations = new ArrayList<>();
		transformations.forEach(transformation -> {
			final BaseOperation operation = transformation.toOperation(original, target);
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

		final boolean triggerListenerManually = totalCost.get() < COST_CUTOFF;
		original.getChildNodeStore().applyTransformOperations(OperationType.REMOVE, removeOperations, triggerListenerManually);
		original.getChildNodeStore().applyTransformOperations(OperationType.ADD, addOperations, triggerListenerManually);
		original.getChildNodeStore().applyTransformOperations(OperationType.SWAP, swapOperations, triggerListenerManually);
		original.getChildNodeStore().applyTransformOperations(OperationType.REPLACE, replaceOperations, triggerListenerManually);
		if (triggerListenerManually) {
			SuiNodeChildListener listener = original.getChildNodeStore().getChildListener();
			if (listener != null) {
				listener.onChange(original);
			}
		}

		return MutationResult.MUTATED;
	}




	/**
	 * Extract the ids of all child nodes of the given parent
	 *
	 * @param parent the parent node
	 * @return the list of all ids
	 */
	private List<String> extractChildIds(final SuiBaseNode parent) {
		return parent.getChildNodeStore().stream()
				.map(node -> node.getPropertyStore().getIdUnsafe())
				.collect(Collectors.toList());
	}




	/**
	 * Mutates all children that are in the original and target node, i.e. are not removed or added during the transformation
	 *
	 * @param original   the original parent node
	 * @param target     the target parent node
	 * @param permanents the children that are not removed or added during the transformation
	 * @return the replace transformations required if a child node is rebuild
	 */
	private List<ReplaceTransformation> mutatePermanentChildren(final SuiBaseNode original,
																final SuiBaseNode target,
																final Set<Pair<String, Integer>> permanents) {
		return LoopUtils.asyncCollectingLoop(new ArrayList<>(permanents), true, permanent -> {
			final String nodeId = permanent.getLeft();
			final int index = permanent.getRight();
			final SuiBaseNode childOriginal = original.getChildNodeStore().find(nodeId);
			final SuiBaseNode childTarget = target.getChildNodeStore().get(index);
			if (SuiServices.get().mutate(childOriginal, childTarget) == MutationResult.REQUIRES_REBUILD) {
				return new ReplaceTransformation(index, nodeId);
			} else {
				return null;
			}
		});
	}


}
