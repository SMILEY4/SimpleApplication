package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.Slider;
import lombok.Getter;

public class BlockIncrementProperty extends Property {


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
	protected boolean isPropertyEqual(final Property other) {
		return NumberUtils.isEqual(getIncrement(), ((BlockIncrementProperty) other).getIncrement());
	}




	@Override
	public String printValue() {
		return getIncrement().toString();
	}




	public static class SliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<BlockIncrementProperty, Slider> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final BlockIncrementProperty property,
						  final Slider fxNode) {
			fxNode.setBlockIncrement(property.getIncrement().doubleValue());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final BlockIncrementProperty property,
									 final SuiNode node,
									 final Slider fxNode) {
			fxNode.setBlockIncrement(property.getIncrement().doubleValue());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final BlockIncrementProperty property,
									 final SuiNode node,
									 final Slider fxNode) {
			fxNode.setBlockIncrement(1);
			return MutationResult.MUTATED;
		}

	}


}



