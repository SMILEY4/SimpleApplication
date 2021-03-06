package de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;

import java.util.List;

public interface FactoryExtension extends NodeFactory {


	/**
	 * @return the list of properties of this created this far.
	 */
	List<SuiProperty> getBuilderProperties();

}
