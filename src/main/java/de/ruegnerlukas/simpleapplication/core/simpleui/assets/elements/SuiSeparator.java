package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.OrientationProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import javafx.scene.control.Separator;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiSeparator {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiSeparator() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
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
			return create(
					SuiSeparator.class,
					state,
					tags
			);
		}


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
