package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InjectableItemListProperty extends ItemListProperty {


	/**
	 * The id of this injection point.
	 */
	private final String injectionPointId;

	/**
	 * The list of factories for default items.
	 */
	private final List<NodeFactory> defaultFactories;

	/**
	 * The marker defining at what position to inject items into
	 */
	private final InjectionIndexMarker injectionIndexMarker;




	/**
	 * @param injectionPointId the id of this injection point.
	 * @param indexMarker      the marker defining at what position to inject items into
	 * @param items            the factories for creating the items/nodes.
	 */
	public InjectableItemListProperty(final String injectionPointId,
									  final InjectionIndexMarker indexMarker,
									  final NodeFactory... items) {
		this.injectionPointId = injectionPointId;
		this.injectionIndexMarker = indexMarker;
		this.defaultFactories = List.of(items);
	}




	/**
	 * @param injectionPointId the id of this injection point.
	 * @param indexMarker      the marker defining at what position to inject items into
	 * @param items            the factories for creating the items/nodes.
	 */
	public InjectableItemListProperty(final String injectionPointId,
									  final InjectionIndexMarker indexMarker,
									  final Collection<NodeFactory> items) {
		this.injectionPointId = injectionPointId;
		this.injectionIndexMarker = indexMarker;
		this.defaultFactories = List.copyOf(items);
	}




	/**
	 * @param injectionPointId the id of this injection point.
	 * @param indexMarker      the marker defining at what position to inject items into
	 * @param items            the factories for creating the items/nodes.
	 */
	public InjectableItemListProperty(final String injectionPointId,
									  final InjectionIndexMarker indexMarker,
									  final Stream<NodeFactory> items) {
		this.injectionPointId = injectionPointId;
		this.injectionIndexMarker = indexMarker;
		this.defaultFactories = List.copyOf(items.collect(Collectors.toList()));
	}




	/**
	 * @param injectionPointId the id of this injection point.
	 * @param indexMarker      the marker defining at what position to inject items into
	 * @param factory          the factory for creating factories for creating the items/nodes.
	 */
	public InjectableItemListProperty(final String injectionPointId,
									  final InjectionIndexMarker indexMarker,
									  final Supplier<List<NodeFactory>> factory) {
		this.injectionPointId = injectionPointId;
		this.injectionIndexMarker = indexMarker;
		this.defaultFactories = factory.get();
	}




	/**
	 * @param injectionPointId the id of this injection point.
	 */
	public InjectableItemListProperty(final String injectionPointId) {
		this.injectionPointId = injectionPointId;
		this.injectionIndexMarker = InjectionIndexMarker.injectFirst();
		this.defaultFactories = List.of();
	}




	@Override
	public List<NodeFactory> getFactories() {
		List<NodeFactory> injected = SuiRegistry.get().getInjected(injectionPointId);
		List<NodeFactory> factories = new ArrayList<>(injected.size() + defaultFactories.size());
		factories.addAll(defaultFactories);
		factories.addAll(injectionIndexMarker.getIndex(factories), injected);
		return factories;
	}

}
