package de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;

import java.util.ArrayList;
import java.util.List;

public abstract class BuilderExtensionContainer implements FactoryExtension {


	/**
	 * The properties of this builder.
	 */
	private final List<SuiProperty> properties = new ArrayList<>();




	@Override
	public List<SuiProperty> getFactoryInternalProperties() {
		return this.properties;
	}


}
