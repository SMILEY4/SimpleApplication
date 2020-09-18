package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.HoverEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.Node;
import lombok.Getter;

public class OnHoverStoppedEventProperty extends AbstractEventListenerProperty<HoverEventData> {


	/**
	 * The listener for events with {@link HoverEventData}.
	 */
	@Getter
	private final SuiEventListener<HoverEventData> listener;

	/**
	 * The proxy for the actual change listener.
	 */
	@Getter
	private final ChangeListenerProxy<Boolean> changeListenerProxy;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link HoverEventData}.
	 */
	public OnHoverStoppedEventProperty(final String propertyId, final SuiEventListener<HoverEventData> listener) {
		super(OnHoverStoppedEventProperty.class, propertyId);
		this.listener = listener;
		this.changeListenerProxy = new ChangeListenerProxy<>((prev, next) -> {
			if (!next) {
				listener.onEvent(
						HoverEventData.builder()
								.hover(false)
								.build()
				);
			}
		});
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnHoverStoppedEventProperty, Node> {


		@Override
		public void build(final SuiNode node,
						  final OnHoverStoppedEventProperty property,
						  final Node fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.focusedProperty());
		}




		@Override
		public MutationResult update(final OnHoverStoppedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			node.getPropertyStore().getSafe(OnHoverStoppedEventProperty.class)
					.map(OnHoverStoppedEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.focusedProperty()));
			property.getChangeListenerProxy().addTo(fxNode.focusedProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnHoverStoppedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.focusedProperty());
			return MutationResult.MUTATED;
		}

	}

}
