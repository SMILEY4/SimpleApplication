package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiLabeledSlider;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedSlider;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiFunction;

@Slf4j
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




	public static class SliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<MinMaxProperty, ExtendedSlider> {


		@Override
		public void build(final SuiNode node, final MinMaxProperty property, final ExtendedSlider fxNode) {
			fxNode.setMinMax(property.getMin().doubleValue(), property.getMax().doubleValue());
		}




		@Override
		public MutationResult update(final MinMaxProperty property, final SuiNode node, final ExtendedSlider fxNode) {
			fxNode.setMinMax(property.getMin().doubleValue(), property.getMax().doubleValue());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MinMaxProperty property, final SuiNode node, final ExtendedSlider fxNode) {
			fxNode.setMinMax(-Double.MAX_VALUE, +Double.MAX_VALUE);
			return MutationResult.MUTATED;
		}

	}






	public static class LabeledSliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<MinMaxProperty, Pane> {


		@Override
		public void build(final SuiNode node, final MinMaxProperty property, final Pane fxNode) {
			final ExtendedSlider slider = SuiLabeledSlider.getSlider(fxNode);
			if (slider != null) {
				slider.setMinMax(property.getMin().doubleValue(), property.getMax().doubleValue());
			} else {
				log.warn("Could not find slider of labeled-slider.");
			}
		}




		@Override
		public MutationResult update(final MinMaxProperty property, final SuiNode node, final Pane fxNode) {
			final ExtendedSlider slider = SuiLabeledSlider.getSlider(fxNode);
			if (slider != null) {
				slider.setMinMax(property.getMin().doubleValue(), property.getMax().doubleValue());
			} else {
				log.warn("Could not find slider of labeled-slider.");
			}
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MinMaxProperty property, final SuiNode node, final Pane fxNode) {
			final ExtendedSlider slider = SuiLabeledSlider.getSlider(fxNode);
			if (slider != null) {
				slider.setMinMax(-Double.MAX_VALUE, +Double.MAX_VALUE);
			} else {
				log.warn("Could not find slider of labeled-slider.");
			}
			return MutationResult.MUTATED;
		}

	}

}



