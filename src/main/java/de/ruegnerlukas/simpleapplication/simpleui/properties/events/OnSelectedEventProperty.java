package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEvent;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.events.SelectionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.ChoiceBox;
import lombok.Getter;

import java.util.Optional;

public class OnSelectedEventProperty<T> extends AbstractEventListenerProperty<SelectionEventData<T>> {


	/**
	 * The listener for events with {@link SelectionEventData}.
	 */
	@Getter
	private final SUIEventListener<SelectionEventData<T>> listener;




	/**
	 * @param listener the listener for events with {@link SelectionEventData}.
	 */
	public OnSelectedEventProperty(final SUIEventListener<SelectionEventData<T>> listener) {
		super(OnSelectedEventProperty.class);
		this.listener = listener;
	}




	public static class ChoiceBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<OnSelectedEventProperty<T>, ChoiceBox<T>> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final OnSelectedEventProperty<T> property,
						  final ChoiceBox<T> fxNode) {
			setListener(property, fxNode);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnSelectedEventProperty<T> property,
									 final SUINode node, final ChoiceBox<T> fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnSelectedEventProperty<T> property,
									 final SUINode node, final ChoiceBox<T> fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}




		/**
		 * Sets the listener of the given property and the given choicebox.
		 *
		 * @param property  the property
		 * @param choiceBox the choiceBox
		 */
		private void setListener(final OnSelectedEventProperty<T> property, final ChoiceBox<T> choiceBox) {
			choiceBox.getSelectionModel().selectedIndexProperty().addListener((value, prev, next) -> {
				final Integer prevIndex = Optional.ofNullable(prev).map(Number::intValue).orElse(null);
				final Integer nextIndex = Optional.ofNullable(prev).map(Number::intValue).orElse(null);
				final T prevItem = Optional.ofNullable(prevIndex).map(index -> choiceBox.getItems().get(index)).orElse(null);
				final T nextItem = Optional.ofNullable(nextIndex).map(index -> choiceBox.getItems().get(index)).orElse(null);
				property.getListener().onEvent(new SUIEvent<>(
						"selection.choicebox",
						new SelectionEventData<>(prevItem, prevIndex, nextItem, nextIndex)
				));
			});
		}

	}


}
