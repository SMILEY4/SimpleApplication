package de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import de.ruegnerlukas.simpleapplication.simpleui.properties.IdProperty;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator.MutationResult.MUTATED;

public class IdMutationStategy implements ChildNodesMutationStrategy {


	@Override
	public BaseNodeMutator.MutationResult mutate(final MasterNodeHandlers nodeHandlers, final SUINode original, final SUINode target) {
		final List<SUINode> newChildList = new ArrayList<>();
		for (int i = 0; i < target.childCount(); i++) {
			final SUINode childTarget = target.getChild(i);
			final String targetId = childTarget.getProperty(IdProperty.class).getId();
			final SUINode resultingNode = original.findChild(targetId)
					.map(childOriginal -> nodeHandlers.getMutator().mutate(childOriginal, childTarget))
					.orElseGet(() -> {
						nodeHandlers.getFxNodeBuilder().build(childTarget);
						return childTarget;
					});
			newChildList.add(resultingNode);
		}
		original.setChildren(newChildList, true);
		return MUTATED;
	}

}
