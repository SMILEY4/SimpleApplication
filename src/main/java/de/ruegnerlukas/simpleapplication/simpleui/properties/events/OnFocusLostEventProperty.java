package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.events.FocusEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SuiEvent;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.Node;
import lombok.Getter;

public class OnFocusLostEventProperty extends AbstractObservableListenerProperty<FocusEventData, Boolean> {


	/**
	 * The identifying string of the event.
	 */
	public static final String EVENT_ID = "focus.lost";
	/**
	 * The listener for events with {@link FocusEventData}.
	 */
	@Getter
	private final SUIEventListener<FocusEventData> listener;




	/**
	 * @param listener the listener for events with {@link FocusEventData}.
	 */
	public OnFocusLostEventProperty(final SUIEventListener<FocusEventData> listener) {
		super(OnFocusLostEventProperty.class, (value, prev, next) -> {
			if (!next) {
				listener.onEvent(new SuiEvent<>(
						EVENT_ID,
						FocusEventData.builder()
								.focused(false)
								.build()
				));
			}
		});
		this.listener = listener;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnFocusLostEventProperty, Node> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SuiNode node, final OnFocusLostEventProperty property,
						  final Node fxNode) {
			fxNode.focusedProperty().addListener(property.getChangeListener());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnFocusLostEventProperty property,
									 final SuiNode node, final Node fxNode) {
			node.getPropertySafe(OnFocusLostEventProperty.class).ifPresent(prop -> {
				fxNode.focusedProperty().removeListener(prop.getChangeListener());
			});
			fxNode.focusedProperty().addListener(property.getChangeListener());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnFocusLostEventProperty property,
									 final SuiNode node, final Node fxNode) {
			fxNode.focusedProperty().removeListener(property.getChangeListener());
			return MutationResult.MUTATED;
		}

	}

}
