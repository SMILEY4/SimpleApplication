package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.MouseMoveEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.Node;
import lombok.Getter;

public class OnMouseMovedEventProperty extends AbstractEventListenerProperty<MouseMoveEventData> {


	/**
	 * The listener for events with {@link MouseMoveEventData}.
	 */
	@Getter
	private final SUIEventListener<MouseMoveEventData> listener;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link MouseMoveEventData}.
	 */
	public OnMouseMovedEventProperty(final String propertyId, final SUIEventListener<MouseMoveEventData> listener) {
		super(OnMouseMovedEventProperty.class, propertyId);
		this.listener = listener;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnMouseMovedEventProperty, Node> {


		@Override
		public void build(final SuiNode node,
						  final OnMouseMovedEventProperty property,
						  final Node fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final OnMouseMovedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnMouseMovedEventProperty property,
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
		private void setListener(final Node fxNode, final OnMouseMovedEventProperty property) {
			fxNode.setOnMouseMoved(e -> property.getListener().onEvent(
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
			));
		}

	}


}
