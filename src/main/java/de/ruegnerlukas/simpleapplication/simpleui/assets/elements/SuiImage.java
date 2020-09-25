package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ImageProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ImageSizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.PreserveRatioProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.image.ImageView;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiImage {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiImage() {
		// do nothing
	}




	/**
	 * Creates a new image
	 *
	 * @param properties the properties
	 * @return the factory for a image
	 */
	public static NodeFactory image(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiImage.class, properties);
		return (state, tags) -> SuiNode.create(
				SuiImage.class,
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
