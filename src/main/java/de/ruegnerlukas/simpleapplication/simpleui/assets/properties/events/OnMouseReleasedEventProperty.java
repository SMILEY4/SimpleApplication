package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.MouseButtonEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.Node;
import lombok.Getter;

public class OnMouseReleasedEventProperty extends AbstractEventListenerProperty<MouseButtonEventData> {

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
		public void build(final SuiNode node,
						  final OnMouseReleasedEventProperty property,
						  final Node fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final OnMouseReleasedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnMouseReleasedEventProperty property,
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
		private void setListener(final Node fxNode, final OnMouseReleasedEventProperty property) {
			fxNode.setOnMouseReleased(e -> property.getListener().onEvent(
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
			));
		}

	}


}
