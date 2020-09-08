package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.MouseScrollEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import javafx.scene.Node;
import lombok.Getter;

public class OnMouseScrollFinishedEventProperty extends AbstractEventListenerProperty<MouseScrollEventData> {

	/**
	 * The listener for events with {@link MouseScrollEventData}.
	 */
	@Getter
	private final SUIEventListener<MouseScrollEventData> listener;




	/**
	 * @param listener the listener for events with {@link MouseScrollEventData}.
	 */
	public OnMouseScrollFinishedEventProperty(final SUIEventListener<MouseScrollEventData> listener) {
		super(OnMouseScrollFinishedEventProperty.class);
		this.listener = listener;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnMouseScrollFinishedEventProperty, Node> {


		@Override
		public void build(final SuiBaseNode node,
						  final OnMouseScrollFinishedEventProperty property,
						  final Node fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final OnMouseScrollFinishedEventProperty property,
									 final SuiBaseNode node,
									 final Node fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnMouseScrollFinishedEventProperty property,
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
		private void setListener(final Node fxNode, final OnMouseScrollFinishedEventProperty property) {
			fxNode.setOnScrollFinished(e -> property.getListener().onEvent(
					MouseScrollEventData.builder()
							.dx(e.getDeltaX())
							.dy(e.getDeltaY())
							.pixelMultiplierX(e.getMultiplierY())
							.pixelMultiplierY(e.getMultiplierY())
							.source(e)
							.build()
			));
		}

	}


}
