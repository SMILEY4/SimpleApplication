package de.ruegnerlukas.simpleapplication.simpleui.mutation;


import de.ruegnerlukas.simpleapplication.simpleui.MasterFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;

public class MasterNodeMutator implements BaseNodeMutator {


	private final MasterFxNodeBuilder fxNodeBuilder;

	private final SceneContext context;

	private final BaseNodeMutator mutator = new NodeMutator();




	public MasterNodeMutator(MasterFxNodeBuilder fxNodeBuilder, SceneContext context) {
		this.fxNodeBuilder = fxNodeBuilder;
		this.context = context;
	}




	public SNode mutate(SNode original, SNode target) {
		if (mutateNode(original, target, context) == MutationResult.REBUILD) {
			fxNodeBuilder.build(target, context);
			return target;
		} else {
			return original;
		}
	}




	@Override
	public MutationResult mutateNode(SNode original, SNode target, SceneContext context) {
		return mutator.mutateNode(original, target, context);
	}


}
