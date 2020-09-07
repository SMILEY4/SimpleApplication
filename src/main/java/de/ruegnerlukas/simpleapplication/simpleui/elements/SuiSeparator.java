package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiBaseNode;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.properties.OrientationProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.scene.control.Separator;

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
	 * @param properties the properties
	 * @return the factory for a separator node
	 */
	public static NodeFactory separator(final Property... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiSeparator.class, get().getEntry(SuiSeparator.class).getProperties(), properties);
		return state -> SuiBaseNode.create(
				SuiSeparator.class,
				List.of(properties),
				state
		);
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
				PropertyEntry.of(OrientationProperty.class, new OrientationProperty.SeparatorUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements BaseFxNodeBuilder<Separator> {


		@Override
		public Separator build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
			return new Separator();
		}

	}

}
