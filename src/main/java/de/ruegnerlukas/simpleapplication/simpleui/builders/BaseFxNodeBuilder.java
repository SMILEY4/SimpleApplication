package de.ruegnerlukas.simpleapplication.simpleui.builders;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import javafx.scene.Node;

public interface BaseFxNodeBuilder<T extends Node> {


	T build(SceneContext context, SNode node);


}
