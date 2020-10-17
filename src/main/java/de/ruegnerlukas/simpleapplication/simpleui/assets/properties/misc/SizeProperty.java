package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.layout.Region;
import lombok.Getter;

import java.util.function.BiFunction;

@Getter
public class SizeProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<SizeProperty, SizeProperty, Boolean> COMPARATOR =
			(a, b) -> NumberUtils.isEqual(a.getMinWidth(), b.getMinWidth())
					&& NumberUtils.isEqual(a.getMinHeight(), b.getMinHeight())
					&& NumberUtils.isEqual(a.getPreferredWidth(), b.getPreferredWidth())
					&& NumberUtils.isEqual(a.getPreferredHeight(), b.getPreferredHeight())
					&& NumberUtils.isEqual(a.getMaxWidth(), b.getMaxWidth())
					&& NumberUtils.isEqual(a.getMaxHeight(), b.getMaxHeight());

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

		super(SizeProperty.class, COMPARATOR);
		this.minWidth = minWidth;
		this.minHeight = minHeight;
		this.preferredWidth = preferredWidth;
		this.preferredHeight = preferredHeight;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param minWidth        the minimum width.
		 * @param minHeight       the minimum height.
		 * @param preferredWidth  the preferred width.
		 * @param preferredHeight the preferred height.
		 * @param maxWidth        the maximum width.
		 * @param maxHeight       the maximum height.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T size(final Number minWidth, final Number minHeight,
					   final Number preferredWidth, final Number preferredHeight,
					   final Number maxWidth, final Number maxHeight) {
			getBuilderProperties().add(new SizeProperty(minWidth, minHeight, preferredWidth, preferredHeight, maxWidth, maxHeight));
			return (T) this;
		}

	}






	public static class RegionUpdatingBuilder implements PropFxNodeUpdatingBuilder<SizeProperty, Region> {


		@Override
		public void build(final SuiNode node,
						  final SizeProperty property,
						  final Region fxNode) {
			setSize(node, property, fxNode);
		}




		@Override
		public MutationResult update(final SizeProperty property,
									 final SuiNode node,
									 final Region fxNode) {
			setSize(node, property, fxNode);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SizeProperty property,
									 final SuiNode node,
									 final Region fxNode) {
			if (!node.getPropertyStore().has(SizeMinProperty.class)) {
				fxNode.setMinSize(0, 0);
			}
			if (!node.getPropertyStore().has(SizePreferredProperty.class)) {
				fxNode.setPrefSize(property.getPreferredWidth().doubleValue(), property.getPreferredHeight().doubleValue());
				return MutationResult.REQUIRES_REBUILD;
			}
			if (!node.getPropertyStore().has(SizeMaxProperty.class)) {
				fxNode.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			}
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
			if (!node.getPropertyStore().has(SizeMinProperty.class)) {
				fxNode.setMinSize(property.getMinWidth().doubleValue(), property.getMinHeight().doubleValue());
			}
			if (!node.getPropertyStore().has(SizePreferredProperty.class)) {
				fxNode.setPrefSize(property.getPreferredWidth().doubleValue(), property.getPreferredHeight().doubleValue());
			}
			if (!node.getPropertyStore().has(SizeMaxProperty.class)) {
				fxNode.setMaxSize(property.getMaxWidth().doubleValue(), property.getMaxHeight().doubleValue());
			}
		}


	}


}
