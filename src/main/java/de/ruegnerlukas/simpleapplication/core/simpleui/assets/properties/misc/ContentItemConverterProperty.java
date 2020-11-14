package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedComboBox;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;
import lombok.Getter;

import java.util.function.BiFunction;

public class ContentItemConverterProperty<T> extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	@SuppressWarnings ("rawtypes")
	private static final BiFunction<ContentItemConverterProperty, ContentItemConverterProperty, Boolean> COMPARATOR =
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
	public ContentItemConverterProperty(final String propertyId,
										final FromStringConverter<T> fromString,
										final ToStringConverter<T> toString) {
		super(ContentItemConverterProperty.class, COMPARATOR, propertyId);
		Validations.INPUT.notNull(fromString).exception("The converter from strings may not be null");
		Validations.INPUT.notNull(toString).exception("The converter to strings may not be null");
		this.converter = new StringConverter<>() {
			@Override
			public String toString(final T t) {
				return toString.toString(t);
			}




			@Override
			public T fromString(final String s) {
				return fromString.fromString(s);
			}
		};
	}




	/**
	 * @param propertyId see {@link SuiProperty#getPropertyId()}
	 * @param converter  the string converter
	 */
	public ContentItemConverterProperty(final String propertyId, final StringConverter<T> converter) {
		super(ContentItemConverterProperty.class, COMPARATOR, propertyId);
		Validations.INPUT.notNull(converter).exception("The converter may not be null");
		this.converter = converter;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param type       the expected type of the items to convert
		 * @param converter  the converter from and to strings
		 * @return this builder for chaining
		 */
		@SuppressWarnings ({"unchecked", "unused"})
		default <E> T contentItemConverter(final String propertyId, final Class<E> type, final StringConverter<E> converter) {
			getBuilderProperties().add(new ContentItemConverterProperty<>(propertyId, converter));
			return (T) this;
		}

		/**
		 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param type       the expected type of the items to convert
		 * @param fromString the converter from strings
		 * @param toString   the converter to strings
		 * @return this builder for chaining
		 */
		@SuppressWarnings ({"unchecked", "unused"})
		default <E> T contentItemConverter(final String propertyId,
										   final Class<E> type,
										   final ContentItemConverterProperty.FromStringConverter<E> fromString,
										   final ContentItemConverterProperty.ToStringConverter<E> toString) {
			getBuilderProperties().add(new ContentItemConverterProperty<>(propertyId, fromString, toString));
			return (T) this;
		}


	}






	public static class ChoiceBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<ContentItemConverterProperty<T>, ChoiceBox<T>> {


		@Override
		public void build(final SuiNode node,
						  final ContentItemConverterProperty<T> property,
						  final ChoiceBox<T> fxNode) {
			fxNode.setConverter(property.getConverter());
		}




		@Override
		public MutationResult update(final ContentItemConverterProperty<T> property,
									 final SuiNode node,
									 final ChoiceBox<T> fxNode) {
			fxNode.setConverter(property.getConverter());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ContentItemConverterProperty<T> property,
									 final SuiNode node,
									 final ChoiceBox<T> fxNode) {
			fxNode.setConverter(null);
			return MutationResult.MUTATED;
		}

	}






	public static class ComboBoxUpdatingBuilder<T> implements
			PropFxNodeUpdatingBuilder<ContentItemConverterProperty<T>, ExtendedComboBox<T>> {


		@Override
		public void build(final SuiNode node, final ContentItemConverterProperty<T> property, final ExtendedComboBox<T> fxNode) {
			fxNode.setConverter(property.getConverter());
		}




		@Override
		public MutationResult update(final ContentItemConverterProperty<T> property, final SuiNode node, final ExtendedComboBox<T> fxNode) {
			fxNode.setConverter(property.getConverter());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ContentItemConverterProperty<T> property, final SuiNode node, final ExtendedComboBox<T> fxNode) {
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



