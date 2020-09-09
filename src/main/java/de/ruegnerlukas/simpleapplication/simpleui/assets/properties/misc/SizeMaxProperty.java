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
public class SizeMaxProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<SizeMaxProperty, SizeMaxProperty, Boolean> COMPARATOR = (a, b) ->
			NumberUtils.isEqual(a.getWidth(), b.getWidth()) && NumberUtils.isEqual(a.getHeight(), b.getHeight());

	/**
	 * The maximum width.
	 */
	private final Number width;


	/**
	 * The maximum height.
	 */
	private final Number height;




	/**
	 * @param width  the maximum width.
	 * @param height the maximum height.
	 */
	public SizeMaxProperty(final Number width, final Number height) {
		super(SizeMaxProperty.class, COMPARATOR);
		this.width = width;
		this.height = height;
	}




	public static class RegionUpdatingBuilder implements PropFxNodeUpdatingBuilder<SizeMaxProperty, Region> {


		@Override
		public void build(final SuiNode node,
						  final SizeMaxProperty property,
						  final Region fxNode) {
			fxNode.setMaxSize(property.getWidth().doubleValue(), property.getHeight().doubleValue());
		}




		@Override
		public MutationResult update(final SizeMaxProperty property,
									 final SuiNode node,
									 final Region fxNode) {
			fxNode.setMaxSize(property.getWidth().doubleValue(), property.getHeight().doubleValue());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SizeMaxProperty property,
									 final SuiNode node,
									 final Region fxNode) {
			if (node.getPropertyStore().has(SizeProperty.class)) {
				SizeProperty sizeProp = node.getPropertyStore().get(SizeProperty.class);
				fxNode.setMaxSize(sizeProp.getMaxWidth().doubleValue(), sizeProp.getMaxHeight().doubleValue());
			} else {
				fxNode.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			}
			return MutationResult.MUTATED;
		}

	}


}
