package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NoOpUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdater;
import de.ruegnerlukas.simpleapplication.simpleui.properties.IdProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import javafx.scene.Node;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class RegistryEntry {


	private final Class<?> nodeType;

	private final BaseFxNodeBuilder<? extends Node> baseFxNodeBuilder;

	private final Map<Class<? extends Property>, PropFxNodeBuilder<? extends Property, ? extends Node>> propFxNodeBuilders = new HashMap<>();

	private final Map<Class<? extends Property>, PropFxNodeUpdater<? extends Property, ? extends Node>> propFxNodeUpdaters = new HashMap<>();




	public RegistryEntry(final Class<?> nodeType, BaseFxNodeBuilder<? extends Node> baseFxNodeBuilder) {
		this.nodeType = nodeType;
		this.baseFxNodeBuilder = baseFxNodeBuilder;
		this.propFxNodeBuilders.put(IdProperty.class, new NoOpUpdatingBuilder());
		this.propFxNodeUpdaters.put(IdProperty.class, new NoOpUpdatingBuilder());
	}




	public Set<Class<? extends Property>> getProperties() {
		Set<Class<? extends Property>> properties = new HashSet<>(propFxNodeBuilders.keySet());
		properties.addAll(propFxNodeBuilders.keySet());
		properties.add(IdProperty.class);
		return properties;
	}

}
