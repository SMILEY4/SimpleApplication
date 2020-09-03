package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.layout.Region;
import lombok.Getter;

@Getter
public class SizeProperty extends Property {


	/**
	 * The minimum width.
	 */
	private final Number minWidth;


	/**
	 * The minimum height.
	 */
	private final Number minHeight;

	/**
	 * The preferred width.
	 */
	private final Number preferredWidth;

	/**
	 * The preferred height.
	 */
	private final Number preferredHeight;

	/**
	 * The maximum width.
	 */
	private final Number maxWidth;

	/**
	 * The maximum height.
	 */
	private final Number maxHeight;




	/**
	 * @param minWidth        the minimum width.
	 * @param minHeight       the minimum height.
	 * @param preferredWidth  the preferred width.
	 * @param preferredHeight the preferred height.
	 * @param maxWidth        the maximum width.
	 * @param maxHeight       the maximum height.
	 */
	public SizeProperty(final Number minWidth, final Number minHeight,
						final Number preferredWidth, final Number preferredHeight,
						final Number maxWidth, final Number maxHeight) {

		super(SizeProperty.class);
		this.minWidth = minWidth;
		this.minHeight = minHeight;
		this.preferredWidth = preferredWidth;
		this.preferredHeight = preferredHeight;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		final SizeProperty sizeOther = (SizeProperty) other;
		return NumberUtils.isEqual(this.getMinWidth(), sizeOther.getMinWidth())
				&& NumberUtils.isEqual(this.getMinHeight(), sizeOther.getMinHeight())
				&& NumberUtils.isEqual(this.getPreferredWidth(), sizeOther.getPreferredWidth())
				&& NumberUtils.isEqual(this.getPreferredHeight(), sizeOther.getPreferredHeight())
				&& NumberUtils.isEqual(this.getMaxWidth(), sizeOther.getMaxWidth())
				&& NumberUtils.isEqual(this.getMaxHeight(), sizeOther.getMaxHeight());
	}




	@Override
	public String printValue() {
		return "min=" + getMinWidth() + "x" + getMinHeight()
				+ " preferred=" + getPreferredWidth() + "x" + getPreferredHeight()
				+ " max=" + getMaxWidth() + "x" + getMaxHeight();
	}




	public static class RegionUpdatingBuilder implements PropFxNodeUpdatingBuilder<SizeProperty, Region> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final SizeProperty property,
						  final Region fxNode) {
			setSize(node, property, fxNode);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final SizeProperty property,
									 final SuiNode node,
									 final Region fxNode) {
			setSize(node, property, fxNode);
			return MutationResult.MUTATED;
		}




		/**
		 * Sets the size of the given fx node.
		 * SizeMinProperty, SizePreferredProperty, SizeMaxProperty have higher priority, if the given node has these too.
		 *
		 * @param node     the simpleui-node
		 * @param property the property
		 * @param fxNode   the fx region
		 */
		private void setSize(final SuiNode node, final SizeProperty property, final Region fxNode) {
			if (!node.hasProperty(SizeMinProperty.class)) {
				fxNode.setMinSize(property.getMinWidth().doubleValue(), property.getMinHeight().doubleValue());
			}
			if (!node.hasProperty(SizePreferredProperty.class)) {
				fxNode.setPrefSize(property.getPreferredWidth().doubleValue(), property.getPreferredHeight().doubleValue());
			}
			if (!node.hasProperty(SizeMaxProperty.class)) {
				fxNode.setMaxSize(property.getMaxWidth().doubleValue(), property.getMaxHeight().doubleValue());
			}
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final SizeProperty property,
									 final SuiNode node,
									 final Region fxNode) {
			if (!node.hasProperty(SizeMinProperty.class)) {
				fxNode.setMinSize(0, 0);
			}
			if (!node.hasProperty(SizePreferredProperty.class)) {
				fxNode.setPrefSize(property.getPreferredWidth().doubleValue(), property.getPreferredHeight().doubleValue());
				return MutationResult.REQUIRES_REBUILD;
			}
			if (!node.hasProperty(SizeMaxProperty.class)) {
				fxNode.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			}
			return MutationResult.MUTATED;
		}

	}


}
