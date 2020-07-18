package de.ruegnerlukas.simpleapplication.simpleui.builders;

import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import javafx.scene.Node;

public class NoOpUpdatingBuilder implements PropFxNodeUpdatingBuilder<Property, Node> {


	@Override
	public void build(final SceneContext context, final SNode node, final Property property, final Node fxNode) {
	}




	@Override
	public BaseNodeMutator.MutationResult update(final SceneContext context, final Property property, final SNode node, final Node fxNode) {
		return BaseNodeMutator.MutationResult.REBUILD;
	}




	@Override
	public BaseNodeMutator.MutationResult remove(final SceneContext context, final Property property, final SNode node, final Node fxNode) {
		return BaseNodeMutator.MutationResult.REBUILD;
	}

}
