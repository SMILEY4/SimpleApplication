package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.events.MouseMoveEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SuiEvent;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.Node;
import lombok.Getter;

public class OnMouseEnteredEventProperty extends AbstractEventListenerProperty<MouseMoveEventData> {

	/**
	 * The identifying string of the event.
	 */
	public static final String EVENT_ID = "mouse.move.entered";

	/**
	 * The listener for events with {@link MouseMoveEventData}.
	 */
	@Getter
	private final SUIEventListener<MouseMoveEventData> listener;




	/**
	 * @param listener the listener for events with {@link MouseMoveEventData}.
	 */
	public OnMouseEnteredEventProperty(final SUIEventListener<MouseMoveEventData> listener) {
		super(OnMouseEnteredEventProperty.class);
		this.listener = listener;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnMouseEnteredEventProperty, Node> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnMouseEnteredEventProperty property,
						  final Node fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnMouseEnteredEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnMouseEnteredEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			fxNode.setOnMouseClicked(null);
			return MutationResult.MUTATED;
		}




		/**
		 * Attaches the listener to the given fx node.
		 *
		 * @param fxNode   the fx node to listen to
		 * @param property the property with the listener to add
		 */
		private void setListener(final Node fxNode, final OnMouseEnteredEventProperty property) {
			fxNode.setOnMouseEntered(e -> property.getListener().onEvent(new SuiEvent<>(
					EVENT_ID,
					MouseMoveEventData.builder()
							.x(e.getX())
							.y(e.getY())
							.altDown(e.isAltDown())
							.ctrlDown(e.isControlDown())
							.metaDown(e.isMetaDown())
							.shiftDown(e.isShiftDown())
							.shortcutDown(e.isShortcutDown())
							.source(e)
							.build()
			)));
		}

	}


}
