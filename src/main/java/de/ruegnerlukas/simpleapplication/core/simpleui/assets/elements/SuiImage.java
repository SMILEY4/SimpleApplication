package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.ImageProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.ImageSizeProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.PreserveRatioProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import javafx.scene.image.ImageView;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiImage {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiImage() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiImageBuilder create() {
		return new SuiImageBuilder();
	}




	public static class SuiImageBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiImageBuilder>,
			RegionBuilderExtension<SuiImageBuilder>,
			CommonEventBuilderExtension<SuiImageBuilder>,
			PreserveRatioProperty.PropertyBuilderExtension<SuiImageBuilder>,
			ImageSizeProperty.PropertyBuilderExtension<SuiImageBuilder>,
			ImageProperty.PropertyBuilderExtension<SuiImageBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiImage.class,
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
		registry.registerBaseFxNodeBuilder(SuiImage.class, new FxNodeBuilder());
		registry.registerProperties(SuiImage.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiImage.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiImage.class, List.of(
				PropertyEntry.of(ImageProperty.class, new ImageProperty.ImageViewUpdatingBuilder()),
				PropertyEntry.of(ImageSizeProperty.class, new ImageSizeProperty.ImageViewUpdatingBuilder()),
				PropertyEntry.of(PreserveRatioProperty.class, new PreserveRatioProperty.ImageViewUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<ImageView> {


		@Override
		public ImageView build(final SuiNode node) {
			return new ImageView();
		}

	}


}
