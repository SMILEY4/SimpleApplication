package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedComboBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.FactoryExtension;
import javafx.scene.control.ChoiceBox;
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




	@SuppressWarnings ("unchecked")
	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		default T contentItemConverter(final Class<T> type, final StringConverter<T> converter) {
			getFactoryInternalProperties().add(Properties.contentItemConverter(type, converter));
			return (T) this;
		}

		default T contentItemConverter(final String propertyId, final Class<T> type, final StringConverter<T> converter) {
			getFactoryInternalProperties().add(Properties.contentItemConverter(propertyId, type, converter));
			return (T) this;
		}

		default T contentItemConverter(final Class<T> type,
									   final ChoicesConverterProperty.FromStringConverter<T> fromString,
									   final ChoicesConverterProperty.ToStringConverter<T> toString) {
			getFactoryInternalProperties().add(Properties.contentItemConverter(type, fromString, toString));
			return (T) this;
		}

		default T contentItemConverter(final String propertyId,
									   final Class<T> type,
									   final ChoicesConverterProperty.FromStringConverter<T> fromString,
									   final ChoicesConverterProperty.ToStringConverter<T> toString) {
			getFactoryInternalProperties().add(Properties.contentItemConverter(propertyId, type, fromString, toString));
			return (T) this;
		}


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






	public static class ComboBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<ChoicesConverterProperty<T>, ExtendedComboBox<T>> {


		@Override
		public void build(final SuiNode node, final ChoicesConverterProperty<T> property, final ExtendedComboBox<T> fxNode) {
			fxNode.setConverter(property.getConverter());
		}




		@Override
		public MutationResult update(final ChoicesConverterProperty<T> property, final SuiNode node, final ExtendedComboBox<T> fxNode) {
			fxNode.setConverter(property.getConverter());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ChoicesConverterProperty<T> property, final SuiNode node, final ExtendedComboBox<T> fxNode) {
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



