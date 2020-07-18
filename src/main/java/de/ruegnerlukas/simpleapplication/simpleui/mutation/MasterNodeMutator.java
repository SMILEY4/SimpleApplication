package de.ruegnerlukas.simpleapplication.simpleui.mutation;


import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;

public class MasterNodeMutator implements BaseNodeMutator {


	/**
	 * The primary builder for fx-nodes.
	 */
	private final MasterFxNodeBuilder fxNodeBuilder;

	/**
	 * The scene context.
	 */
	private final SceneContext context;

	/**
	 * The actual node mutator.
	 */
	private final BaseNodeMutator mutator = new NodeMutator();




	/**
	 * @param fxNodeBuilder the primary builder for fx-nodes.
	 * @param context       the scene context.
	 */
	public MasterNodeMutator(final MasterFxNodeBuilder fxNodeBuilder, final SceneContext context) {
		this.fxNodeBuilder = fxNodeBuilder;
		this.context = context;
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
	public SNode mutate(final SNode original, final SNode target) {
		if (mutateNode(original, target, context) == MutationResult.REBUILD) {
			fxNodeBuilder.build(target, context);
			return target;
		} else {
			return original;
		}
	}




	@Override
	public MutationResult mutateNode(final SNode original, final SNode target, final SceneContext context) {
		return mutator.mutateNode(original, target, context);
	}


}
