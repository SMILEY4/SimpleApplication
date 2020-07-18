package de.ruegnerlukas.simpleapplication.simpleui.elements.injection;


import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class InjectionContext {


	/**
	 * Hidden constructor for utility classes.
	 */
	private InjectionContext() {
		// do nothing
	}




	/**
	 * The list of registered factories ready to be injected at defined points.
	 */
	private static final Map<String, List<NodeFactory>> INJECTED_FACTORIES = new HashMap<>();




	/**
	 * Register the given node factory at an injection point with the given id.
	 *
	 * @param injectionPointId the id of the injection point
	 * @param factory          the node factory to inject
	 */
	public static void inject(final String injectionPointId, final NodeFactory factory) {
		List<NodeFactory> factories = INJECTED_FACTORIES.computeIfAbsent(injectionPointId, k -> new ArrayList<>());
		factories.add(factory);
	}




	/**
	 * @param injectionPointId the id of the injection point
	 * @return the factories registered to be injection into the point with the given id
	 */
	public static List<NodeFactory> get(final String injectionPointId) {
		return INJECTED_FACTORIES.getOrDefault(injectionPointId, List.of());
	}

}
