package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.OrientationProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.scene.control.Separator;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiSeparator {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiSeparator() {
		// do nothing
	}




	public static SuiSeparatorBuilder create() {
		return new SuiSeparatorBuilder();
	}




	public static class SuiSeparatorBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiSeparatorBuilder>,
			RegionBuilderExtension<SuiSeparatorBuilder>,
			CommonEventBuilderExtension<SuiSeparatorBuilder>,
			OrientationProperty.PropertyBuilderExtension<SuiSeparatorBuilder>,
			TooltipProperty.PropertyBuilderExtension<SuiSeparatorBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return SuiNode.create(
					SuiSeparator.class,
					getFactoryInternalProperties(),
					state,
					tags
			);
		}


	}




	/**
	 * Creates a new separator node,
	 *
	 * @param properties the properties
	 * @return the factory for a separator node
	 */
	public static NodeFactory separator(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiSeparator.class, properties);
		return (state, tags) -> SuiNode.create(
				SuiSeparator.class,
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
		registry.registerBaseFxNodeBuilder(SuiSeparator.class, new FxNodeBuilder());
		registry.registerProperties(SuiSeparator.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiSeparator.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiSeparator.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiSeparator.class, List.of(
				PropertyEntry.of(OrientationProperty.class, new OrientationProperty.SeparatorUpdatingBuilder()),
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<Separator> {


		@Override
		public Separator build(final SuiNode node) {
			return new Separator();
		}

	}

}
