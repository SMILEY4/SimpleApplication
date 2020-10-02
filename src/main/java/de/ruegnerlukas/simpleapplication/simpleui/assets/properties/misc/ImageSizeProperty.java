package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import lombok.Getter;

import java.util.Optional;
import java.util.function.BiFunction;

public class ImageSizeProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<ImageSizeProperty, ImageSizeProperty, Boolean> COMPARATOR =
			(a, b) -> a.getWidth().equals(b.getWidth()) && a.getHeight().equals(b.getHeight());

	/**
	 * The width-dimension.
	 */
	@Getter
	private final ImageDimension width;

	/**
	 * The height-dimension.
	 */
	@Getter
	private final ImageDimension height;




	/**
	 * @param width  the width-dimension.
	 * @param height the height-dimension.
	 */
	public ImageSizeProperty(final ImageDimension width, final ImageDimension height) {
		super(ImageSizeProperty.class, COMPARATOR);
		this.width = width;
		this.height = height;
	}




	public static final class ImageDimension {


		/**
		 * Possible dimension types
		 */
		public enum Type {

			/**
			 * size of the original image.
			 */
			UNDEFINED,

			/**
			 * absolute size
			 */
			ABSOLUTE,

			/**
			 * relative to the original image size
			 */
			RELATIVE,

			/**
			 * size relative to the parent node
			 */
			PARENT_RELATIVE
		}




		/**
		 * @return an nundefined image dimension. Size will be that of the original image.
		 */
		public static ImageDimension undefined() {
			return new ImageDimension(Type.UNDEFINED, null);
		}




		/**
		 * @return an image dimension with an absolute size
		 */
		public static ImageDimension absolute(final Number size) {
			return new ImageDimension(Type.ABSOLUTE, size);
		}




		/**
		 * @return an image dimension with a size relative to the original image size
		 */
		public static ImageDimension relative(final Number percentage) {
			return new ImageDimension(Type.RELATIVE, percentage);
		}




		/**
		 * @return an image dimension with a size relative to the parent node
		 */
		public static ImageDimension parentRelative(final Number percentage) {
			return new ImageDimension(Type.PARENT_RELATIVE, percentage);
		}




		/**
		 * The type of this dimension
		 */
		@Getter
		private final Type type;

		/**
		 * The size. Relative, absolute or null
		 */
		@Getter
		private final Number size;




		/**
		 * @param type the type
		 * @param size the size/percentage or null
		 */
		private ImageDimension(final Type type, final Number size) {
			this.type = type;
			this.size = size;
		}




		@Override
		public boolean equals(final Object o) {
			if (o instanceof ImageDimension) {
				final ImageDimension other = (ImageDimension) o;
				return this.getType() == other.getType() && NumberUtils.isEqual(getSize(), other.getSize());
			} else {
				return false;
			}
		}




		@Override
		public int hashCode() {
			return super.hashCode();
		}

	}






	public static class ImageViewUpdatingBuilder implements PropFxNodeUpdatingBuilder<ImageSizeProperty, ImageView> {


		@Override
		public void build(final SuiNode node,
						  final ImageSizeProperty property,
						  final ImageView fxNode) {
			setWidth(property, fxNode);
			setHeight(property, fxNode);
		}




		@Override
		public MutationResult update(final ImageSizeProperty property,
									 final SuiNode node,
									 final ImageView fxNode) {
			setWidth(property, fxNode);
			setHeight(property, fxNode);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ImageSizeProperty property,
									 final SuiNode node,
									 final ImageView fxNode) {
			fxNode.setFitWidth(Optional.ofNullable(fxNode.getImage()).map(Image::getWidth).orElse(0.0));
			fxNode.setFitHeight(Optional.ofNullable(fxNode.getImage()).map(Image::getWidth).orElse(0.0));
			return MutationResult.MUTATED;
		}




		/**
		 * Calculate and set the width of the given image view.
		 *
		 * @param property  the size property
		 * @param imageView the image view
		 */
		private void setWidth(final ImageSizeProperty property, final ImageView imageView) {
			if (property.getWidth().getType() == ImageDimension.Type.PARENT_RELATIVE) {
				Platform.runLater(() -> {
					Validations.STATE.typeOf(imageView.getParent(), Region.class)
							.log("Parent must be of type Region to use the parent-relative type. {}", imageView.getParent())
							.onSuccess(() -> {
								final Region parent = (Region) imageView.getParent();
								imageView.fitWidthProperty().bind(
										parent.widthProperty().multiply(property.getWidth().getSize().doubleValue()));
							});
				});
			} else {
				imageView.fitWidthProperty().unbind();
				imageView.setFitWidth(getSize(imageView, property.getWidth()));
			}
		}




		/**
		 * Calculate and set the width of the given image view.
		 *
		 * @param property  the size property
		 * @param imageView the image view
		 */
		private void setHeight(final ImageSizeProperty property, final ImageView imageView) {
			if (property.getHeight().getType() == ImageDimension.Type.PARENT_RELATIVE) {
				Platform.runLater(() -> {
					Validations.STATE.typeOf(imageView.getParent(), Region.class)
							.log("Parent must be of type Region to use the parent-relative type. {}", imageView.getParent())
							.onSuccess(() -> {
								final Region parent = (Region) imageView.getParent();
								imageView.fitHeightProperty().bind(
										parent.heightProperty().multiply(property.getHeight().getSize().doubleValue()));
							});
				});
			} else {
				imageView.fitHeightProperty().unbind();
				imageView.setFitHeight(getSize(imageView, property.getHeight()));
			}
		}




		/**
		 * @param imageView the image view
		 * @param dimension the dimension
		 * @return the absolute size of the dimension for the given view.
		 */
		private double getSize(final ImageView imageView, final ImageDimension dimension) {
			if (dimension.getType() == ImageDimension.Type.ABSOLUTE) {
				return dimension.getSize().doubleValue();
			} else if (dimension.getType() == ImageDimension.Type.RELATIVE) {
				return dimension.getSize().doubleValue() * (imageView.getImage() == null ? 0.0 : imageView.getImage().getWidth());
			} else {
				return Optional.ofNullable(imageView.getImage()).map(Image::getWidth).orElse(0.0);
			}
		}

	}


}


