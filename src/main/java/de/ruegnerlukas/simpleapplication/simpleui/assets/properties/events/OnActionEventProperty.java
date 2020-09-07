package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
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
		public void build(final SuiBaseNode node,
						  final OnActionEventProperty property,
						  final ButtonBase fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final OnActionEventProperty property,
									 final SuiBaseNode node,
									 final ButtonBase fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnActionEventProperty property,
									 final SuiBaseNode node,
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
