package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.events.FocusEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
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
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnFocusReceivedEventProperty property,
						  final Node fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.focusedProperty());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnFocusReceivedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			node.getPropertySafe(OnFocusReceivedEventProperty.class)
					.map(OnFocusReceivedEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.focusedProperty()));
			property.getChangeListenerProxy().addTo(fxNode.focusedProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnFocusReceivedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.focusedProperty());
			return MutationResult.MUTATED;
		}

	}

}
