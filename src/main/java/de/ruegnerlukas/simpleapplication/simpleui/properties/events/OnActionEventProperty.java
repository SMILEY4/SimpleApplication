package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.events.ActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEvent;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.ButtonBase;
import lombok.Getter;

public class OnActionEventProperty extends AbstractEventListenerProperty<ActionEventData> {


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
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final OnActionEventProperty property,
						  final ButtonBase fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnActionEventProperty property,
									 final SUINode node, final ButtonBase fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnActionEventProperty property,
									 final SUINode node, final ButtonBase fxNode) {
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
			fxNode.setOnAction(e -> property.getListener().onEvent(new SUIEvent<>(
					"action.buttonbase",
					ActionEventData.builder()
							.source(e)
							.build()
			)));
		}

	}


}
