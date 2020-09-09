package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;
import lombok.Getter;

import java.util.function.Function;

public class LabelFormatterProperty extends SuiProperty {


	/**
	 * The label formatting function
	 */
	@Getter
	private final Function<Double, String> formatter;




	/**
	 * @param formatter the label formatting function
	 */
	public LabelFormatterProperty(final Function<Double, String> formatter) {
		super(LabelFormatterProperty.class);
		this.formatter = formatter;
	}




	@Override
	protected boolean isPropertyEqual(final SuiProperty other) {
		return getFormatter().equals(((LabelFormatterProperty) other).getFormatter());
	}




	@Override
	public String printValue() {
		return getFormatter() == null ? "null" : getFormatter().toString();
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


}



