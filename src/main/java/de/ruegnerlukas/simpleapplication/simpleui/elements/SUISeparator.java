package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.DisabledProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.OrientationProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeMinProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizePreferredProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.StyleProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;

import java.util.ArrayList;
import java.util.List;

public final class SUISeparator {


	/**
	 * Hidden constructor for utility classes
	 */
	private SUISeparator() {
		// do nothing
	}




	/**
	 * Creates a new separator node,
	 *
	 * @param orientation the orientation of the separator
	 * @param properties  the properties
	 * @return the factory for a separator node
	 */
	public static NodeFactory separator(final Orientation orientation, final Property... properties) {
		final List<Property> propList = new ArrayList<>(List.of(properties));
		propList.add(new OrientationProperty(orientation));
		Properties.checkIllegal(SUISeparator.class, SUIRegistry.get().getEntry(SUISeparator.class).getProperties(), propList);
		return state -> new SUINode(SUISeparator.class, propList, state, null);
	}




	/**
	 * Creates a new separator node,
	 *
	 * @param properties the properties
	 * @return the factory for a separator node
	 */
	public static NodeFactory separator(final Property... properties) {
		Properties.checkIllegal(SUISeparator.class, SUIRegistry.get().getEntry(SUISeparator.class).getProperties(), properties);
		return state -> new SUINode(SUISeparator.class, List.of(properties), state, null);
	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SUISeparator.class, new SUISeparator.SeperatorNodeBuilder());
		registry.registerProperty(SUISeparator.class, SizeMinProperty.class, new SizeMinProperty.SizeMinUpdatingBuilder());
		registry.registerProperty(SUISeparator.class, SizePreferredProperty.class,
				new SizePreferredProperty.SizePreferredUpdatingBuilder());
		registry.registerProperty(SUISeparator.class, SizeMaxProperty.class, new SizeMaxProperty.SizeMaxUpdatingBuilder());
		registry.registerProperty(SUISeparator.class, SizeProperty.class, new SizeProperty.SizeUpdatingBuilder());
		registry.registerProperty(SUISeparator.class, DisabledProperty.class, new DisabledProperty.DisabledUpdatingBuilder());
		registry.registerProperty(SUISeparator.class, OrientationProperty.class,
				new OrientationProperty.SeparatorOrientationUpdatingBuilder());
		registry.registerProperty(SUISeparator.class, StyleProperty.class, new StyleProperty.StyleUpdatingBuilder());
	}




	private static class SeperatorNodeBuilder implements BaseFxNodeBuilder<Separator> {


		@Override
		public Separator build(final MasterNodeHandlers nodeHandlers, final SUINode node) {
			return new Separator();
		}

	}

}
