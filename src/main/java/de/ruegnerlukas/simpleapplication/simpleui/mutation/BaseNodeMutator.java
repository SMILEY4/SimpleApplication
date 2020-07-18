package de.ruegnerlukas.simpleapplication.simpleui.mutation;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;

public interface BaseNodeMutator {


	enum MutationResult {
		REBUILD,
		MUTATED
	}


	MutationResult mutateNode(SNode original, SNode target, final SceneContext context);


}
