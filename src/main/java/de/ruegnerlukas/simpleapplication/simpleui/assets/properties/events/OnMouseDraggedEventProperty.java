package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.MouseDragEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import javafx.scene.Node;
import lombok.Getter;

public class OnMouseDraggedEventProperty extends AbstractEventListenerProperty<MouseDragEventData> {


	/**
	 * The listener for events with {@link MouseDragEventData}.
	 */
	@Getter
	private final SUIEventListener<MouseDragEventData> listener;




	/**
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 */
	public OnMouseDraggedEventProperty(final SUIEventListener<MouseDragEventData> listener) {
		super(OnMouseDraggedEventProperty.class);
		this.listener = listener;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnMouseDraggedEventProperty, Node> {


		@Override
		public void build(final SuiBaseNode node,
						  final OnMouseDraggedEventProperty property,
						  final Node fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final OnMouseDraggedEventProperty property,
									 final SuiBaseNode node,
									 final Node fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnMouseDraggedEventProperty property,
									 final SuiBaseNode node,
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
		private void setListener(final Node fxNode, final OnMouseDraggedEventProperty property) {
			fxNode.setOnMouseDragged(e -> property.getListener().onEvent(
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
