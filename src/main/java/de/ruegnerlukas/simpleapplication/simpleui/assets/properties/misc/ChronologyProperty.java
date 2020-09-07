package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import javafx.scene.control.DatePicker;
import lombok.Getter;

import java.time.chrono.Chronology;
import java.util.Locale;

public class ChronologyProperty extends Property {


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
		super(ChronologyProperty.class);
		this.chronology = chronology;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return chronology.equals(((ChronologyProperty) other).getChronology());
	}




	@Override
	public String printValue() {
		return String.valueOf(chronology);
	}





	public static class DatePickerUpdatingBuilder implements PropFxNodeUpdatingBuilder<ChronologyProperty, DatePicker> {


		@Override
		public void build(final SuiBaseNode node,
						  final ChronologyProperty property,
						  final DatePicker fxNode) {
			fxNode.setChronology(property.getChronology());
		}




		@Override
		public MutationResult update(final ChronologyProperty property,
									 final SuiBaseNode node,
									 final DatePicker fxNode) {
			fxNode.setChronology(property.getChronology());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ChronologyProperty property,
									 final SuiBaseNode node,
									 final DatePicker fxNode) {
			fxNode.setChronology(DEFAULT);
			return MutationResult.MUTATED;
		}

	}


}



