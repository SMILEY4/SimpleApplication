package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;
import lombok.Getter;

import static de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator.MutationResult;

public class ChoiceBoxConverterProperty extends Property {


	/**
	 * The string converter
	 */
	@Getter
	private final StringConverter<Object> converter;




	/**
	 * @param converter the string converter
	 */
	public ChoiceBoxConverterProperty(final StringConverter<Object> converter) {
		super(ChoiceBoxConverterProperty.class);
		this.converter = converter;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return converter.equals(((ChoiceBoxConverterProperty) other).getConverter());
	}




	@Override
	public String printValue() {
		return getConverter() != null ? getConverter().toString() : "null";
	}




	public static class CBConverterUpdatingBuilder implements PropFxNodeUpdatingBuilder<ChoiceBoxConverterProperty, ChoiceBox<Object>> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final ChoiceBoxConverterProperty property,
						  final ChoiceBox<Object> fxNode) {
			fxNode.setConverter(property.getConverter());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final ChoiceBoxConverterProperty property,
									 final SUINode node, final ChoiceBox<Object> fxNode) {
			fxNode.setConverter(property.getConverter());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final ChoiceBoxConverterProperty property,
									 final SUINode node, final ChoiceBox<Object> fxNode) {
			fxNode.setConverter(null);
			return MutationResult.MUTATED;
		}

	}


}



