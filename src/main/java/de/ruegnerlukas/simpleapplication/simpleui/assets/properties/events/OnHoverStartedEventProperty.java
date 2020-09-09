package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.HoverEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.Node;
import lombok.Getter;

public class OnHoverStartedEventProperty extends AbstractEventListenerProperty<HoverEventData> {


	/**
	 * The listener for events with {@link HoverEventData}.
	 */
	@Getter
	private final SUIEventListener<HoverEventData> listener;

	/**
	 * The proxy for the actual change listener.
	 */
	@Getter
	private final ChangeListenerProxy<Boolean> changeListenerProxy;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link HoverEventData}.
	 */
	public OnHoverStartedEventProperty(final String propertyId, final SUIEventListener<HoverEventData> listener) {
		super(OnHoverStartedEventProperty.class, propertyId);
		this.listener = listener;
		this.changeListenerProxy = new ChangeListenerProxy<>((prev, next) -> {
			if (next) {
				listener.onEvent(
						HoverEventData.builder()
								.hover(true)
								.build()
				);
			}
		});
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnHoverStartedEventProperty, Node> {


		@Override
		public void build(final SuiNode node,
						  final OnHoverStartedEventProperty property,
						  final Node fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.focusedProperty());
		}




		@Override
		public MutationResult update(final OnHoverStartedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			node.getPropertyStore().getSafe(OnHoverStartedEventProperty.class)
					.map(OnHoverStartedEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.focusedProperty()));
			property.getChangeListenerProxy().addTo(fxNode.focusedProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnHoverStartedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.focusedProperty());
			return MutationResult.MUTATED;
		}

	}

}
