package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.OrientationProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.get;

public final class SuiSeparator {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiSeparator() {
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
		Properties.checkIllegal(SuiSeparator.class, get().getEntry(SuiSeparator.class).getProperties(), propList);
		return state -> new SuiNode(SuiSeparator.class, propList, state, null);
	}




	/**
	 * Creates a new separator node,
	 *
	 * @param properties the properties
	 * @return the factory for a separator node
	 */
	public static NodeFactory separator(final Property... properties) {
		Properties.validate(SuiSeparator.class, get().getEntry(SuiSeparator.class).getProperties(), properties);
		return state -> new SuiNode(SuiSeparator.class, List.of(properties), state, null);
	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SuiRegistry registry) {
		registry.registerBaseFxNodeBuilder(SuiSeparator.class, new FxNodeBuilder());
		registry.registerProperties(SuiSeparator.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiSeparator.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiSeparator.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiSeparator.class, List.of(
				PropertyEntry.of(OrientationProperty.class, new OrientationProperty.SeparatorOrientationUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements BaseFxNodeBuilder<Separator> {


		@Override
		public Separator build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
			return new Separator();
		}

	}

}
