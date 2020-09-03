package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.events.CheckedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SuiEvent;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.CheckBox;
import lombok.Getter;

public class OnUncheckedEventProperty extends AbstractObservableListenerProperty<CheckedEventData, Boolean> {


	/**
	 * The identifying string of the event.
	 */
	public static final String EVENT_ID = "scroll.horizontal";

	/**
	 * The listener for events with {@link CheckedEventData}.
	 */
	@Getter
	private final SUIEventListener<CheckedEventData> listener;




	/**
	 * @param listener the listener for events with {@link CheckedEventData}.
	 */
	public OnUncheckedEventProperty(final SUIEventListener<CheckedEventData> listener) {
		super(OnUncheckedEventProperty.class, (value, prev, next) -> {
			if (next != null && !next) {
				listener.onEvent(new SuiEvent<>(
						EVENT_ID,
						CheckedEventData.builder()
								.checked(false)
								.build()
				));
			}
		});
		this.listener = listener;
	}




	public static class CheckboxUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnUncheckedEventProperty, CheckBox> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnUncheckedEventProperty property,
						  final CheckBox fxNode) {
			property.addChangeListenerTo(fxNode.selectedProperty());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnUncheckedEventProperty property,
									 final SuiNode node,
									 final CheckBox fxNode) {
			node.getPropertySafe(OnUncheckedEventProperty.class).ifPresent(prop -> {
				prop.removeChangeListenerFrom(fxNode.selectedProperty());
			});
			property.addChangeListenerTo(fxNode.selectedProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnUncheckedEventProperty property,
									 final SuiNode node,
									 final CheckBox fxNode) {
			property.removeChangeListenerFrom(fxNode.selectedProperty());
			return MutationResult.MUTATED;
		}

	}


}
