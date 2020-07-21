package de.ruegnerlukas.simpleapplication.simpleui.registry;


import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdater;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIAnchorPane;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIButton;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIScrollPane;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUISeparator;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIVBox;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import javafx.scene.Node;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SUIRegistry {


	/**
	 * The singleton instance of the registry.
	 */
	private static SUIRegistry instance;




	/**
	 * initializes the singleton instance of this registry.
	 */
	public static void initialize() {
		instance = new SUIRegistry();
	}




	/**
	 * @return the singleton instance of this registry after initialisation.
	 */
	public static SUIRegistry get() {
		if (instance == null) {
			log.warn("SUIRegistry not initialized. Returning null.");
		}
		return instance;
	}




	/**
	 * The map of all entries. The key is the type of a node.
	 */
	private final Map<Class<?>, RegistryEntry> entries = new HashMap<>();


	/**
	 * The list of registered factories ready to be injected at defined points.
	 */
	private final Map<String, List<NodeFactory>> injectedFactories = new HashMap<>();




	/**
	 * Default constructor. Registers the pre-build nodes.
	 */
	public SUIRegistry() {
		SUIButton.register(this);
		SUIAnchorPane.register(this);
		SUIScrollPane.register(this);
		SUIVBox.register(this);
		SUISeparator.register(this);
	}




	/**
	 * Register a {@link BaseFxNodeBuilder} for the given node type
	 *
	 * @param nodeType      the node type
	 * @param fxNodeBuilder the builder of a base fx-node
	 */
	public void registerBaseFxNodeBuilder(final Class<?> nodeType, final BaseFxNodeBuilder<? extends Node> fxNodeBuilder) {
		RegistryEntry entry = new RegistryEntry(nodeType, fxNodeBuilder);
		entries.put(entry.getNodeType(), entry);
	}




	/**
	 * Register a property for the given node type with a {@link PropFxNodeUpdatingBuilder}.
	 *
	 * @param nodeType the type of the node
	 * @param property the type of the property
	 * @param up       the {@link PropFxNodeUpdatingBuilder}
	 */
	public void registerProperty(final Class<?> nodeType,
								 final Class<? extends Property> property,
								 final PropFxNodeUpdatingBuilder<? extends Property, ? extends Node> up) {
		registerProperty(nodeType, property, up, up);
	}




	/**
	 * Register a property for the given node type with a {@link PropFxNodeBuilder}.
	 *
	 * @param nodeType the type of the node
	 * @param property the type of the property
	 * @param builder  the {@link PropFxNodeBuilder}
	 */
	public void registerProperty(final Class<?> nodeType,
								 final Class<? extends Property> property,
								 final PropFxNodeBuilder<? extends Property, ? extends Node> builder) {
		final RegistryEntry entry = getEntry(nodeType);
		if (entry != null) {
			entry.getPropFxNodeBuilders().put(property, builder);
		}
	}




	/**
	 * Register a property for the given node type with a {@link PropFxNodeBuilder} and {@link PropFxNodeUpdater}.
	 *
	 * @param nodeType the type of the node
	 * @param property the type of the property
	 * @param builder  the {@link PropFxNodeBuilder}
	 * @param updater  the {@link PropFxNodeUpdater}
	 */
	public void registerProperty(final Class<?> nodeType,
								 final Class<? extends Property> property,
								 final PropFxNodeBuilder<? extends Property, ? extends Node> builder,
								 final PropFxNodeUpdater<? extends Property, ? extends Node> updater) {
		final RegistryEntry entry = getEntry(nodeType);
		if (entry != null) {
			entry.getPropFxNodeBuilders().put(property, builder);
			entry.getPropFxNodeUpdaters().put(property, updater);
		}
	}




	/**
	 * Find the entry for the given node type.
	 *
	 * @param nodeType the type of the node
	 * @return the entry or null
	 */
	public RegistryEntry getEntry(final Class<?> nodeType) {
		return entries.get(nodeType);
	}




	/**
	 * Register the given node factory at an injection point with the given id.
	 *
	 * @param injectionPointId the id of the injection point
	 * @param factory          the node factory to inject
	 */
	public void inject(final String injectionPointId, final NodeFactory factory) {
		List<NodeFactory> factories = injectedFactories.computeIfAbsent(injectionPointId, k -> new ArrayList<>());
		factories.add(factory);
	}




	/**
	 * @param injectionPointId the id of the injection point
	 * @return the factories registered to be injection into the point with the given id
	 */
	public List<NodeFactory> get(final String injectionPointId) {
		return injectedFactories.getOrDefault(injectionPointId, List.of());
	}


}
