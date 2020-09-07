package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.FocusEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import javafx.scene.Node;
import lombok.Getter;

public class OnFocusReceivedEventProperty extends AbstractEventListenerProperty<FocusEventData> {


	/**
	 * The listener for events with {@link FocusEventData}.
	 */
	@Getter
	private final SUIEventListener<FocusEventData> listener;

	/**
	 * The proxy for the actual change listener.
	 */
	@Getter
	private final ChangeListenerProxy<Boolean> changeListenerProxy;




	/**
	 * @param listener the listener for events with {@link FocusEventData}.
	 */
	public OnFocusReceivedEventProperty(final SUIEventListener<FocusEventData> listener) {
		super(OnFocusReceivedEventProperty.class);
		this.listener = listener;
		this.changeListenerProxy = new ChangeListenerProxy<>((prev, next) -> {
			if (next) {
				listener.onEvent(
						FocusEventData.builder()
								.focused(false)
								.build()
				);
			}
		});
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnFocusReceivedEventProperty, Node> {


		@Override
		public void build(final SuiBaseNode node,
						  final OnFocusReceivedEventProperty property,
						  final Node fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.focusedProperty());
		}




		@Override
		public MutationResult update(final OnFocusReceivedEventProperty property,
									 final SuiBaseNode node,
									 final Node fxNode) {
			node.getPropertyStore().getSafe(OnFocusReceivedEventProperty.class)
					.map(OnFocusReceivedEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.focusedProperty()));
			property.getChangeListenerProxy().addTo(fxNode.focusedProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnFocusReceivedEventProperty property,
									 final SuiBaseNode node,
									 final Node fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.focusedProperty());
			return MutationResult.MUTATED;
		}

	}

}
