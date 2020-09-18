package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.MouseScrollEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.Node;
import lombok.Getter;

public class OnMouseScrollEventProperty extends AbstractEventListenerProperty<MouseScrollEventData> {


	/**
	 * The listener for events with {@link MouseScrollEventData}.
	 */
	@Getter
	private final SuiEventListener<MouseScrollEventData> listener;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link MouseScrollEventData}.
	 */
	public OnMouseScrollEventProperty(final String propertyId, final SuiEventListener<MouseScrollEventData> listener) {
		super(OnMouseScrollEventProperty.class, propertyId);
		this.listener = listener;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnMouseScrollEventProperty, Node> {


		@Override
		public void build(final SuiNode node,
						  final OnMouseScrollEventProperty property,
						  final Node fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final OnMouseScrollEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnMouseScrollEventProperty property,
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
		private void setListener(final Node fxNode, final OnMouseScrollEventProperty property) {
			fxNode.setOnScroll(e -> property.getListener().onEvent(
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
