package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import javafx.scene.layout.Region;
import lombok.Getter;

import static de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator.MutationResult;

@Getter
public class SizeProperty extends Property {


	/**
	 * The minimum width.
	 */
	private final Double minWidth;


	/**
	 * The minimum height.
	 */
	private final Double minHeight;

	/**
	 * The preferred width.
	 */
	private final Double preferredWidth;

	/**
	 * The preferred height.
	 */
	private final Double preferredHeight;

	/**
	 * The maximum width.
	 */
	private final Double maxWidth;

	/**
	 * The maximum height.
	 */
	private final Double maxHeight;




	/**
	 * @param minWidth        the minimum width.
	 * @param minHeight       the minimum height.
	 * @param preferredWidth  the preferred width.
	 * @param preferredHeight the preferred height.
	 * @param maxWidth        the maximum width.
	 * @param maxHeight       the maximum height.
	 */
	public SizeProperty(final Double minWidth, final Double minHeight,
						final Double preferredWidth, final Double preferredHeight,
						final Double maxWidth, final Double maxHeight) {

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
	 * Safe check to see if the two given values are equal.
	 *
	 * @param a the first number or null
	 * @param b the second number or null
	 * @return whether the two numbers are equal.
	 */
	protected static boolean isEqual(final Double a, final Double b) {
		if (a == null && b == null) {
			return true;
		} else {
			if (a != null) {
				if (b == null) {
					return false;
				} else {
					return a.compareTo(b) == 0;
				}
			} else {
				return false;
			}
		}
	}




	public static class SizeUpdatingBuilder implements PropFxNodeUpdatingBuilder<SizeProperty, Region> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final SizeProperty property,
						  final Region fxNode) {
			setSize(node, property, fxNode);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final SizeProperty property,
									 final SUINode node, final Region fxNode) {
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
		private void setSize(final SUINode node, final SizeProperty property, final Region fxNode) {
			if (!node.hasProperty(SizeMinProperty.class)) {
				fxNode.setMinSize(property.getMinWidth(), property.getMinHeight());
			}
			if (!node.hasProperty(SizePreferredProperty.class)) {
				fxNode.setPrefSize(property.getPreferredWidth(), property.getPreferredHeight());
			}
			if (!node.hasProperty(SizeMaxProperty.class)) {
				fxNode.setMaxSize(property.getMaxWidth(), property.getMaxHeight());
			}
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final SizeProperty property,
									 final SUINode node, final Region fxNode) {
			if (!node.hasProperty(SizeMinProperty.class)) {
				fxNode.setMinSize(0, 0);
			}
			if (!node.hasProperty(SizePreferredProperty.class)) {
				fxNode.setPrefSize(property.getPreferredWidth(), property.getPreferredHeight());
				return MutationResult.REBUILD;
			}
			if (!node.hasProperty(SizeMaxProperty.class)) {
				fxNode.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			}
			return MutationResult.MUTATED;
		}

	}


}
