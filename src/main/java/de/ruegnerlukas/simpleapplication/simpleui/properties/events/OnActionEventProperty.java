package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.events.ActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.ButtonBase;
import lombok.Getter;

public class OnActionEventProperty extends AbstractEventListenerProperty<ActionEventData> {


	/**
	 * The identifying string of the event.
	 */
	public static final String EVENT_ID = "action.buttonbase";

	/**
	 * The listener for events with {@link ActionEventData}.
	 */
	@Getter
	private final SUIEventListener<ActionEventData> listener;




	/**
	 * @param listener the listener for events with {@link ActionEventData}.
	 */
	public OnActionEventProperty(final SUIEventListener<ActionEventData> listener) {
		super(OnActionEventProperty.class);
		this.listener = listener;
	}




	public static class ButtonBaseUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnActionEventProperty, ButtonBase> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnActionEventProperty property,
						  final ButtonBase fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnActionEventProperty property,
									 final SuiNode node,
									 final ButtonBase fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnActionEventProperty property,
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
		private void setListener(final ButtonBase fxNode, final OnActionEventProperty property) {
			fxNode.setOnAction(e -> property.getListener().onEvent(new ActionEventData(e)));
		}

	}


}
