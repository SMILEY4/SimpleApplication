package de.ruegnerlukas.simpleapplication.simpleui.registry;


import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdater;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIAnchorPane;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIButton;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIChoiceBox;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIHBox;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUILabel;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIRaw;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIScrollPane;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUISeparator;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIVBox;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import javafx.scene.Node;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
		SUIHBox.register(this);
		SUISeparator.register(this);
		SUILabel.register(this);
		SUIChoiceBox.register(this);
		SUIRaw.register(this);
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
	 * @param ub       the {@link PropFxNodeUpdatingBuilder}
	 */
	public void registerProperty(final Class<?> nodeType,
								 final Class<? extends Property> property,
								 final PropFxNodeUpdatingBuilder<? extends Property, ? extends Node> ub) {
		registerProperty(nodeType, property, ub, ub);
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
		if (entry != null && builder != null) {
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
			if (builder != null) {
				entry.getPropFxNodeBuilders().put(property, builder);
			}
			if (updater != null) {
				entry.getPropFxNodeUpdaters().put(property, updater);
			}
		}
	}




	/**
	 * Registers the given list of properties
	 *
	 * @param nodeType   the type of the node
	 * @param properties the list of property data
	 */
	public void registerProperties(final Class<?> nodeType, final List<PropertyEntry> properties) {
		properties.forEach(entry -> registerProperty(nodeType, entry.getType(), entry.getBuilder(), entry.getUpdater()));
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
	 * Registers the given node factories at an injection point with the given id.
	 *
	 * @param injectionPointId the id of the injection point
	 * @param factories        the node factories to inject
	 */
	public void inject(final String injectionPointId, final List<NodeFactory> factories) {
		factories.forEach(factory -> inject(injectionPointId, factory));
	}




	/**
	 * Registers the given node factories at an injection point with the given id.
	 *
	 * @param injectionPointId the id of the injection point
	 * @param factories        the node factories to inject
	 */
	public void inject(final String injectionPointId, final NodeFactory... factories) {
		for (NodeFactory factory : factories) {
			inject(injectionPointId, factory);
		}
	}




	/**
	 * Register the given node factory at an injection point with the given id.
	 *
	 * @param injectionPointId the id of the injection point
	 * @param factory          the node factory to inject
	 */
	public void inject(final String injectionPointId, final NodeFactory factory) {
		injectedFactories
				.computeIfAbsent(injectionPointId, k -> new ArrayList<>())
				.add(factory);
	}




	/**
	 * @param injectionPointId the id of the injection point
	 * @return the factories registered to be injection into the point with the given id
	 */
	public List<NodeFactory> getInjected(final String injectionPointId) {
		return injectedFactories.getOrDefault(injectionPointId, List.of());
	}




	@Getter
	@AllArgsConstructor
	public static class PropertyEntry {


		/**
		 * the type of the property
		 */
		@Getter
		private final Class<? extends Property> type;

		/**
		 * The builder
		 */
		@Getter
		private final PropFxNodeBuilder<? extends Property, ? extends Node> builder;

		/**
		 * The updater
		 */
		@Getter
		private final PropFxNodeUpdater<? extends Property, ? extends Node> updater;




		/**
		 * @param type    the type of the property
		 * @param builder the builder
		 * @param updater the updater
		 * @return the property entry
		 */
		public static PropertyEntry of(
				final Class<? extends Property> type,
				final PropFxNodeBuilder<? extends Property, ? extends Node> builder,
				final PropFxNodeUpdater<? extends Property, ? extends Node> updater) {
			return new PropertyEntry(type, builder, updater);
		}




		/**
		 * @param type            the type of the property
		 * @param updatingBuilder the builder and updater
		 * @return the property entry
		 */
		public static PropertyEntry of(
				final Class<? extends Property> type,
				final PropFxNodeUpdatingBuilder<? extends Property, ? extends Node> updatingBuilder) {
			return new PropertyEntry(type, updatingBuilder, updatingBuilder);
		}

	}


}
