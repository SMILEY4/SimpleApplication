package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.events.DatePickerActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.DatePicker;
import lombok.Getter;

import java.time.LocalDate;

public class OnSelectedDateEventProperty<T> extends AbstractEventListenerProperty<DatePickerActionEventData> {


	/**
	 * The listener for events with {@link DatePickerActionEventData}.
	 */
	@Getter
	private final SUIEventListener<DatePickerActionEventData> listener;




	/**
	 * @param listener the listener for events with {@link DatePickerActionEventData}.
	 */
	public OnSelectedDateEventProperty(final SUIEventListener<DatePickerActionEventData> listener) {
		super(OnSelectedDateEventProperty.class);
		this.listener = listener;
	}




	public static class DatePickerUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnSelectedDateEventProperty<LocalDate>, DatePicker> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnSelectedDateEventProperty<LocalDate> property,
						  final DatePicker fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnSelectedDateEventProperty<LocalDate> property,
									 final SuiNode node,
									 final DatePicker fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnSelectedDateEventProperty<LocalDate> property,
									 final SuiNode node,
									 final DatePicker fxNode) {
			fxNode.setOnMouseClicked(null);
			return MutationResult.MUTATED;
		}




		/**
		 * Attaches the listener to the given fx node.
		 *
		 * @param fxNode   the fx node to listen to
		 * @param property the property with the listener to add
		 */
		private void setListener(final DatePicker fxNode, final OnSelectedDateEventProperty<LocalDate> property) {
			fxNode.setOnAction(e -> property.getListener().onEvent(new DatePickerActionEventData(fxNode.getValue(), e)));
		}

	}

}
