package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.MouseDragEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.Node;
import lombok.Getter;

public class OnMouseDragReleasedEventProperty extends AbstractEventListenerProperty<MouseDragEventData> {


	/**
	 * The listener for events with {@link MouseDragEventData}.
	 */
	@Getter
	private final SUIEventListener<MouseDragEventData> listener;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link MouseDragEventData}.
	 */
	public OnMouseDragReleasedEventProperty(final String propertyId, final SUIEventListener<MouseDragEventData> listener) {
		super(OnMouseDragReleasedEventProperty.class, propertyId);
		this.listener = listener;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnMouseDragReleasedEventProperty, Node> {


		@Override
		public void build(final SuiNode node,
						  final OnMouseDragReleasedEventProperty property,
						  final Node fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final OnMouseDragReleasedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnMouseDragReleasedEventProperty property,
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
		private void setListener(final Node fxNode, final OnMouseDragReleasedEventProperty property) {
			fxNode.setOnMouseDragReleased(e -> property.getListener().onEvent(
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
			));
		}

	}


}
