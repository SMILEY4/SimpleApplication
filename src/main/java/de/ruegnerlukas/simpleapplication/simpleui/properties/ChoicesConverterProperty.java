package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import lombok.Getter;

public class ChoicesConverterProperty<T> extends Property {


	/**
	 * The string converter
	 */
	@Getter
	private final StringConverter<T> converter;




	/**
	 * @param fromString converter from a string to an object
	 * @param toString   converter from an object to a string
	 */
	public ChoicesConverterProperty(final FromStringConverter<T> fromString, final ToStringConverter<T> toString) {
		this(new StringConverter<T>() {
			@Override
			public String toString(final T t) {
				return toString.toString(t);
			}




			@Override
			public T fromString(final String s) {
				return fromString.fromString(s);
			}
		});
	}




	/**
	 * @param converter the string converter
	 */
	public ChoicesConverterProperty(final StringConverter<T> converter) {
		super(ChoicesConverterProperty.class);
		this.converter = converter;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return converter.equals(((ChoicesConverterProperty) other).getConverter());
	}




	@Override
	public String printValue() {
		return getConverter() != null ? getConverter().toString() : "null";
	}




	public static class ChoiceBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<ChoicesConverterProperty<T>, ChoiceBox<T>> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final ChoicesConverterProperty<T> property,
						  final ChoiceBox<T> fxNode) {
			fxNode.setConverter(property.getConverter());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final ChoicesConverterProperty<T> property,
									 final SuiNode node,
									 final ChoiceBox<T> fxNode) {
			fxNode.setConverter(property.getConverter());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final ChoicesConverterProperty<T> property,
									 final SuiNode node,
									 final ChoiceBox<T> fxNode) {
			fxNode.setConverter(null);
			return MutationResult.MUTATED;
		}

	}






	public static class ComboBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<ChoicesConverterProperty<T>, ComboBox<T>> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final ChoicesConverterProperty<T> property,
						  final ComboBox<T> fxNode) {
			fxNode.setConverter(property.getConverter());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final ChoicesConverterProperty<T> property,
									 final SuiNode node,
									 final ComboBox<T> fxNode) {
			fxNode.setConverter(property.getConverter());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final ChoicesConverterProperty<T> property,
									 final SuiNode node,
									 final ComboBox<T> fxNode) {
			fxNode.setConverter(null);
			return MutationResult.MUTATED;
		}

	}






	public interface FromStringConverter<T> {


		/**
		 * Convert the given string to a value
		 *
		 * @param str the string to convert
		 * @return the created value
		 */
		T fromString(String str);

	}






	public interface ToStringConverter<T> {


		/**
		 * Convert the given value to a string
		 *
		 * @param value the object to convert
		 * @return the created string
		 */
		String toString(T value);

	}


}



