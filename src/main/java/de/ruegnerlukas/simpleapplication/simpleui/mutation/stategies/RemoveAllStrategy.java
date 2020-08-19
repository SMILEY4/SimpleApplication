package de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;

import java.util.List;

public class RemoveAllStrategy implements ChildNodesMutationStrategy {



	@Override
	public DecisionData canBeAppliedTo(final SUINode original, final SUINode target, final boolean allChildrenHaveId) {
		if (original.hasChildren() && !target.hasChildren()) {
			return DecisionData.APPLIABLE_NO_DATA;
		} else {
			return DecisionData.NOT_APPLIABLE;
		}
	}





	@Override
	public BaseNodeMutator.MutationResult mutate(final MasterNodeHandlers nodeHandlers, final SUINode original, final SUINode target, final DecisionData decisionData) {
		original.setChildren(List.of(), true);
		return BaseNodeMutator.MutationResult.MUTATED;
	}

}
