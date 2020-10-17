package de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;

import java.util.List;

public interface FactoryExtension extends NodeFactory {


	/**
	 * @return the list of properties of this created this far.
	 */
	List<SuiProperty> getFactoryInternalProperties();

}
