package de.ruegnerlukas.simpleapplication.simpleui;


import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdater;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SAnchorPane;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SButton;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SScrollPane;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SSeparator;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SVBox;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import javafx.scene.Node;

import java.util.HashMap;
import java.util.Map;

public class SimpleUIRegistry {


	/**
	 * The singleton instance of the registry.
	 */
	private static SimpleUIRegistry instance;




	/**
	 * initializes the singleton instance of this registry.
	 */
	public static void initialize() {
		instance = new SimpleUIRegistry();
	}




	/**
	 * @return the singleton instance of this registry.
	 */
	public static SimpleUIRegistry get() {
		return instance;
	}




	/**
	 * The map of all entries. The key is the type of a node.
	 */
	private final Map<Class<?>, RegistryEntry> entries = new HashMap<>();




	/**
	 * Default constructor. Registers the pre-build nodes.
	 */
	public SimpleUIRegistry() {
		SButton.register(this);
		SAnchorPane.register(this);
		SScrollPane.register(this);
		SVBox.register(this);
		SSeparator.register(this);
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


}
