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
			listener.onEvent(new SUIEvent<>(
					EVENT_ID,
					FocusEventData.builder()
							.focused(next)
							.build()
			));
		});
		this.listener = listener;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnFocusChangedEventProperty, Node> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final OnFocusChangedEventProperty property,
						  final Node fxNode) {
			fxNode.focusedProperty().addListener(property.getChangeListener());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnFocusChangedEventProperty property,
									 final SUINode node, final Node fxNode) {
			node.getPropertySafe(OnFocusChangedEventProperty.class).ifPresent(prop -> {
				fxNode.focusedProperty().removeListener(prop.getChangeListener());
			});
			fxNode.focusedProperty().addListener(property.getChangeListener());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnFocusChangedEventProperty property,
									 final SUINode node, final Node fxNode) {
			fxNode.focusedProperty().removeListener(property.getChangeListener());
			return MutationResult.MUTATED;
		}

	}

}
