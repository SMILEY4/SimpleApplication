package de.ruegnerlukas.simpleapplication.core.simpleui.core.builders;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import javafx.scene.Node;

public interface PropFxNodeUpdatingBuilder<P extends SuiProperty, T extends Node> extends PropFxNodeBuilder<P, T>, PropFxNodeUpdater<P, T> {


}
