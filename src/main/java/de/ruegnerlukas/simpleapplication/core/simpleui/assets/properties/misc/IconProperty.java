package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;

import java.util.function.BiFunction;

public class IconProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<IconProperty, IconProperty, Boolean> COMPARATOR =
			(a, b) -> NumberUtils.isEqual(a.getWidth(), b.getWidth())
					&& a.getImgResource() == null ? (b.getImgResource() == null) : (a.getImgResource().isEqual(b.getImgResource()));

	/**
	 * The resource pointing to the image
	 */
	@Getter
	private final Resource imgResource;

	/**
	 * The width of the icon. The height is then calculated automatically to keep the original aspect ratio
	 */
	@Getter
	private final Number width;

	/**
	 * The gap between the icon and the text content.
	 */
	@Getter
	private final Number gap;




	/**
	 * @param imgResource the resource pointing to the image
	 * @param width       the width of the icon. The height is then calculated automatically to keep the original aspect ratio
	 * @param gap         the gap between the icon and the text content
	 */
	public IconProperty(final Resource imgResource, final Number width, final Number gap) {
		super(IconProperty.class, COMPARATOR);
		if (imgResource != null) {
			Validations.INPUT.notNull(width).exception("The width may not be null");
			Validations.INPUT.notNull(gap).exception("The gap may not be null");
		}
		this.imgResource = imgResource;
		this.width = width;
		this.gap = gap;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param resource the resource pointing to the image
		 * @param width    the width of the icon. The height is then calculated automatically to keep the original aspect ratio
		 * @param gap      the gap between the icon and the text content
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T icon(final Resource resource, final Number width, final Number gap) {
			getBuilderProperties().add(new IconProperty(resource, width, gap));
			return (T) this;
		}


	}






	public static class LabeledUpdatingBuilder implements PropFxNodeUpdatingBuilder<IconProperty, Labeled> {


		@Override
		public void build(final SuiNode node, final IconProperty property, final Labeled fxNode) {
			setGraphic(property, fxNode);
		}




		@Override
		public MutationResult update(final IconProperty property, final SuiNode node, final Labeled fxNode) {
			setGraphic(property, fxNode);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final IconProperty property, final SuiNode node, final Labeled fxNode) {
			fxNode.setGraphic(null);
			fxNode.setGraphicTextGap(0);
			return MutationResult.MUTATED;
		}




		/**
		 * Sets the icon graphic of the element.
		 *
		 * @param property the property
		 * @param labeled  the element
		 */
		private void setGraphic(final IconProperty property, final Labeled labeled) {
			if (property.getImgResource() != null) {
				final ImageView imageView = new ImageView(new Image(property.getImgResource().getPath()));
				imageView.setFitWidth(property.getWidth().doubleValue());
				imageView.setPreserveRatio(true);
				labeled.setGraphic(imageView);
				labeled.setGraphicTextGap(property.getGap().doubleValue());
			} else {
				labeled.setGraphic(null);
				labeled.setGraphicTextGap(0);
			}
		}

	}


}
