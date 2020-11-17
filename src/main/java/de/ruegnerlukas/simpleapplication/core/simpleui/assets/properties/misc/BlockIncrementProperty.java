package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.SuiLabeledSlider;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
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
		Validations.INPUT.notNull(increment).exception("The increment value may not be null.");
		Validations.INPUT.isNotNegative(increment.doubleValue()).exception("The increment may not be negative");
		this.increment = increment;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param increment the increment
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T blockIncrement(final Number increment) {
			getBuilderProperties().add(new BlockIncrementProperty(increment));
			return (T) this;
		}

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






	public static class LabeledSliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<BlockIncrementProperty, Pane> {


		@Override
		public void build(final SuiNode node,
						  final BlockIncrementProperty property,
						  final Pane fxNode) {
			final Slider slider = SuiLabeledSlider.getSlider(fxNode);
			slider.setBlockIncrement(property.getIncrement().doubleValue());
		}




		@Override
		public MutationResult update(final BlockIncrementProperty property,
									 final SuiNode node,
									 final Pane fxNode) {
			final Slider slider = SuiLabeledSlider.getSlider(fxNode);
			slider.setBlockIncrement(property.getIncrement().doubleValue());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final BlockIncrementProperty property,
									 final SuiNode node,
									 final Pane fxNode) {
			final Slider slider = SuiLabeledSlider.getSlider(fxNode);
			slider.setBlockIncrement(1);
			return MutationResult.MUTATED;
		}

	}

}



