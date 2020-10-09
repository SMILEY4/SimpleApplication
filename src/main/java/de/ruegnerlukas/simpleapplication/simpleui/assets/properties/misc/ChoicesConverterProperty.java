package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import lombok.Getter;

import java.util.function.BiFunction;

public class ChoicesConverterProperty<T> extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	@SuppressWarnings ("rawtypes")
	private static final BiFunction<ChoicesConverterProperty, ChoicesConverterProperty, Boolean> COMPARATOR =
			(a, b) -> a.getConverter().equals(b.getConverter());


	/**
	 * The string converter
	 */
	@Getter
	private final StringConverter<T> converter;




	/**
	 * @param propertyId see {@link SuiProperty#getPropertyId()}
	 * @param fromString converter from a string to an object
	 * @param toString   converter from an object to a string
	 */
	public ChoicesConverterProperty(final String propertyId, final FromStringConverter<T> fromString, final ToStringConverter<T> toString) {
		this(propertyId, new StringConverter<>() {
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
	 * @param propertyId see {@link SuiProperty#getPropertyId()}
	 * @param converter  the string converter
	 */
	public ChoicesConverterProperty(final String propertyId, final StringConverter<T> converter) {
		super(ChoicesConverterProperty.class, COMPARATOR, propertyId);
		this.converter = converter;
	}




	public static class ChoiceBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<ChoicesConverterProperty<T>, ChoiceBox<T>> {


		@Override
		public void build(final SuiNode node,
						  final ChoicesConverterProperty<T> property,
						  final ChoiceBox<T> fxNode) {
			fxNode.setConverter(property.getConverter());
		}




		@Override
		public MutationResult update(final ChoicesConverterProperty<T> property,
									 final SuiNode node,
									 final ChoiceBox<T> fxNode) {
			fxNode.setConverter(property.getConverter());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ChoicesConverterProperty<T> property,
									 final SuiNode node,
									 final ChoiceBox<T> fxNode) {
			fxNode.setConverter(null);
			return MutationResult.MUTATED;
		}

	}






	public static class ComboBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<ChoicesConverterProperty<T>, ComboBox<T>> {


		@Override
		public void build(final SuiNode node, final ChoicesConverterProperty<T> property, final ComboBox<T> fxNode) {
			fxNode.setConverter(property.getConverter());
		}




		@Override
		public MutationResult update(final ChoicesConverterProperty<T> property, final SuiNode node, final ComboBox<T> fxNode) {
			fxNode.setConverter(property.getConverter());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ChoicesConverterProperty<T> property, final SuiNode node, final ComboBox<T> fxNode) {
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



