package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.beans.value.ChangeListener;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import lombok.Getter;
import lombok.Setter;

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
	 * A listener to the parent node responsible for the width.
	 */
	@Getter
	@Setter
	private ChangeListener<Parent> parentWidthListener;

	/**
	 * A listener to the parent node responsible for the height.
	 */
	@Getter
	@Setter
	private ChangeListener<Parent> parentHeightListener;




	/**
	 * @param width  the width-dimension.
	 * @param height the height-dimension.
	 */
	public ImageSizeProperty(final ImageDimension width, final ImageDimension height) {
		super(ImageSizeProperty.class, COMPARATOR);
		this.width = width;
		this.height = height;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param width  the width-dimension.
		 * @param height the height-dimension.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T imageSize(final ImageSizeProperty.ImageDimension width, final ImageSizeProperty.ImageDimension height) {
			getBuilderProperties().add(new ImageSizeProperty(width, height));
			return (T) this;
		}

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


		/**
		 * Removes the parent listeners of the given property from the given node if possible
		 *
		 * @param property the property with the references to the listeners
		 * @param node     the node to remove the listeners from
		 */
		private void removeListeners(final ImageSizeProperty property, final ImageView node) {
			removeWidthListener(property, node);
			removeHeightListener(property, node);
		}




		/**
		 * Removes the parent width listener of the given property from the given node if possible
		 *
		 * @param property the property with the reference to the listener
		 * @param node     the node to remove the listener from
		 */
		private void removeWidthListener(final ImageSizeProperty property, final ImageView node) {
			if (property != null) {
				if (property.getParentWidthListener() != null) {
					node.parentProperty().removeListener(property.getParentWidthListener());
				}
			}
		}




		/**
		 * Removes the parent height listener of the given property from the given node if possible
		 *
		 * @param property the property with the reference to the listener
		 * @param node     the node to remove the listener from
		 */
		private void removeHeightListener(final ImageSizeProperty property, final ImageView node) {
			if (property != null) {
				if (property.getParentHeightListener() != null) {
					node.parentProperty().removeListener(property.getParentHeightListener());
				}
			}
		}




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
			removeListeners(node.getPropertyStore().get(ImageSizeProperty.class), fxNode);
			setWidth(property, fxNode);
			setHeight(property, fxNode);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ImageSizeProperty property,
									 final SuiNode node,
									 final ImageView fxNode) {
			removeListeners(property, fxNode);
			fxNode.setFitWidth(Optional.ofNullable(fxNode.getImage()).map(Image::getWidth).orElse(0.0));
			fxNode.setFitHeight(Optional.ofNullable(fxNode.getImage()).map(Image::getWidth).orElse(0.0));
			fxNode.fitWidthProperty().unbind();
			fxNode.fitHeightProperty().unbind();
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
				removeWidthListener(property, imageView);
				property.setParentWidthListener((value, prevParent, parent) -> {
					if (parent instanceof Region) {
						imageView.fitWidthProperty().bind(
								((Region) parent).widthProperty().multiply(property.getWidth().getSize().doubleValue()));
					} else {
						imageView.fitWidthProperty().unbind();
					}
				});
				imageView.parentProperty().addListener(property.getParentWidthListener());
			} else {
				imageView.fitWidthProperty().unbind();
				imageView.setFitWidth(getSize(imageView, property.getWidth(), true));
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
				removeHeightListener(property, imageView);
				property.setParentHeightListener((value, prevParent, parent) -> {
					if (parent instanceof Region) {
						imageView.fitHeightProperty().bind(
								((Region) parent).heightProperty().multiply(property.getHeight().getSize().doubleValue()));
					} else {
						imageView.fitHeightProperty().unbind();
					}
				});
				imageView.parentProperty().addListener(property.getParentHeightListener());
			} else {
				imageView.fitHeightProperty().unbind();
				imageView.setFitHeight(getSize(imageView, property.getHeight(), false));
			}
		}




		/**
		 * @param imageView the image view
		 * @param dimension the dimension
		 * @param isWidth   whether we calculate the width or height
		 * @return the absolute size of the dimension for the given view.
		 */
		private double getSize(final ImageView imageView, final ImageDimension dimension, final boolean isWidth) {
			final double imgSize = Optional.ofNullable(imageView.getImage())
					.map(img -> isWidth ? img.getWidth() : img.getHeight())
					.orElse(0.0);
			double resultSize;
			if (dimension.getType() == ImageDimension.Type.ABSOLUTE) {
				resultSize = dimension.getSize().doubleValue();
			} else if (dimension.getType() == ImageDimension.Type.RELATIVE) {
				resultSize = imgSize * dimension.getSize().doubleValue();
			} else {
				resultSize = imgSize;
			}
			return resultSize;
		}

	}


}



