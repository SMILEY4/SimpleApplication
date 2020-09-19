package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.DatePickerActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.control.DatePicker;
import lombok.Getter;

public class OnSelectedDateEventProperty extends AbstractEventListenerProperty<DatePickerActionEventData> {


	/**
	 * The listener for events with {@link DatePickerActionEventData}.
	 */
	@Getter
	private final SuiEventListener<DatePickerActionEventData> listener;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link DatePickerActionEventData}.
	 */
	public OnSelectedDateEventProperty(final String propertyId, final SuiEventListener<DatePickerActionEventData> listener) {
		super(OnSelectedDateEventProperty.class, propertyId);
		this.listener = listener;
	}




	public static class DatePickerUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnSelectedDateEventProperty, DatePicker> {


		@Override
		public void build(final SuiNode node,
						  final OnSelectedDateEventProperty property,
						  final DatePicker fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final OnSelectedDateEventProperty property,
									 final SuiNode node,
									 final DatePicker fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnSelectedDateEventProperty property,
									 final SuiNode node,
									 final DatePicker fxNode) {
			fxNode.setOnAction(null);
			return MutationResult.MUTATED;
		}




		/**
		 * Attaches the listener to the given fx node.
		 *
		 * @param fxNode   the fx node to listen to
		 * @param property the property with the listener to add
		 */
		private void setListener(final DatePicker fxNode, final OnSelectedDateEventProperty property) {
			fxNode.setOnAction(e -> property.getListener().onEvent(new DatePickerActionEventData(fxNode.getValue(), e)));
		}

	}

}
