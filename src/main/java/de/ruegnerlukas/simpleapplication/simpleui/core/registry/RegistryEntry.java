package de.ruegnerlukas.simpleapplication.simpleui.core.registry;

import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NoOpUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdater;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.IdProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Property;
import javafx.scene.Node;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class RegistryEntry {


	/**
	 * The type of the node of this entry.
	 */
	private final Class<?> nodeType;

	/**
	 * The primary builder (of the fx-node) for the node type of this entry.
	 */
	private final AbstractFxNodeBuilder<? extends Node> baseFxNodeBuilder;

	/**
	 * The node builder for the node type and a property type of this entry.
	 */
	private final Map<Class<? extends Property>, PropFxNodeBuilder<? extends Property, ? extends Node>> propFxNodeBuilders
			= new HashMap<>();

	/**
	 * The node updater for the node type and a property type of this entry.
	 */
	private final Map<Class<? extends Property>, PropFxNodeUpdater<? extends Property, ? extends Node>> propFxNodeUpdaters
			= new HashMap<>();




	/**
	 * @param nodeType          the type of the node
	 * @param baseFxNodeBuilder the primary builder (of the fx-node) for the node type of this entry.
	 */
	public RegistryEntry(final Class<?> nodeType, final AbstractFxNodeBuilder<? extends Node> baseFxNodeBuilder) {
		this.nodeType = nodeType;
		this.baseFxNodeBuilder = baseFxNodeBuilder;
		this.propFxNodeBuilders.put(IdProperty.class, new NoOpUpdatingBuilder());
		this.propFxNodeUpdaters.put(IdProperty.class, new NoOpUpdatingBuilder());
	}




	/**
	 * @return a set of all property types that have at least a builder registered at this entry.
	 */
	public Set<Class<? extends Property>> getProperties() {
		Set<Class<? extends Property>> properties = new HashSet<>(propFxNodeBuilders.keySet());
		properties.add(IdProperty.class);
		return properties;
	}

}
