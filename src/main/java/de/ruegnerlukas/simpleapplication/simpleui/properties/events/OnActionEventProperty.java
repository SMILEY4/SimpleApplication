package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.events.ActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.events.SuiEvent;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.DatePicker;
import lombok.Getter;

import java.time.LocalDate;

public class OnActionEventProperty<T> extends AbstractEventListenerProperty<ActionEventData<T>> {


	/**
	 * The identifying string of the event.
	 */
	public static final String EVENT_ID = "action.buttonbase";

	/**
	 * The listener for events with {@link ActionEventData}.
	 */
	@Getter
	private final SUIEventListener<ActionEventData<T>> listener;




	/**
	 * @param listener the listener for events with {@link ActionEventData}.
	 */
	public OnActionEventProperty(final SUIEventListener<ActionEventData<T>> listener) {
		super(OnActionEventProperty.class);
		this.listener = listener;
	}




	public static class ButtonBaseUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnActionEventProperty<?>, ButtonBase> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnActionEventProperty<?> property,
						  final ButtonBase fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnActionEventProperty<?> property,
									 final SuiNode node,
									 final ButtonBase fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnActionEventProperty<?> property,
									 final SuiNode node,
									 final ButtonBase fxNode) {
			fxNode.setOnMouseClicked(null);
			return MutationResult.MUTATED;
		}




		/**
		 * Attaches the listener to the given fx node.
		 *
		 * @param fxNode   the fx node to listen to
		 * @param property the property with the listener to add
		 */
		private void setListener(final ButtonBase fxNode, final OnActionEventProperty<?> property) {
			fxNode.setOnAction(e -> property.getListener().onEvent(new SuiEvent<>(
					EVENT_ID, new ActionEventData<>(null, e)
			)));
		}

	}






	public static class DatePickerUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnActionEventProperty<LocalDate>, DatePicker> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnActionEventProperty<LocalDate> property,
						  final DatePicker fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnActionEventProperty<LocalDate> property,
									 final SuiNode node,
									 final DatePicker fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnActionEventProperty<LocalDate> property,
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
		private void setListener(final DatePicker fxNode, final OnActionEventProperty<LocalDate> property) {
			fxNode.setOnAction(e -> property.getListener().onEvent(new SuiEvent<>(
					EVENT_ID, new ActionEventData<>(fxNode.getValue(), e)
			)));
		}

	}

}
