package de.ruegnerlukas.simpleapplication.simpleui.builders;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.State;

public interface NodeFactory {


	SNode create(State state);


}
