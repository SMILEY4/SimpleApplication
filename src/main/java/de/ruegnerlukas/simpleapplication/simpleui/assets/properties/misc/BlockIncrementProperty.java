package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.control.Slider;
import lombok.Getter;

public class BlockIncrementProperty extends SuiProperty {


	/**
	 * The increment size.
	 */
	@Getter
	private final Number increment;




	/**
	 * @param increment the increment size
	 */
	public BlockIncrementProperty(final Number increment) {
		super(BlockIncrementProperty.class);
		this.increment = increment;
	}




	@Override
	protected boolean isPropertyEqual(final SuiProperty other) {
		return NumberUtils.isEqual(getIncrement(), ((BlockIncrementProperty) other).getIncrement());
	}




	@Override
	public String printValue() {
		return getIncrement().toString();
	}




	public static class SliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<BlockIncrementProperty, Slider> {


		@Override
		public void build(final SuiNode node,
						  final BlockIncrementProperty property,
						  final Slider fxNode) {
			fxNode.setBlockIncrement(property.getIncrement().doubleValue());
		}




		@Override
		public MutationResult update(final BlockIncrementProperty property,
									 final SuiNode node,
									 final Slider fxNode) {
			fxNode.setBlockIncrement(property.getIncrement().doubleValue());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final BlockIncrementProperty property,
									 final SuiNode node,
									 final Slider fxNode) {
			fxNode.setBlockIncrement(1);
			return MutationResult.MUTATED;
		}

	}


}



