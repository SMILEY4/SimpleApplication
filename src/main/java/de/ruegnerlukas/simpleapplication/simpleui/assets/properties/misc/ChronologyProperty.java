package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
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



