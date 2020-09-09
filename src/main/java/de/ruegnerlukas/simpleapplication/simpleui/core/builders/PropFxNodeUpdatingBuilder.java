package de.ruegnerlukas.simpleapplication.simpleui.core.builders;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.Node;

public interface PropFxNodeUpdatingBuilder<P extends SuiProperty, T extends Node> extends PropFxNodeBuilder<P, T>, PropFxNodeUpdater<P, T> {


}
