package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.OrientationProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.get;

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
		Properties.checkIllegal(SUISeparator.class, get().getEntry(SUISeparator.class).getProperties(), propList);
		return state -> new SUINode(SUISeparator.class, propList, state, null);
	}




	/**
	 * Creates a new separator node,
	 *
	 * @param properties the properties
	 * @return the factory for a separator node
	 */
	public static NodeFactory separator(final Property... properties) {
		Properties.validate(SUISeparator.class, get().getEntry(SUISeparator.class).getProperties(), properties);
		return state -> new SUINode(SUISeparator.class, List.of(properties), state, null);
	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SUISeparator.class, new SUISeparator.SeperatorNodeBuilder());
		registry.registerProperties(SUISeparator.class, PropertyGroups.commonProperties());
		registry.registerProperties(SUISeparator.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SUISeparator.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SUISeparator.class, List.of(
				PropertyEntry.of(OrientationProperty.class, new OrientationProperty.SeparatorOrientationUpdatingBuilder())
		));
	}




	private static class SeperatorNodeBuilder implements BaseFxNodeBuilder<Separator> {


		@Override
		public Separator build(final MasterNodeHandlers nodeHandlers, final SUINode node) {
			return new Separator();
		}

	}

}
