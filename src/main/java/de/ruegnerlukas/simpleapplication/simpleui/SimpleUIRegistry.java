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


	private static SimpleUIRegistry instance;




	public static void initialize() {
		instance = new SimpleUIRegistry();
	}




	public static SimpleUIRegistry get() {
		return instance;
	}




	private final Map<Class<?>, RegistryEntry> entries = new HashMap<>();




	public SimpleUIRegistry() {
		SButton.register(this);
		SAnchorPane.register(this);
		SScrollPane.register(this);
		SVBox.register(this);
		SSeparator.register(this);
	}




	public void registerBaseFxNodeBuilder(Class<?> nodeType, BaseFxNodeBuilder<? extends Node> fxNodeBuilder) {
		RegistryEntry entry = new RegistryEntry(nodeType, fxNodeBuilder);
		entries.put(entry.getNodeType(), entry);
	}




	public void registerProperty(Class<?> nodeType, Class<? extends Property> property,
								 PropFxNodeUpdatingBuilder<? extends Property, ? extends Node> propNodeUpdatingBuilder) {
		registerProperty(nodeType, property, propNodeUpdatingBuilder, propNodeUpdatingBuilder);
	}




	public void registerProperty(Class<?> nodeType, Class<? extends Property> property,
								 PropFxNodeBuilder<? extends Property, ? extends Node> propNodeBuilder) {
		final RegistryEntry entry = getEntry(nodeType);
		if (entry != null) {
			entry.getPropFxNodeBuilders().put(property, propNodeBuilder);
		}
	}




	public void registerProperty(Class<?> nodeType, Class<? extends Property> property,
								 PropFxNodeBuilder<? extends Property, ? extends Node> propNodeBuilder,
								 PropFxNodeUpdater<? extends Property, ? extends Node> propNodeUpdater) {
		final RegistryEntry entry = getEntry(nodeType);
		if (entry != null) {
			entry.getPropFxNodeBuilders().put(property, propNodeBuilder);
			entry.getPropFxNodeUpdaters().put(property, propNodeUpdater);
		}
	}




	public RegistryEntry getEntry(final Class<?> nodeType) {
		return entries.get(nodeType);
	}


}
