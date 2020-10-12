package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiImage;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdater;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;

import java.util.function.BiFunction;

public class ImageProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<ImageProperty, ImageProperty, Boolean> COMPARATOR =
			(a, b) -> a.getImage().getUrl().equals(b.getImage().getUrl());

	/**
	 * The image.
	 */
	@Getter
	private final Image image;




	/**
	 * @param image the image.
	 */
	public ImageProperty(final Image image) {
		super(ImageProperty.class, COMPARATOR);
		this.image = image;
	}




	public static class ImageViewUpdatingBuilder implements PropFxNodeUpdatingBuilder<ImageProperty, ImageView> {


		@Override
		public void build(final SuiNode node,
						  final ImageProperty property,
						  final ImageView fxNode) {
			fxNode.setImage(property.getImage());
			node.getPropertyStore().getSafe(ImageSizeProperty.class).ifPresent(sizeProp -> {
				@SuppressWarnings ("unchecked") final PropFxNodeBuilder<ImageSizeProperty, ImageView> builder =
						(PropFxNodeBuilder<ImageSizeProperty, ImageView>) SuiRegistry.get()
								.getEntry(SuiImage.class)
								.getPropFxNodeBuilders().get(ImageSizeProperty.class);
				if (builder != null) {
					builder.build(node, sizeProp, fxNode);
				}
			});
		}




		@Override
		public MutationResult update(final ImageProperty property,
									 final SuiNode node,
									 final ImageView fxNode) {
			fxNode.setImage(property.getImage());
			node.getPropertyStore().getSafe(ImageSizeProperty.class).ifPresent(sizeProp -> {
				@SuppressWarnings ("unchecked") final PropFxNodeUpdater<ImageSizeProperty, ImageView> updater =
						(PropFxNodeUpdater<ImageSizeProperty, ImageView>) SuiRegistry.get()
								.getEntry(SuiImage.class)
								.getPropFxNodeUpdaters().get(ImageSizeProperty.class);
				if (updater != null) {
					updater.update(sizeProp, node, fxNode);
				}
			});
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ImageProperty property,
									 final SuiNode node,
									 final ImageView fxNode) {
			fxNode.setImage(property.getImage());
			node.getPropertyStore().getSafe(ImageSizeProperty.class).ifPresent(sizeProp -> {
				@SuppressWarnings ("unchecked") final PropFxNodeUpdater<ImageSizeProperty, ImageView> updater =
						(PropFxNodeUpdater<ImageSizeProperty, ImageView>) SuiRegistry.get()
								.getEntry(SuiImage.class)
								.getPropFxNodeUpdaters().get(ImageSizeProperty.class);
				if (updater != null) {
					updater.update(sizeProp, node, fxNode);
				}
			});
			return MutationResult.MUTATED;
		}

	}


}



