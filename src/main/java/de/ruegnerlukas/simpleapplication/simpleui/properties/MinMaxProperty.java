package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.Slider;
import lombok.Getter;

public class MinMaxProperty extends Property {


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
		super(MinMaxProperty.class);
		this.min = min;
		this.max = max;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return NumberUtils.isEqual(getMin(), ((MinMaxProperty) other).getMin())
				&& NumberUtils.isEqual(getMax(), ((MinMaxProperty) other).getMax());
	}




	@Override
	public String printValue() {
		return getMin() + "," + getMax();
	}




	public static class SliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<MinMaxProperty, Slider> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final MinMaxProperty property,
						  final Slider fxNode) {
			fxNode.setMin(property.getMin().doubleValue());
			fxNode.setMax(property.getMax().doubleValue());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final MinMaxProperty property,
									 final SuiNode node,
									 final Slider fxNode) {
			fxNode.setMin(property.getMin().doubleValue());
			fxNode.setMax(property.getMax().doubleValue());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final MinMaxProperty property,
									 final SuiNode node,
									 final Slider fxNode) {
			fxNode.setMin(-Double.MAX_VALUE);
			fxNode.setMax(+Double.MAX_VALUE);
			return MutationResult.MUTATED;
		}

	}


}



