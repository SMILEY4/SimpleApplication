package de.ruegnerlukas.simpleapplication.simpleui.builders;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import javafx.scene.Node;

public class NoOpUpdatingBuilder implements PropFxNodeUpdatingBuilder<Property, Node> {


	@Override
	public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final Property property, final Node fxNode) {
	}




	@Override
	public MutationResult update(final MasterNodeHandlers nodeHandlers, final Property property,
								 final SUINode node, final Node fxNode) {
		return MutationResult.REQUIRES_REBUILD;
	}




	@Override
	public MutationResult remove(final MasterNodeHandlers nodeHandlers, final Property property,
								 final SUINode node, final Node fxNode) {
		return MutationResult.REQUIRES_REBUILD;
	}

}
