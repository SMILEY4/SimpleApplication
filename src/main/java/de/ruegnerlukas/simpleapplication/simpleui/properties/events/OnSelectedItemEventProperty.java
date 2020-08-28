package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEvent;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.events.SelectedItemEventData;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.ChoiceBox;
import lombok.Getter;

public class OnSelectedItemEventProperty<T> extends AbstractEventListenerProperty<SelectedItemEventData<T>> {


	/**
	 * The listener for events with {@link SelectedItemEventData}.
	 */
	@Getter
	private final SUIEventListener<SelectedItemEventData<T>> listener;




	/**
	 * @param listener the listener for events with {@link SelectedItemEventData}.
	 */
	public OnSelectedItemEventProperty(final SUIEventListener<SelectedItemEventData<T>> listener) {
		super(OnSelectedItemEventProperty.class);
		this.listener = listener;
	}




	public static class ChoiceBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<OnSelectedItemEventProperty<T>, ChoiceBox<T>> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final OnSelectedItemEventProperty<T> property,
						  final ChoiceBox<T> fxNode) {
			setListener(property, fxNode);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnSelectedItemEventProperty<T> property,
									 final SUINode node, final ChoiceBox<T> fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnSelectedItemEventProperty<T> property,
									 final SUINode node, final ChoiceBox<T> fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}




		/**
		 * Sets the listener of the given property and the given choicebox.
		 *
		 * @param property  the property
		 * @param choiceBox the choiceBox
		 */
		private void setListener(final OnSelectedItemEventProperty<T> property, final ChoiceBox<T> choiceBox) {
			choiceBox.getSelectionModel().selectedItemProperty().addListener((value, prev, next) -> {
				property.getListener().onEvent(new SUIEvent<>(
						"selection.item.choicebox",
						new SelectedItemEventData<>(next, prev)
				));
			});
		}

	}


}
