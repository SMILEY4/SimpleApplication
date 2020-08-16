package de.ruegnerlukas.simpleapplication.simpleui.mutation;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.AddAllStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.ChildNodesMutationStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.IdMutationStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.RemoveAllStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.StandardMutationStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.properties.IdProperty;

public class MutationStrategyDecider implements ChildNodesMutationStrategy {


	private final ChildNodesMutationStrategy standardStrategy = new StandardMutationStrategy();

		private final ChildNodesMutationStrategy idStrategy = new IdMutationStrategy();
//	private final ChildNodesMutationStrategy idStrategy = new AsyncIdMutationStrategy();

	private final ChildNodesMutationStrategy removeAllStrategy = new RemoveAllStrategy();

	private final ChildNodesMutationStrategy addAllStrategy = new AddAllStrategy();




	@Override
	public BaseNodeMutator.MutationResult mutate(final MasterNodeHandlers nodeHandlers, final SUINode original, final SUINode target) {

		if (!original.hasChildren() && !target.hasChildren()) {
			return BaseNodeMutator.MutationResult.MUTATED;
		}

		if (original.hasChildren() && !target.hasChildren()) {
			return removeAllStrategy.mutate(nodeHandlers, original, target);
		}

		if (!original.hasChildren() && target.hasChildren()) {
			return addAllStrategy.mutate(nodeHandlers, original, target);
		}

		if (allChildrenHaveId(original) && allChildrenHaveId(target)) {
			return idStrategy.mutate(nodeHandlers, original, target);
		}

		return standardStrategy.mutate(nodeHandlers, original, target);
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
