package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiLabeledSlider;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.FactoryExtension;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import lombok.Getter;

import java.util.function.BiFunction;
import java.util.function.Function;

public class LabelFormatterProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<LabelFormatterProperty, LabelFormatterProperty, Boolean> COMPARATOR =
			(a, b) -> a.getFormatter().equals(b.getFormatter());


	/**
	 * The label formatting function
	 */
	@Getter
	private final Function<Double, String> formatter;




	/**
	 * @param formatter the label formatting function
	 */
	public LabelFormatterProperty(final String propertyId, final Function<Double, String> formatter) {
		super(LabelFormatterProperty.class, COMPARATOR, propertyId);
		this.formatter = formatter;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param formatter  the label formatting function
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T labelFormatter(final String propertyId, final Function<Double, String> formatter) {
			getFactoryInternalProperties().add(new LabelFormatterProperty(propertyId, formatter));
			return (T) this;
		}

	}






	public static class SliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<LabelFormatterProperty, Slider> {


		@Override
		public void build(final SuiNode node,
						  final LabelFormatterProperty property,
						  final Slider fxNode) {
			setFormatter(fxNode, property);
		}




		@Override
		public MutationResult update(final LabelFormatterProperty property,
									 final SuiNode node,
									 final Slider fxNode) {
			setFormatter(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final LabelFormatterProperty property,
									 final SuiNode node,
									 final Slider fxNode) {
			fxNode.setLabelFormatter(null);
			return MutationResult.MUTATED;
		}




		/**
		 * @param slider   the slider
		 * @param property the property
		 */
		private void setFormatter(final Slider slider, final LabelFormatterProperty property) {
			slider.setLabelFormatter(new StringConverter<>() {
				@Override
				public String toString(final Double value) {
					return property.getFormatter().apply(value);
				}




				@Override
				public Double fromString(final String string) {
					return null;
				}
			});
		}


	}






	public static class LabeledSliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<LabelFormatterProperty, Pane> {


		@Override
		public void build(final SuiNode node,
						  final LabelFormatterProperty property,
						  final Pane fxNode) {
			final Slider slider = SuiLabeledSlider.getSlider(fxNode);
			setFormatter(slider, property);
		}




		@Override
		public MutationResult update(final LabelFormatterProperty property,
									 final SuiNode node,
									 final Pane fxNode) {
			final Slider slider = SuiLabeledSlider.getSlider(fxNode);
			setFormatter(slider, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final LabelFormatterProperty property,
									 final SuiNode node,
									 final Pane fxNode) {
			final Slider slider = SuiLabeledSlider.getSlider(fxNode);
			slider.setLabelFormatter(null);
			return MutationResult.MUTATED;
		}




		/**
		 * @param slider   the slider
		 * @param property the property
		 */
		private void setFormatter(final Slider slider, final LabelFormatterProperty property) {
			slider.setLabelFormatter(new StringConverter<>() {
				@Override
				public String toString(final Double value) {
					return property.getFormatter().apply(value);
				}




				@Override
				public Double fromString(final String string) {
					return null;
				}
			});
		}


	}


}



