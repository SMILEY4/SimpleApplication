package de.ruegnerlukas.simpleapplication.simpleui.builders;

import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import javafx.scene.Node;

public interface PropFxNodeBuilder<P extends Property, T extends Node> {


	void build(SceneContext context, SNode node, P property, T fxNode);


}
