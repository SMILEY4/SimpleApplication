package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;
import lombok.Getter;

import java.util.function.Function;

public class LabelFormatterProperty extends Property {


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
	protected boolean isPropertyEqual(final Property other) {
		return getFormatter().equals(((LabelFormatterProperty) other).getFormatter());
	}




	@Override
	public String printValue() {
		return getFormatter() == null ? "null" : getFormatter().toString();
	}




	public static class SliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<LabelFormatterProperty, Slider> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SuiNode node, final LabelFormatterProperty property,
						  final Slider fxNode) {
			setFormatter(fxNode, property);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final LabelFormatterProperty property,
									 final SuiNode node, final Slider fxNode) {
			setFormatter(fxNode, property);
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




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final LabelFormatterProperty property,
									 final SuiNode node, final Slider fxNode) {
			fxNode.setLabelFormatter(null);
			return MutationResult.MUTATED;
		}

	}


}



