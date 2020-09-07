package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.MouseButtonEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import javafx.scene.Node;
import lombok.Getter;

public class OnMouseClickedEventProperty extends AbstractEventListenerProperty<MouseButtonEventData> {

	/**
	 * The listener for events with {@link MouseButtonEventData}.
	 */
	@Getter
	private final SUIEventListener<MouseButtonEventData> listener;




	/**
	 * @param listener the listener for events with {@link MouseButtonEventData}.
	 */
	public OnMouseClickedEventProperty(final SUIEventListener<MouseButtonEventData> listener) {
		super(OnMouseClickedEventProperty.class);
		this.listener = listener;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnMouseClickedEventProperty, Node> {


		@Override
		public void build(final SuiBaseNode node,
						  final OnMouseClickedEventProperty property,
						  final Node fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final OnMouseClickedEventProperty property,
									 final SuiBaseNode node,
									 final Node fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnMouseClickedEventProperty property,
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
		private void setListener(final Node fxNode, final OnMouseClickedEventProperty property) {
			fxNode.setOnMouseClicked(e -> property.getListener().onEvent(
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
