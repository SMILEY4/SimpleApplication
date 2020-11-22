package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;

import java.util.Objects;
import java.util.function.BiFunction;

public class GraphicProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<GraphicProperty, GraphicProperty, Boolean> COMPARATOR = (a, b) -> {
		if (!NumberUtils.isEqual(a.getWidth(), b.getWidth())) {
			return false;
		}
		if (a.useImageGraphic() != b.useImageGraphic() || a.useNodeGraphic() != b.useNodeGraphic()) {
			return false;
		}
		if (a.useNodeGraphic()) {
			return Objects.equals(a.getNodeGraphic(), b.getNodeGraphic());
		} else {
			return a.getImgResource() == null ? (b.getImgResource() == null) : (a.getImgResource().isEqual(b.getImgResource()));
		}
	};

	/**
	 * The resource pointing to the image (or null)
	 */
	@Getter
	private final Resource imgResource;

	/**
	 * The node to use as graphic (or null)
	 */
	@Getter
	private final Node nodeGraphic;

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
	public GraphicProperty(final Resource imgResource, final Number width, final Number gap) {
		super(GraphicProperty.class, COMPARATOR);
		if (imgResource != null) {
			Validations.INPUT.notNull(width).exception("The width may not be null");
			Validations.INPUT.notNull(gap).exception("The gap may not be null");
		}
		this.imgResource = imgResource;
		this.nodeGraphic = null;
		this.width = width;
		this.gap = gap;
	}




	/**
	 * @param graphic the node to use as the graphic
	 */
	public GraphicProperty(final Node graphic, final Number gap) {
		super(GraphicProperty.class, COMPARATOR);
		if (graphic != null) {
			Validations.INPUT.notNull(gap).exception("The gap may not be null");
		}
		this.imgResource = null;
		this.nodeGraphic = graphic;
		this.width = null;
		this.gap = gap;
	}




	/**
	 * @return whether to use a generic node as the graphic
	 */
	private boolean useNodeGraphic() {
		return nodeGraphic != null;
	}




	/**
	 * @return whether to use an image as the graphic
	 */
	private boolean useImageGraphic() {
		return imgResource != null;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param resource the resource pointing to the image
		 * @param width    the width of the graphic. The height is then calculated automatically to keep the original aspect ratio
		 * @param gap      the gap between the graphic and the text content
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T imageGraphic(final Resource resource, final Number width, final Number gap) {
			getBuilderProperties().add(new GraphicProperty(resource, width, gap));
			return (T) this;
		}

		/**
		 * @param graphic the generic graphics node to display
		 * @param gap     the gap between the graphic and the text content
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T graphic(final Node graphic, final Number gap) {
			getBuilderProperties().add(new GraphicProperty(graphic, gap));
			return (T) this;
		}


	}






	public static class LabeledUpdatingBuilder implements PropFxNodeUpdatingBuilder<GraphicProperty, Labeled> {


		@Override
		public void build(final SuiNode node, final GraphicProperty property, final Labeled fxNode) {
			setGraphic(property, fxNode);
		}




		@Override
		public MutationResult update(final GraphicProperty property, final SuiNode node, final Labeled fxNode) {
			setGraphic(property, fxNode);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final GraphicProperty property, final SuiNode node, final Labeled fxNode) {
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
		private void setGraphic(final GraphicProperty property, final Labeled labeled) {
			if (property.getImgResource() != null) {
				final ImageView imageView = new ImageView(new Image(property.getImgResource().getPath()));
				imageView.setFitWidth(property.getWidth().doubleValue());
				imageView.setPreserveRatio(true);
				labeled.setGraphic(imageView);
				labeled.setGraphicTextGap(property.getGap().doubleValue());
			} else if (property.getNodeGraphic() != null) {
				labeled.setGraphic(property.getNodeGraphic());
				labeled.setGraphicTextGap(property.getGap().doubleValue());
			} else {
				labeled.setGraphic(null);
				labeled.setGraphicTextGap(0);
			}
		}

	}


}
