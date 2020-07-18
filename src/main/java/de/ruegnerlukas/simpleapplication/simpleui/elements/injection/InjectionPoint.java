package de.ruegnerlukas.simpleapplication.simpleui.elements.injection;


import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;

import java.util.List;

public class InjectionPoint extends ItemListProperty {


	private final String injectionPointId;




	public InjectionPoint(final String injectionPointId) {
		this.injectionPointId = injectionPointId;
	}




	@Override
	public List<NodeFactory> getFactories() {
		return InjectionContext.get(injectionPointId);
	}

}
