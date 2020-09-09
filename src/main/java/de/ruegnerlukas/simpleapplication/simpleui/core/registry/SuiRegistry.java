package de.ruegnerlukas.simpleapplication.simpleui.core.registry;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiAnchorPane;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiAnchorPaneItem;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiButton;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiChoiceBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiComboBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiContainer;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiDatePicker;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiHBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiLabel;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiScrollPane;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiSeparator;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiSlider;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiTextArea;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiTextField;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiVBox;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdater;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import javafx.scene.Node;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SuiRegistry {


	/**
	 * The singleton instance of the registry.
	 */
	private static SuiRegistry instance;




	/**
	 * initializes the singleton instance of this registry with all pre-build nodes registered.
	 */
	public static void initialize() {
		instance = new SuiRegistry(false);
	}




	/**
	 * initializes the singleton instance of this registry.
	 */
	public static void initializeEmpty() {
		instance = new SuiRegistry(true);
	}




	/**
	 * Disposes of the current registry instance.
	 */
	public static void dispose() {
		instance = null;
	}




	/**
	 * @return the singleton instance of this registry after initialisation.
	 */
	public static SuiRegistry get() {
		Validations.PRESENCE.notNull(instance).exception("SuiRegistry is not initialized. Call SuiRegistry#initialize() first.");
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
	 * Default constructor. Optionally registers the pre-build nodes.
	 *
	 * @param initEmpty whether to initialize this registry with the pre-build nodes
	 */
	public SuiRegistry(final boolean initEmpty) {
		if (!initEmpty) {
			SuiSeparator.register(this);
			SuiLabel.register(this);
			SuiButton.register(this);
			SuiChoiceBox.register(this);
			SuiComboBox.register(this);
			SuiTextField.register(this);
			SuiTextArea.register(this);
			SuiDatePicker.register(this);
			SuiSlider.register(this);
			SuiContainer.register(this);
			SuiAnchorPane.register(this);
			SuiAnchorPaneItem.register(this);
			SuiScrollPane.register(this);
			SuiVBox.register(this);
			SuiHBox.register(this);
		}
	}




	/**
	 * Register a {@link AbstractFxNodeBuilder} for the given node type
	 *
	 * @param nodeType      the node type
	 * @param fxNodeBuilder the builder of a base fx-node
	 */
	public void registerBaseFxNodeBuilder(final Class<?> nodeType, final AbstractFxNodeBuilder<? extends Node> fxNodeBuilder) {
		Validations.INPUT.notNull(nodeType).exception("The node type can not be null");
		Validations.INPUT.notNull(fxNodeBuilder).exception("The fx node builder can not be null");
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
								 final Class<? extends SuiProperty> property,
								 final PropFxNodeUpdatingBuilder<? extends SuiProperty, ? extends Node> ub) {
		Validations.INPUT.notNull(nodeType).exception("The node type can not be null");
		Validations.INPUT.notNull(property).exception("The property type can not be null");
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
								 final Class<? extends SuiProperty> property,
								 final PropFxNodeBuilder<? extends SuiProperty, ? extends Node> builder) {
		Validations.INPUT.notNull(nodeType).exception("The node type can not be null");
		Validations.INPUT.notNull(property).exception("The property type can not be null");
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
								 final Class<? extends SuiProperty> property,
								 final PropFxNodeBuilder<? extends SuiProperty, ? extends Node> builder,
								 final PropFxNodeUpdater<? extends SuiProperty, ? extends Node> updater) {
		Validations.INPUT.notNull(nodeType).exception("The node type can not be null");
		Validations.INPUT.notNull(property).exception("The property type can not be null");
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
		Validations.INPUT.notNull(nodeType).exception("The node type can not be null");
		Validations.INPUT.notNull(properties).exception("The property-list can not be null");
		properties.forEach(entry -> registerProperty(nodeType, entry.getType(), entry.getBuilder(), entry.getUpdater()));
	}




	/**
	 * Find the entry for the given node type.
	 *
	 * @param nodeType the type of the node
	 * @return the entry or null
	 */
	public RegistryEntry getEntry(final Class<?> nodeType) {
		Validations.PRESENCE.containsKey(entries, nodeType).exception("The node type {} is unknown.", nodeType);
		return entries.get(nodeType);
	}




	/**
	 * Registers the given node factories at an injection point with the given id.
	 *
	 * @param injectionPointId the id of the injection point
	 * @param factories        the node factories to inject
	 */
	public void inject(final String injectionPointId, final List<NodeFactory> factories) {
		Validations.INPUT.notEmpty(injectionPointId).exception("The injection point id can not be null");
		Validations.INPUT.notNull(factories).exception("The factory-list can not be null");
		Validations.INPUT.containsNoNull(factories).exception("The factory-list can not contain null-entries");
		factories.forEach(factory -> inject(injectionPointId, factory));
	}




	/**
	 * Registers the given node factories at an injection point with the given id.
	 *
	 * @param injectionPointId the id of the injection point
	 * @param factories        the node factories to inject
	 */
	public void inject(final String injectionPointId, final NodeFactory... factories) {
		Validations.INPUT.notEmpty(injectionPointId).exception("The injection point id can not be null");
		Validations.INPUT.notNull(factories).exception("The factories can not be null");
		Validations.INPUT.containsNoNull(factories).exception("The factory-list can not contain null-entries");
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
		Validations.INPUT.notEmpty(injectionPointId).exception("The injection point id can not be null");
		Validations.INPUT.notNull(factory).exception("The factory can not be null");
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
		private final Class<? extends SuiProperty> type;

		/**
		 * The builder
		 */
		@Getter
		private final PropFxNodeBuilder<? extends SuiProperty, ? extends Node> builder;

		/**
		 * The updater
		 */
		@Getter
		private final PropFxNodeUpdater<? extends SuiProperty, ? extends Node> updater;




		/**
		 * @param type    the type of the property
		 * @param builder the builder
		 * @param updater the updater
		 * @return the property entry
		 */
		public static PropertyEntry of(
				final Class<? extends SuiProperty> type,
				final PropFxNodeBuilder<? extends SuiProperty, ? extends Node> builder,
				final PropFxNodeUpdater<? extends SuiProperty, ? extends Node> updater) {
			return new PropertyEntry(type, builder, updater);
		}




		/**
		 * @param type            the type of the property
		 * @param updatingBuilder the builder and updater
		 * @return the property entry
		 */
		public static PropertyEntry of(
				final Class<? extends SuiProperty> type,
				final PropFxNodeUpdatingBuilder<? extends SuiProperty, ? extends Node> updatingBuilder) {
			return new PropertyEntry(type, updatingBuilder, updatingBuilder);
		}

	}


}
