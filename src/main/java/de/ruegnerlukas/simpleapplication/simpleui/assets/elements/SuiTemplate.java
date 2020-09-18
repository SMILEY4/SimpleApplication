package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.Node;

import java.util.List;

public final class SuiTemplate {

	// ==========================================================================
	// TO NOT USE THIS AS A COMPONENT !!
	// THIS IS JUST TEMPLATE CODE TO MAKE CREATING FUTURE COMPONENTS FASTER !!
	// ==========================================================================




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
	public static NodeFactory template(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiTemplate.class, properties);
		return (state, tags) -> SuiNode.create(
				SuiTemplate.class,
				List.of(properties),
				state,
				tags
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




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<Node> {


		@Override
		public Node build(final SuiNode node) {
			return null;
		}

	}


}
