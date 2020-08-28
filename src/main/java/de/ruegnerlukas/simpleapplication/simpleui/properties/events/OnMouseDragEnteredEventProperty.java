package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.events.MouseDragEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEvent;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.Node;
import lombok.Getter;

public class OnMouseDragEnteredEventProperty extends AbstractEventListenerProperty<MouseDragEventData> {

	/**
	 * The identifying string of the event.
	 */
	public static final String EVENT_ID = "mouse.drag.entered";

	/**
	 * The listener for events with {@link MouseDragEventData}.
	 */
	@Getter
	private final SUIEventListener<MouseDragEventData> listener;




	/**
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 */
	public OnMouseDragEnteredEventProperty(final SUIEventListener<MouseDragEventData> listener) {
		super(OnMouseDragEnteredEventProperty.class);
		this.listener = listener;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnMouseDragEnteredEventProperty, Node> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final OnMouseDragEnteredEventProperty property,
						  final Node fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnMouseDragEnteredEventProperty property,
									 final SUINode node, final Node fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnMouseDragEnteredEventProperty property,
									 final SUINode node, final Node fxNode) {
			fxNode.setOnMouseClicked(null);
			return MutationResult.MUTATED;
		}




		/**
		 * Attaches the listener to the given fx node.
		 *
		 * @param fxNode   the fx node to listen to
		 * @param property the property with the listener to add
		 */
		private void setListener(final Node fxNode, final OnMouseDragEnteredEventProperty property) {
			fxNode.setOnMouseDragEntered(e -> property.getListener().onEvent(new SUIEvent<>(
					EVENT_ID,
					MouseDragEventData.builder()
							.x(e.getX())
							.y(e.getY())
							.button(e.getButton())
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
