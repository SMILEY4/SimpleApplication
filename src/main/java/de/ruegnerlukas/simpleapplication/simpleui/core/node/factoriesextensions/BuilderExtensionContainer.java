package de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;

import java.util.ArrayList;
import java.util.List;

public abstract class BuilderExtensionContainer implements FactoryExtension {


	/**
	 * The internal properties of this factory extension.
	 */
	private final List<SuiProperty> properties = new ArrayList<>();




	@Override
	public List<SuiProperty> getFactoryInternalProperties() {
		return this.properties;
	}




	/**
	 * @return the internal properties as an array
	 */
	public SuiProperty[] getFactoryInternalPropertiesAsArray() {
		final SuiProperty[] array = new SuiProperty[properties.size()];
		properties.toArray(array);
		return array;
	}

}
