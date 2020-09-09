package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.control.Slider;
import lombok.Getter;

import java.util.function.BiFunction;

public class BlockIncrementProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<BlockIncrementProperty, BlockIncrementProperty, Boolean> COMPARATOR =
			(a, b) -> NumberUtils.isEqual(a.getIncrement(), b.getIncrement());

	/**
	 * The increment size.
	 */
	@Getter
	private final Number increment;




	/**
	 * @param increment the increment size
	 */
	public BlockIncrementProperty(final Number increment) {
		super(BlockIncrementProperty.class, COMPARATOR);
		this.increment = increment;
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



