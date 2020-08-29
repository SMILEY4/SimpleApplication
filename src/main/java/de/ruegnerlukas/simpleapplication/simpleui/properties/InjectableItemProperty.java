package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;

import java.util.List;

public class InjectableItemProperty extends ItemProperty {


	/**
	 * The id of this injection point.
	 */
	private final String injectionPointId;

	/**
	 * The factory for a default item.
	 */
	private final NodeFactory defaultFactory;




	/**
	 * @param injectionPointId the id of this injection point.
	 * @param item             the factory for creating the default item.
	 */
	public InjectableItemProperty(final String injectionPointId,
								  final NodeFactory item) {
		this.injectionPointId = injectionPointId;
		this.defaultFactory = item;
	}




	/**
	 * @param injectionPointId the id of this injection point.
	 */
	public InjectableItemProperty(final String injectionPointId) {
		this.injectionPointId = injectionPointId;
		this.defaultFactory = null;
	}




	@Override
	public NodeFactory getFactory() {
		List<NodeFactory> injected = SUIRegistry.get().getInjected(injectionPointId);
		if (!injected.isEmpty()) {
			return injected.get(injected.size() - 1);
		} else {
			return defaultFactory;
		}
	}

}
