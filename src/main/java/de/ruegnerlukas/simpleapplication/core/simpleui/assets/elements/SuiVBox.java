package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedVBox;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.FitToWidthProperty;
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
import javafx.scene.layout.VBox;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiVBox {


	/**
	 * Hidden constructor for utility classes.
	 */
	private SuiVBox() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiVBoxBuilder create() {
		return new SuiVBoxBuilder();
	}




	public static class SuiVBoxBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiVBoxBuilder>,
			RegionBuilderExtension<SuiVBoxBuilder>,
			CommonEventBuilderExtension<SuiVBoxBuilder>,
			ItemListProperty.PropertyBuilderExtension<SuiVBoxBuilder>,
			ItemProperty.PropertyBuilderExtension<SuiVBoxBuilder>,
			AlignmentProperty.PropertyBuilderExtension<SuiVBoxBuilder>,
			SpacingProperty.PropertyBuilderExtension<SuiVBoxBuilder>,
			FitToWidthProperty.PropertyBuilderExtension<SuiVBoxBuilder>,
			OptimizedProperty.PropertyBuilderExtension<SuiVBoxBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiVBox.class,
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
		registry.registerBaseFxNodeBuilder(SuiVBox.class, new FxNodeBuilder());
		registry.registerProperties(SuiVBox.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiVBox.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiVBox.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiVBox.class, List.of(
				PropertyEntry.of(FitToWidthProperty.class, new FitToWidthProperty.VBoxUpdatingBuilder()),
				PropertyEntry.of(SpacingProperty.class, new SpacingProperty.VBoxUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.VBoxUpdatingBuilder()),
				PropertyEntry.of(ItemListProperty.class, new ItemListProperty.PaneBuilder(), null),
				PropertyEntry.of(ItemProperty.class, new ItemProperty.PaneBuilder(), null),
				PropertyEntry.of(OptimizedProperty.class, new OptimizedProperty.VBoxUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<VBox> {


		@Override
		public ExtendedVBox build(final SuiNode node) {
			return new ExtendedVBox();
		}

	}


}
