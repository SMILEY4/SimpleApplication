package de.ruegnerlukas.simpleapplication.simpleui.elements.injection;


import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;

import java.util.List;

public class InjectionPoint extends ItemListProperty {


	/**
	 * The id of this injection point.
	 */
	private final String injectionPointId;




	/**
	 * @param injectionPointId the id of this injection point.
	 */
	public InjectionPoint(final String injectionPointId) {
		this.injectionPointId = injectionPointId;
	}




	@Override
	public List<NodeFactory> getFactories() {
		return InjectionContext.get(injectionPointId);
	}

}
