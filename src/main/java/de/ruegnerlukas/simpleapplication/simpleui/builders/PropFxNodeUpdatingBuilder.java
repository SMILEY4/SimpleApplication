package de.ruegnerlukas.simpleapplication.simpleui.builders;

import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import javafx.scene.Node;

public interface PropFxNodeUpdatingBuilder<P extends Property, T extends Node> extends PropFxNodeBuilder<P, T>, PropFxNodeUpdater<P, T> {


}
