package de.ruegnerlukas.simpleapplication.simpleui.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiBaseNode;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.scene.Node;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.get;

public final class SuiTemplate {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiTemplate() {
		// do nothing
	}




	/**
	 * Creates a new INSERT_NAME
	 *
	 * @param properties the properties
	 * @return the factory for a INSERT_NAME
	 */
	public static NodeFactory template(final Property... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiTemplate.class, get().getEntry(SuiTemplate.class).getProperties(), properties);
		return state -> SuiBaseNode.create(
				SuiTemplate.class,
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
		registry.registerBaseFxNodeBuilder(SuiTemplate.class, new FxNodeBuilder());
		registry.registerProperties(SuiTemplate.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiTemplate.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiTemplate.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiTemplate.class, List.of(
				// ...
		));
	}




	private static class FxNodeBuilder implements BaseFxNodeBuilder<Node> {


		@Override
		public Node build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
			return null;
		}

	}


}
