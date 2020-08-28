package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.events.MouseButtonEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEvent;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.Node;
import lombok.Getter;

public class OnMouseReleasedEventProperty extends AbstractEventListenerProperty<MouseButtonEventData> {

	/**
	 * The identifying string of the event.
	 */
	public static final String EVENT_ID = "mouse.released";

	/**
	 * The listener for events with {@link MouseButtonEventData}.
	 */
	@Getter
	private final SUIEventListener<MouseButtonEventData> listener;




	/**
	 * @param listener the listener for events with {@link MouseButtonEventData}.
	 */
	public OnMouseReleasedEventProperty(final SUIEventListener<MouseButtonEventData> listener) {
		super(OnMouseReleasedEventProperty.class);
		this.listener = listener;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnMouseReleasedEventProperty, Node> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final OnMouseReleasedEventProperty property,
						  final Node fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnMouseReleasedEventProperty property,
									 final SUINode node, final Node fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnMouseReleasedEventProperty property,
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
		private void setListener(final Node fxNode, final OnMouseReleasedEventProperty property) {
			fxNode.setOnMouseReleased(e -> property.getListener().onEvent(new SUIEvent<>(
					EVENT_ID,
					MouseButtonEventData.builder()
							.x(e.getX())
							.y(e.getY())
							.button(e.getButton())
							.clickCount(e.getClickCount())
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
