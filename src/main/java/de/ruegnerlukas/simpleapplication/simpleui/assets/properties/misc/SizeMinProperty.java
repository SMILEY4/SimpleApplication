package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.layout.Region;
import lombok.Getter;

import java.util.function.BiFunction;

@Getter
public class SizeMinProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<SizeMinProperty, SizeMinProperty, Boolean> COMPARATOR = (a, b) ->
			NumberUtils.isEqual(a.getWidth(), b.getWidth()) && NumberUtils.isEqual(a.getHeight(), b.getHeight());

	/**
	 * The minimum width.
	 */
	private final Number width;


	/**
	 * The minimum height.
	 */
	private final Number height;




	/**
	 * @param width  the minimum width.
	 * @param height the minimum height.
	 */
	public SizeMinProperty(final Number width, final Number height) {
		super(SizeMinProperty.class, COMPARATOR);
		this.width = width;
		this.height = height;
	}




	public static class RegionUpdatingBuilder implements PropFxNodeUpdatingBuilder<SizeMinProperty, Region> {


		@Override
		public void build(final SuiNode node,
						  final SizeMinProperty property,
						  final Region fxNode) {
			fxNode.setMinSize(property.getWidth().doubleValue(), property.getHeight().doubleValue());
		}




		@Override
		public MutationResult update(final SizeMinProperty property,
									 final SuiNode node,
									 final Region fxNode) {
			fxNode.setMinSize(property.getWidth().doubleValue(), property.getHeight().doubleValue());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SizeMinProperty property,
									 final SuiNode node,
									 final Region fxNode) {
			if (node.getPropertyStore().has(SizeProperty.class)) {
				SizeProperty sizeProp = node.getPropertyStore().get(SizeProperty.class);
				fxNode.setMinSize(sizeProp.getMinWidth().doubleValue(), sizeProp.getMinHeight().doubleValue());
			} else {
				fxNode.setMinSize(0, 0);
			}
			return MutationResult.MUTATED;
		}

	}


}
