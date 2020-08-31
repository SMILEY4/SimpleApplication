package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.events.SuiEvent;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.events.SelectedItemEventData;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.ChoiceBox;
import lombok.Getter;

public class OnSelectedItemEventProperty<T> extends AbstractObservableListenerProperty<SelectedItemEventData<T>, T> {


	/**
	 * The identifying string of the event.
	 */
	public static final String EVENT_ID = "selection.item";

	/**
	 * The listener for events with {@link SelectedItemEventData}.
	 */
	@Getter
	private final SUIEventListener<SelectedItemEventData<T>> listener;




	/**
	 * @param listener the listener for events with {@link SelectedItemEventData}.
	 */
	public OnSelectedItemEventProperty(final SUIEventListener<SelectedItemEventData<T>> listener) {
		super(OnSelectedItemEventProperty.class, (value, prev, next) -> {
			listener.onEvent(new SuiEvent<>(
					EVENT_ID,
					new SelectedItemEventData<>(next, prev)
			));
		});
		this.listener = listener;
	}




	public static class ChoiceBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<OnSelectedItemEventProperty<T>, ChoiceBox<T>> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SuiNode node, final OnSelectedItemEventProperty<T> property,
						  final ChoiceBox<T> fxNode) {
			fxNode.getSelectionModel().selectedItemProperty().addListener(property.getChangeListener());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnSelectedItemEventProperty<T> property,
									 final SuiNode node, final ChoiceBox<T> fxNode) {
			node.getPropertySafe(OnSelectedItemEventProperty.class).ifPresent(prop -> {
				fxNode.getSelectionModel().selectedItemProperty().removeListener(prop.getChangeListener());
			});
			fxNode.getSelectionModel().selectedItemProperty().addListener(property.getChangeListener());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnSelectedItemEventProperty<T> property,
									 final SuiNode node, final ChoiceBox<T> fxNode) {
			fxNode.getSelectionModel().selectedItemProperty().addListener(property.getChangeListener());
			return MutationResult.MUTATED;
		}

	}


}
