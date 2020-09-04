package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.events.FocusEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.Node;
import lombok.Getter;

public class OnFocusChangedEventProperty extends AbstractObservableListenerProperty<FocusEventData, Boolean> {


	/**
	 * The identifying string of the event.
	 */
	public static final String EVENT_ID = "focus.changed";

	/**
	 * The listener for events with {@link FocusEventData}.
	 */
	@Getter
	private final SUIEventListener<FocusEventData> listener;




	/**
	 * @param listener the listener for events with {@link FocusEventData}.
	 */
	public OnFocusChangedEventProperty(final SUIEventListener<FocusEventData> listener) {
		super(OnFocusChangedEventProperty.class, (value, prev, next) -> {
			listener.onEvent(
					FocusEventData.builder()
							.focused(next)
							.build()
			);
		});
		this.listener = listener;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnFocusChangedEventProperty, Node> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnFocusChangedEventProperty property,
						  final Node fxNode) {
			property.addChangeListenerTo(fxNode.focusedProperty());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnFocusChangedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			node.getPropertySafe(OnFocusChangedEventProperty.class).ifPresent(prop -> {
				prop.removeChangeListenerFrom(fxNode.focusedProperty());
			});
			property.addChangeListenerTo(fxNode.focusedProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnFocusChangedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			property.removeChangeListenerFrom(fxNode.focusedProperty());
			return MutationResult.MUTATED;
		}

	}

}
