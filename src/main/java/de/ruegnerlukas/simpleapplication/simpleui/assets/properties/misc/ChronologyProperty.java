package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.FactoryExtension;
import javafx.scene.control.DatePicker;
import lombok.Getter;

import java.time.chrono.Chronology;
import java.util.Locale;
import java.util.function.BiFunction;

public class ChronologyProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<ChronologyProperty, ChronologyProperty, Boolean> COMPARATOR =
			(a, b) -> a.getChronology().equals(b.getChronology());

	/**
	 * The default {@link Chronology}.
	 */
	public static final Chronology DEFAULT = Chronology.ofLocale(Locale.getDefault(Locale.Category.FORMAT));

	/**
	 * The {@link Chronology}.
	 */
	@Getter
	private final Chronology chronology;




	/**
	 * @param chronology the {@link Chronology}
	 */
	public ChronologyProperty(final Chronology chronology) {
		super(ChronologyProperty.class, COMPARATOR);
		this.chronology = chronology;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param locale the locale to use
		 * @return this builder for chaining
		 */
		default T chronology(final Locale locale) {
			Validations.INPUT.notNull(locale).exception("The locale may not be null.");
			return chronology(Chronology.ofLocale(locale));
		}


		/**
		 * @param chronology the {@link Chronology}
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T chronology(final Chronology chronology) {
			getFactoryInternalProperties().add(new ChronologyProperty(chronology));
			return (T) this;
		}

	}






	public static class DatePickerUpdatingBuilder implements PropFxNodeUpdatingBuilder<ChronologyProperty, DatePicker> {


		@Override
		public void build(final SuiNode node,
						  final ChronologyProperty property,
						  final DatePicker fxNode) {
			fxNode.setChronology(property.getChronology());
		}




		@Override
		public MutationResult update(final ChronologyProperty property,
									 final SuiNode node,
									 final DatePicker fxNode) {
			fxNode.setChronology(property.getChronology());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ChronologyProperty property,
									 final SuiNode node,
									 final DatePicker fxNode) {
			fxNode.setChronology(DEFAULT);
			return MutationResult.MUTATED;
		}

	}


}



