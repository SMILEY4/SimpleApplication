package de.ruegnerlukas.simpleapplication.simpleui.builders;

import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import javafx.scene.Node;

public class NoOpUpdatingBuilder implements PropFxNodeUpdatingBuilder<Property, Node> {


	@Override
	public void build(final MasterNodeHandlers nodeHandlers, final SuiNode node, final Property property, final Node fxNode) {
		// do nothing here
	}




	@Override
	public MutationResult update(final MasterNodeHandlers nodeHandlers, final Property property,
								 final SuiNode node, final Node fxNode) {
		return MutationResult.REQUIRES_REBUILD;
	}




	@Override
	public MutationResult remove(final MasterNodeHandlers nodeHandlers, final Property property,
								 final SuiNode node, final Node fxNode) {
		return MutationResult.REQUIRES_REBUILD;
	}

}
