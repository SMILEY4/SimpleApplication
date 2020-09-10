package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.control.Slider;
import lombok.Getter;

import java.util.function.BiFunction;

public class MinMaxProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<MinMaxProperty, MinMaxProperty, Boolean> COMPARATOR =
			(a, b) -> NumberUtils.isEqual(a.getMin(), b.getMin()) && NumberUtils.isEqual(a.getMax(), b.getMax());

	/**
	 * the min value (inclusive)
	 */
	@Getter
	private final Number min;


	/**
	 * the max value (inclusive)
	 */
	@Getter
	private final Number max;




	/**
	 * @param min the min value (inclusive)
	 * @param max the max value (inclusive)
	 */
	public MinMaxProperty(final Number min, final Number max) {
		super(MinMaxProperty.class, COMPARATOR);
		this.min = min;
		this.max = max;
	}




	public static class SliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<MinMaxProperty, Slider> {


		@Override
		public void build(final SuiNode node,
						  final MinMaxProperty property,
						  final Slider fxNode) {
			fxNode.setMin(property.getMin().doubleValue());
			fxNode.setMax(property.getMax().doubleValue());
		}




		@Override
		public MutationResult update(final MinMaxProperty property,
									 final SuiNode node,
									 final Slider fxNode) {
			fxNode.setMin(property.getMin().doubleValue());
			fxNode.setMax(property.getMax().doubleValue());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MinMaxProperty property,
									 final SuiNode node,
									 final Slider fxNode) {
			fxNode.setMin(-Double.MAX_VALUE);
			fxNode.setMax(+Double.MAX_VALUE);
			return MutationResult.MUTATED;
		}

	}


}



