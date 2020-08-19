package de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator.MutationResult.MUTATED;

public class AddAllStrategy implements ChildNodesMutationStrategy {


	@Override
	public DecisionData canBeAppliedTo(final SUINode original, final SUINode target, final boolean allChildrenHaveId) {
		if (!original.hasChildren() && target.hasChildren()) {
			return DecisionData.APPLIABLE_NO_DATA;
		} else {
			return DecisionData.NOT_APPLIABLE;
		}
	}




	@Override
	public BaseNodeMutator.MutationResult mutate(final MasterNodeHandlers nodeHandlers, final SUINode original, final SUINode target, final DecisionData decisionData) {
		final List<SUINode> newChildList = new ArrayList<>();
		for (int i = 0; i < target.childCount(); i++) {
			final SUINode childTarget = target.getChild(i);
			nodeHandlers.getFxNodeBuilder().build(childTarget);
			newChildList.add(childTarget);
		}
		original.setChildren(newChildList, true);
		return MUTATED;
	}

}
