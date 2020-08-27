package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.events.FocusEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEvent;
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
	 * @param listener the listener for events with {@link FocusEventData}.
	 */
	public OnFocusReceivedEventProperty(final SUIEventListener<FocusEventData> listener) {
		super(OnFocusReceivedEventProperty.class);
		this.listener = listener;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnFocusReceivedEventProperty, Node> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final OnFocusReceivedEventProperty property,
						  final Node fxNode) {
			fxNode.focusedProperty().addListener((value, prev, next) -> {
				if (next) {
					property.getListener().onEvent(new SUIEvent<>(
							"focus.received",
							FocusEventData.builder()
									.focused(false)
									.build()
					));
				}
			});
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnFocusReceivedEventProperty property,
									 final SUINode node, final Node fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnFocusReceivedEventProperty property,
									 final SUINode node, final Node fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}

	}

}