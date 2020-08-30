package de.ruegnerlukas.simpleapplication.simpleui.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NoOpUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.scene.Node;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.get;

public final class SUIRaw {


	/**
	 * Hidden constructor for utility classes
	 */
	private SUIRaw() {
		// do nothing
	}




	/**
	 * Creates a new raw node
	 *
	 * @param rawProperty the property defining the behaviour of the raw javafx node
	 * @param properties  the properties
	 * @return the factory for a raw node
	 */
	public static NodeFactory raw(final RawBehaviourProperty<? extends Node> rawProperty, final Property... properties) {
		Properties.validate(SUIRaw.class, get().getEntry(SUIRaw.class).getProperties(), properties);
		final List<Property> propertyList = new ArrayList<>(List.of(properties));
		propertyList.add(rawProperty);
		return state -> new SUINode(SUIRaw.class, propertyList, state, null);
	}




	/**
	 * @param builder the function building the javafx node
	 * @return the property defining the behaviour of the raw node.
	 */
	public static <T extends Node> RawBehaviourProperty<T> rawBehaviour(final Function<SUINode, T> builder) {
		return new RawBehaviourProperty<>(builder);
	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SUIRaw.class, new SUIRaw.RawNodeBuilder());
		registry.registerProperties(SUIRaw.class, PropertyGroups.commonProperties());
		registry.registerProperties(SUIRaw.class, List.of(
				PropertyEntry.of(RawBehaviourProperty.class, new NoOpUpdatingBuilder())
		));
	}




	private static class RawNodeBuilder implements BaseFxNodeBuilder<Node> {


		@Override
		public Node build(final MasterNodeHandlers nodeHandlers, final SUINode node) {
			Optional<RawBehaviourProperty> rawProperty = node.getPropertySafe(RawBehaviourProperty.class);
			Validations.PRESENCE.isPresent(rawProperty).exception("SUIRaw nodes must have a RawBehaviourProperty-property.");
			return (Node) rawProperty.map(property -> property.getBuilder().apply(node)).orElse(null);
		}

	}






	public static class RawBehaviourProperty<T extends Node> extends Property {


		/**
		 * The function building the javafx node
		 */
		@Getter
		private final Function<SUINode, T> builder;




		/**
		 * @param builder the function building the javafx node
		 */
		public RawBehaviourProperty(final Function<SUINode, T> builder) {
			super(RawBehaviourProperty.class);
			this.builder = builder;
		}




		@Override
		protected boolean isPropertyEqual(final Property other) {
			return true; // never mutate this property
		}




		@Override
		public String printValue() {
			return "-";
		}

	}


}
