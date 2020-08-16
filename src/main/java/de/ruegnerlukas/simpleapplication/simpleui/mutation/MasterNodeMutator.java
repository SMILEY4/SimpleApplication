package de.ruegnerlukas.simpleapplication.simpleui.mutation;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.SUISceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterFxNodeBuilder;

public class MasterNodeMutator implements BaseNodeMutator {


	/**
	 * The primary builder for fx-nodes.
	 */
	private final MasterFxNodeBuilder fxNodeBuilder;

	/**
	 * The scene context.
	 */
	private final SUISceneContext context;

	/**
	 * The actual node mutator.
	 */
	private final BaseNodeMutator mutator = new NodeMutator();




	/**
	 * @param fxNodeBuilder the primary builder for fx-nodes.
	 * @param context       the scene context.
	 */
	public MasterNodeMutator(final MasterFxNodeBuilder fxNodeBuilder, final SUISceneContext context) {
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
	public SUINode mutate(final SUINode original, final SUINode target) {
		if (mutateNode(original, target, context.getMasterNodeHandlers()) == MutationResult.REQUIRES_REBUILD) {
			System.out.println("rebuild");
			fxNodeBuilder.build(target, context.getMasterNodeHandlers());
			return target;
		} else {
			return original;
		}
	}




	@Override
	public MutationResult mutateNode(final SUINode original, final SUINode target, final MasterNodeHandlers nodeHandlers) {
		return mutator.mutateNode(original, target, nodeHandlers);
	}


}
