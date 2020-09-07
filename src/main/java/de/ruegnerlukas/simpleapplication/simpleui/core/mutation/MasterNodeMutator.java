package de.ruegnerlukas.simpleapplication.simpleui.core.mutation;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.MasterFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies.ChildNodesMutationStrategy;

import java.util.List;

public class MasterNodeMutator implements BaseNodeMutator {


	/**
	 * The primary builder for fx-nodes.
	 */
	private final MasterFxNodeBuilder fxNodeBuilder;

	/**
	 * The scene context.
	 */
	private final SuiSceneController context;

	/**
	 * The actual node mutator.
	 */
	private final BaseNodeMutator mutator;




	/**
	 * @param fxNodeBuilder      the primary builder for fx-nodes.
	 * @param context            the scene context.
	 * @param mutationStrategies the strategies for mutating child nodes
	 */
	public MasterNodeMutator(final MasterFxNodeBuilder fxNodeBuilder,
							 final SuiSceneController context,
							 final List<ChildNodesMutationStrategy> mutationStrategies) {
		this.fxNodeBuilder = fxNodeBuilder;
		this.context = context;
		this.mutator = new NodeMutator(mutationStrategies);
	}




	/**
	 * Mutate the given node (and all children).
	 * The properties and fx-node of the original node will be changed to match the given target node.
	 * The returned node is either the mutated original node or a newly rebuild node.
	 *
	 * @param original the original node
	 * @param target   the target node to match
	 * @return the mutated or newly created node.
	 */
	public SuiNode mutate(final SuiNode original, final SuiNode target) {
		if (mutateNode(original, target, context.getMasterNodeHandlers()) == MutationResult.REQUIRES_REBUILD) {
			fxNodeBuilder.build(target, context.getMasterNodeHandlers());
			return target;
		} else {
			return original;
		}
	}




	@Override
	public MutationResult mutateNode(final SuiNode original, final SuiNode target, final MasterNodeHandlers nodeHandlers) {
		return mutator.mutateNode(original, target, nodeHandlers);
	}


}