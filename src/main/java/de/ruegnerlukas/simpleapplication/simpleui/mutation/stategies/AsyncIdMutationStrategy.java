package de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies;

import de.ruegnerlukas.simpleapplication.common.AsyncChunkProcessor;
import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import de.ruegnerlukas.simpleapplication.simpleui.properties.IdProperty;

import java.util.List;
import java.util.stream.Collectors;

import static de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator.MutationResult.MUTATED;

public class AsyncIdMutationStrategy implements ChildNodesMutationStrategy {


	private static final int N_CORES = Runtime.getRuntime().availableProcessors();




	@Override
	public BaseNodeMutator.MutationResult mutate(final MasterNodeHandlers nodeHandlers, final SUINode original, final SUINode target) {

		// todo: maybe remove async again
		AsyncChunkProcessor<SUINode, SUINode> processor = new AsyncChunkProcessor<>(target.getChildrenUnmodifiable(), target.childCount() / N_CORES * 2,
				childTargetChunk -> childTargetChunk.stream()
						.map(childTarget -> {
							final String targetId = childTarget.getProperty(IdProperty.class).getId();
							return original.findChild(targetId)
									.map(childOriginal -> nodeHandlers.getMutator().mutate(childOriginal, childTarget))
									.orElse(childTarget);
						}).collect(Collectors.toList()));
		List<SUINode> newChildList = processor.get();

		newChildList.forEach(child -> {
			if (child.getFxNode() == null) {
				nodeHandlers.getFxNodeBuilder().build(child);
			}
		});

		original.setChildren(newChildList, true); // todo bottleneck for smaller number of modifications

		return MUTATED;
	}

}
