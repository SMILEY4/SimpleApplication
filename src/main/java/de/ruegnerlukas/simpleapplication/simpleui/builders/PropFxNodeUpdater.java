package de.ruegnerlukas.simpleapplication.simpleui.builders;

import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import javafx.scene.Node;

public interface PropFxNodeUpdater<P extends Property, T extends Node> {


	BaseNodeMutator.MutationResult update(SceneContext context, P property, SNode node, T fxNode);

	BaseNodeMutator.MutationResult remove(SceneContext context, P property, SNode node, T fxNode);


}
