package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.events.SuiEvent;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.events.MouseScrollEventData;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.Node;
import lombok.Getter;

public class OnMouseScrollStartedEventProperty extends AbstractEventListenerProperty<MouseScrollEventData> {

	/**
	 * The identifying string of the event.
	 */
	public static final String EVENT_ID = "mousescroll.started";

	/**
	 * The listener for events with {@link MouseScrollEventData}.
	 */
	@Getter
	private final SUIEventListener<MouseScrollEventData> listener;




	/**
	 * @param listener the listener for events with {@link MouseScrollEventData}.
	 */
	public OnMouseScrollStartedEventProperty(final SUIEventListener<MouseScrollEventData> listener) {
		super(OnMouseScrollStartedEventProperty.class);
		this.listener = listener;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnMouseScrollStartedEventProperty, Node> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SuiNode node, final OnMouseScrollStartedEventProperty property,
						  final Node fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnMouseScrollStartedEventProperty property,
									 final SuiNode node, final Node fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnMouseScrollStartedEventProperty property,
									 final SuiNode node, final Node fxNode) {
			fxNode.setOnMouseClicked(null);
			return MutationResult.MUTATED;
		}




		/**
		 * Attaches the listener to the given fx node.
		 *
		 * @param fxNode   the fx node to listen to
		 * @param property the property with the listener to add
		 */
		private void setListener(final Node fxNode, final OnMouseScrollStartedEventProperty property) {
			fxNode.setOnScrollStarted(e -> property.getListener().onEvent(new SuiEvent<>(
					EVENT_ID,
					MouseScrollEventData.builder()
							.dx(e.getDeltaX())
							.dy(e.getDeltaY())
							.pixelMultiplierX(e.getMultiplierY())
							.pixelMultiplierY(e.getMultiplierY())
							.source(e)
							.build()
			)));
		}

	}


}
