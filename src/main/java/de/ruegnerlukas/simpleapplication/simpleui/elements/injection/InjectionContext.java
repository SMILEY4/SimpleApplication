package de.ruegnerlukas.simpleapplication.simpleui.elements.injection;


import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InjectionContext {


	private static final Map<String, List<NodeFactory>> injectedFactories = new HashMap<>();




	public static void inject(final String injectionPointId, final NodeFactory factory) {
		List<NodeFactory> factories = injectedFactories.computeIfAbsent(injectionPointId, k -> new ArrayList<>());
		factories.add(factory);
	}




	public static List<NodeFactory> get(final String injectionPointId) {
		return injectedFactories.getOrDefault(injectionPointId, List.of());
	}

}
