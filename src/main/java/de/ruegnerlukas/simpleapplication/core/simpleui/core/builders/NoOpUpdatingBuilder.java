package de.ruegnerlukas.simpleapplication.core.simpleui.core.builders;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import javafx.scene.Node;

public class NoOpUpdatingBuilder implements PropFxNodeUpdatingBuilder<SuiProperty, Node> {


	@Override
	public void build(final SuiNode node, final SuiProperty property, final Node fxNode) {
		// do nothing here
	}




	@Override
	public MutationResult update(final SuiProperty property, final SuiNode node, final Node fxNode) {
		return MutationResult.REQUIRES_REBUILD;
	}




	@Override
	public MutationResult remove(final SuiProperty property, final SuiNode node, final Node fxNode) {
		return MutationResult.REQUIRES_REBUILD;
	}

}
