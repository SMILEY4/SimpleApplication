package de.ruegnerlukas.simpleapplication.simpleui.core.builders;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import javafx.scene.Node;

public class NoOpUpdatingBuilder implements PropFxNodeUpdatingBuilder<Property, Node> {


	@Override
	public void build(final SuiBaseNode node, final Property property, final Node fxNode) {
		// do nothing here
	}




	@Override
	public MutationResult update(final Property property, final SuiBaseNode node, final Node fxNode) {
		return MutationResult.REQUIRES_REBUILD;
	}




	@Override
	public MutationResult remove(final Property property, final SuiBaseNode node, final Node fxNode) {
		return MutationResult.REQUIRES_REBUILD;
	}

}
