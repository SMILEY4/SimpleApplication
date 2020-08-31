package de.ruegnerlukas.simpleapplication.simpleui.properties;


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
		return isEqual(this.getMinWidth(), sizeOther.getMinWidth())
				&& isEqual(this.getMinHeight(), sizeOther.getMinHeight())
				&& isEqual(this.getPreferredWidth(), sizeOther.getPreferredWidth())
				&& isEqual(this.getPreferredHeight(), sizeOther.getPreferredHeight())
				&& isEqual(this.getMaxWidth(), sizeOther.getMaxWidth())
				&& isEqual(this.getMaxHeight(), sizeOther.getMaxHeight());
	}




	@Override
	public String printValue() {
		return "min=" + getMinWidth() + "x" + getMinHeight()
				+ " preferred=" + getPreferredWidth() + "x" + getPreferredHeight()
				+ " max=" + getMaxWidth() + "x" + getMaxHeight();
	}




	/**
	 * Null-Safe check to see if the two given values are equal.
	 *
	 * @param a the first number or null
	 * @param b the second number or null
	 * @return whether the two numbers are equal.
	 */
	protected static boolean isEqual(final Number a, final Number b) {
		if (a == null && b == null) {
			return true;
		} else {
			if (a != null) {
				if (b == null) {
					return false;
				} else {
					return compare(a, b) == 0;
				}
			} else {
				return false;
			}
		}
	}




	/**
	 * Compares the two given numbers.
	 *
	 * @param a the first number
	 * @param b the second number
	 * @return the value 0 if a is numerically equal to b;
	 * a value less than 0 if a is numerically less than b;
	 * and a value greater than 0 if a is numerically greater than b.
	 */
	private static int compare(final Number a, final Number b) {
		if (a instanceof Double || a instanceof Float || b instanceof Double || b instanceof Float) {
			return Double.compare(a.doubleValue(), b.doubleValue());
		} else {
			return Long.compare(a.longValue(), b.longValue());
		}
	}




	public static class RegionUpdatingBuilder implements PropFxNodeUpdatingBuilder<SizeProperty, Region> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SuiNode node, final SizeProperty property,
						  final Region fxNode) {
			setSize(node, property, fxNode);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final SizeProperty property,
									 final SuiNode node, final Region fxNode) {
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
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final SizeProperty property,
									 final SuiNode node, final Region fxNode) {
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
