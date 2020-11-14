package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedHBox;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.FitToHeightProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.OptimizedProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.SpacingProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNodeChildListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNodeChildTransformListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import javafx.scene.layout.HBox;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiHBox {


	/**
	 * Hidden constructor for utility classes.
	 */
	private SuiHBox() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiHBoxBuilder create() {
		return new SuiHBoxBuilder();
	}




	public static class SuiHBoxBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiHBoxBuilder>,
			RegionBuilderExtension<SuiHBoxBuilder>,
			CommonEventBuilderExtension<SuiHBoxBuilder>,
			ItemListProperty.PropertyBuilderExtension<SuiHBoxBuilder>,
			ItemProperty.PropertyBuilderExtension<SuiHBoxBuilder>,
			AlignmentProperty.PropertyBuilderExtension<SuiHBoxBuilder>,
			SpacingProperty.PropertyBuilderExtension<SuiHBoxBuilder>,
			FitToHeightProperty.PropertyBuilderExtension<SuiHBoxBuilder>,
			OptimizedProperty.PropertyBuilderExtension<SuiHBoxBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiHBox.class,
					state,
					tags,
					SuiNodeChildListener.DEFAULT,
					SuiNodeChildTransformListener.DEFAULT
			);
		}

	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SuiRegistry registry) {
		registry.registerBaseFxNodeBuilder(SuiHBox.class, new FxNodeBuilder());
		registry.registerProperties(SuiHBox.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiHBox.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiHBox.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiHBox.class, List.of(
				PropertyEntry.of(FitToHeightProperty.class, new FitToHeightProperty.HBoxUpdatingBuilder()),
				PropertyEntry.of(SpacingProperty.class, new SpacingProperty.HBoxUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.HBoxUpdatingBuilder()),
				PropertyEntry.of(ItemListProperty.class, new ItemListProperty.PaneBuilder(), null),
				PropertyEntry.of(ItemProperty.class, new ItemProperty.PaneBuilder(), null),
				PropertyEntry.of(OptimizedProperty.class, new OptimizedProperty.HBoxUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<HBox> {


		@Override
		public ExtendedHBox build(final SuiNode node) {
			return new ExtendedHBox();
		}

	}


}
